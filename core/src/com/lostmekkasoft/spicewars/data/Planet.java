/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

import com.badlogic.gdx.Game;
import com.lostmekkasoft.spicewars.GameplayScreen;
import com.lostmekkasoft.spicewars.SpiceWars;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author LostMekka
 */
public class Planet extends Location {

	public static enum PlanetType {
		normal, station
	}
	
	public static double MAX_HP = 1000;
	public static double HP_REGENERATION = 0.5;

	public Team team;
	public String name;
	public int maxNormalSlots;
	public int maxMineSlots;
	public LinkedList<Building> normalSlots;
	public LinkedList<Building> mineSlots;
	public double hp = MAX_HP;
	private boolean hasHQ = false;
	public PlanetType type;

	public Planet(int radius, Team team, int maxNormalSlots, int maxMineSlots, Point position, PlanetType type, GameplayScreen parentScreen) {
		super(position, radius, parentScreen);
		this.team = team;
		this.maxNormalSlots = maxNormalSlots;
		this.maxMineSlots = maxMineSlots;
		this.type = type;
		normalSlots = new LinkedList<>();
		mineSlots = new LinkedList<>();
		if(type == PlanetType.station)this.maxMineSlots = 0;
	}

	@Override
	public void update(double time) {
		if(armies.size() == 1){
			// there is one army here. bombard planet?
			Army a = armies.getFirst();
			if(a.team != team) a.bombardPlanet(this, time);
		}
		super.update(time);
		// regenerate planet hp
		hp = Math.min(hp + HP_REGENERATION*time, MAX_HP);
		// update buildings
		for(Building b : normalSlots) b.update(time);
	}

	public int getWorkingWorkers(Team t){
		int workers=0;
		int buildings=0;
		for(Army a:armies){
			if(a.team == t) workers = (int)Math.ceil(a.ships[0]);	
		}
		if(workers == 0) return 0;
		for(Building b:normalSlots){
			if(b.team == t && (!b.isFinishedBuilding || b.hp < b.getMaxHp())) buildings++;
		}
		for(Building b:mineSlots){
			if(b.team == t && (!b.isFinishedBuilding || b.hp < b.getMaxHp())) buildings++;
		}
		return Math.min(buildings*Building.MAX_WORKERS_PER_BUILDING, workers);
	}
	
	public int getWorkingFactories(Team t){
		int i = 0;
		for(Building b : normalSlots){
			if(b.isActive == true) i++;
		}
		return i;
	}
	
	public void buildStuff(Team t, double efficiency, double time){
		if(t == SpiceWars.teamNeutral) return;
		// build units out of factories (only when this planet belongs to the team)
		if(t == team) for(Building b : normalSlots){
			int i;
			switch(b.type){
				case workerFactory : i = 0; break;
				case fighterFactory : i = 1; break;
				case frigateFactory : i = 2; break;
				case destroyerFactory : i = 3; break;
				default: continue;
			}
			b.progress += Building.FACTORY_SPICE_USAGE / Army.cost[i] * efficiency * time;
			int n = (int)b.progress;
			if(n >= 0){
				Army a = getArmy(t);
				if(a == null){
					a = new Army(t);
					a.ships[i] += n;
					armies.add(a);
				}
				b.progress -= n;
			}
		}
		// build buildings with workers
		int workerCount = getWorkingWorkers(t);
		LinkedList<Building> l = (LinkedList<Building>)normalSlots.clone();
		l.addAll(mineSlots);
		for(Building b : l) if(b.team == t && !b.isFinishedBuilding){
			if(workerCount <= 0) break;
			int w = Math.min(workerCount, Building.MAX_WORKERS_PER_BUILDING);
			workerCount -= w;
			double rate = w * Building.WORKER_SPICE_USAGE / b.getCost() * efficiency * time;
			if(rate + b.progress > 1) rate = 1 - b.progress;
			b.progress += rate;
			b.hp += rate * (b.getMaxHp() - 1);
			if(b.progress >= 1) buildingFinished(b);
		}
		// repair buildings with the remaining workers
		for(Building b : l) if(b.team == t && b.isFinishedBuilding && b.hp < b.getMaxHp()){
			if(workerCount <= 0) break;
			int w = Math.min(workerCount, Building.MAX_WORKERS_PER_BUILDING);
			workerCount -= w;
			int max = b.getMaxHp();
			double rate = w * Building.WORKER_SPICE_USAGE / b.getCost() * efficiency * time;
			b.hp = Math.min(hp + rate * max, max);
		}
	}
	
	private Army getArmy(Team t){
		for(Army a : armies) if(a.team == t) return a;
		return null;
	}
	
	private void changeTeam(Team t){
		// reset team of planet and all buildings
		team = t;
		for(Building b2 : normalSlots) b2.changeTeam(t);
		for(Building b2 : mineSlots) b2.changeTeam(t);
	}
	
	private void buildingFinished(Building b){
		if(b.type == Building.BuildingType.hq){
			hasHQ = true;
			// remove all hqs that are not of this team (any unfinished buildings)
			ListIterator<Building> iter = normalSlots.listIterator();
			while(iter.hasNext()){
				Building b2 = iter.next();
				if(b2.type == Building.BuildingType.hq && b2.team != b.team){
					iter.remove();
				}
			}
			changeTeam(b.team);
		}
	}
	
	public boolean canAddBuilding(Building.BuildingType t) {
		if (t == Building.BuildingType.spiceMine) {
			return maxMineSlots > mineSlots.size();
		} else {
			return (!hasHQ || t != Building.BuildingType.hq) &&
					maxNormalSlots > normalSlots.size();
		}
	}

	public boolean addBuilding(Building.BuildingType t) {
		if (!canAddBuilding(t)) return false;
		addBuildingInternal(new Building(t, team, this), t == Building.BuildingType.spiceMine ? mineSlots : normalSlots);
		return true;
	}

	private void addBuildingInternal(Building b, LinkedList<Building> l) {
		ListIterator<Building> i = l.listIterator();
		while (i.hasNext()) {
			Building b2 = i.next();
			if (b.type.ordinal() > b2.type.ordinal()) {
				continue;
			}
			i.previous();
			i.add(b);
			return;
		}
		l.addLast(b);
	}

	public void damageRandomBuilding(double damage, double accuracy) {
		if (!hasHQ) {
			return; // do not damage neutral planets
		}
		Building b;
		if (accuracy >= 1) {
			b = normalSlots.getFirst();
		} else if (accuracy <= 0) {
			int r = SpiceWars.random.nextInt(normalSlots.size() + mineSlots.size());
			if (r < normalSlots.size()) {
				b = normalSlots.get(r);
			} else {
				b = mineSlots.get(r - normalSlots.size());
			}
		} else {
			double r = accuracy + (1 - accuracy) * (normalSlots.size() - 1 + mineSlots.size());
			r *= SpiceWars.random.nextDouble();
			if (r < accuracy) {
				b = normalSlots.getFirst();
			} else {
				r -= accuracy;
				int i = (int) (r / (1 - accuracy)) + 1;
				if (i < normalSlots.size()) {
					b = normalSlots.get(i);
				} else {
					b = mineSlots.get(i - normalSlots.size());
				}
			}
		}
		b.hp -= damage;
		if (b.hp <= 0) {
			destroyBuilding(b);
			// deal overdamage to additional building
			if (b.hp < 0) {
				damageRandomBuilding(-b.hp, accuracy);
			}
		}
	}

	private void destroyBuilding(Building b){
		normalSlots.remove(b);
		mineSlots.remove(b);
		b.onDestroy();
		if(b.type == Building.BuildingType.hq){
			hasHQ = false;
			changeTeam(SpiceWars.teamNeutral);
		}
	}
	
	public void onDestroy(){
		//destroy planet
		for(Building b : mineSlots) destroyBuilding(b); 
		for(Building b : normalSlots) if(b.type != Building.BuildingType.hq){
			destroyBuilding(b);
		}
	}
}
