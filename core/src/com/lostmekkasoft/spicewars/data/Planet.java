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
public class Planet {

	public static enum PlanetType {
		normal, station
	}
	
	public static double MAX_HP = 1000;
	public static double HP_REGENERATION = 0.5;

	public int radius;
	public Team team;
	public String name;
	public int maxNormalSlots;
	public int maxMineSlots;
	public LinkedList<Building> normalSlots;
	public LinkedList<Building> mineSlots;
	public double hp = MAX_HP;
	public Point position;
	public LinkedList<Army> armies = new LinkedList<>();
	private boolean hasHQ = false;
	public PlanetType type;

	public Planet(int radius, Team team, int maxNormalSlots, int maxMineSlots, Point position, PlanetType type) {
		this.radius = radius;
		this.team = team;
		this.maxNormalSlots = maxNormalSlots;
		this.maxMineSlots = maxMineSlots;
		this.position = position;
		this.type = type;
		normalSlots = new LinkedList<>();
		mineSlots = new LinkedList<>();
		if(type == PlanetType.station)this.maxMineSlots = 0;
	}

	public void update(double time){
		// let armies fight/bombard
		if(armies.size() >= 2){
			// there are more than 1 army on this planet. let them fight!
			Army.fight(armies, time);
		} else {
			// there is one or no army here. bombard planet?
			if(!armies.isEmpty()){
				Army a = armies.getFirst();
				if(a.team != team) a.bombardPlanet(this, time);
			}
		}
		// regenerate planet hp
		hp = Math.min(hp + HP_REGENERATION*time, MAX_HP);
		// update buildings
		for(Building b : normalSlots) b.update(time);
	}
	
	public void receiveArmy(Army a){
		for(Army a2 : armies) if(a.team == a2.team){
			a2.add(a);
			return;
		}
		armies.add(a);
	}
	
	private Army split(Team team, double[] ratios){
		Army a = null;
		for(Army a2 : armies) if(a2.team == team){
			a = a2;
			break;
		}
		return a == null ? null : a.split(ratios);
	}
	
	public Army sendArmy(Team team, double[] ratios, Point target){
		Army a = split(team, ratios);
		if(a == null) return null;
		a.targetPoint = target;
		return a;
	}
	
	public Army sendArmy(Team team, double[] ratios, Planet target){
		if(target == this) return null;
		Army a = split(team, ratios);
		if(a == null) return null;
		a.targetPlanet = target;
		return a;
	}
	
	public int getWorkingWorkers(Team t){
		int workers=0;
		int buildings=0;
		for(Army a:armies){
			if(a.team == t) workers = (int)Math.ceil(a.ships[0]);	
		}
		if(workers == 0) return 0;
		for(Building b:normalSlots){
			if(b.team == t && (b.buildProgress < 1 || b.hp < b.getMaxHp())) buildings++;
		}
		for(Building b:mineSlots){
			if(b.team == t && (b.buildProgress < 1 || b.hp < b.getMaxHp())) buildings++;
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
			b.factoryProgress += Building.FACTORY_SPICE_USAGE / Army.cost[i] * efficiency * time;
			int n = (int)b.factoryProgress;
			if(n >= 0){
				Army a = getArmy(t);
				if(a == null){
					a = new Army(t);
					a.ships[i] += n;
					armies.add(a);
				}
				b.factoryProgress -= n;
			}
		}
		// build buildings with workers
		int workerCount = getWorkingWorkers(t);
		LinkedList<Building> l = (LinkedList<Building>)normalSlots.clone();
		l.addAll(mineSlots);
		for(Building b : l) if(b.team == t && b.buildProgress < 1){
			if(workerCount <= 0) break;
			int w = Math.min(workerCount, Building.MAX_WORKERS_PER_BUILDING);
			workerCount -= w;
			double rate = w * Building.WORKER_SPICE_USAGE / b.getCost() * efficiency * time;
			if(rate + b.buildProgress > 1) rate = 1 - b.buildProgress;
			b.buildProgress += rate;
			b.hp += rate * b.getMaxHp();
			if(b.buildProgress >= 1) buildingFinished(b);
		}
		// repair buildings with the remaining workers
		for(Building b : l) if(b.team == t && b.buildProgress >= 1 && b.hp < b.getMaxHp()){
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
	
	private void buildingFinished(Building b){
		switch(b.type){
			case spiceMine:
				b.team.spiceIncome += Building.MINE_INCOME;
				break;
			case generator:
				b.team.energyIncome += Building.GENERATOR_INCOME;
				break;
			case spiceSilo:
				b.team.maxSpiceStorage += Building.SILO_STORAGE;
				break;
			case battery:
				b.team.maxEnergyStorage += Building.BATTERY_STORAGE;
				break;
			case hq:
				hasHQ = true;
				// remove all hqs that are not of this team (any unfinished buildings)
				ListIterator<Building> iter = normalSlots.listIterator();
				while(iter.hasNext()){
					Building b2 = iter.next();
					if(b2.type == Building.BuildingType.hq && b2.team != b.team){
						iter.remove();
					}
				}
				// reset team of planet and all buildings
				team = b.team;
				for(Building b2 : normalSlots) b2.team = team;
				for(Building b2 : mineSlots) b2.team = team;
				break;
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
		addBuildingInternal(new Building(t, team), t == Building.BuildingType.spiceMine ? mineSlots : normalSlots);
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
		if(b.type == Building.BuildingType.hq){
			hasHQ = false;
			team = SpiceWars.teamNeutral;
		} else {
			if(b.type == Building.BuildingType.spiceMine){
				mineSlots.remove(b);
				team.spiceIncome -= Building.MINE_INCOME;
			} 
			else if(b.type == Building.BuildingType.generator){
				normalSlots.remove(b);
				team.energyIncome -= Building.GENERATOR_INCOME;
			} else {
				normalSlots.remove(b);
			}
		}
	}
	
	public void onDelete( ){
			//destroy planet
			for(Building b : mineSlots) destroyBuilding(b); 
			for(Building b : normalSlots){
				if(b.type == Building.BuildingType.generator || b.type == Building.BuildingType.spiceMine){
					destroyBuilding(b);
				}
			}
	}
}
