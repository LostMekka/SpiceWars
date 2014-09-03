/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

import com.badlogic.gdx.graphics.Color;
import com.lostmekkasoft.spicewars.SpiceWars;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author LostMekka
 */
public class Planet {

	public int radius;
	public Team team;
	public int maxNormalSlots;
	public int maxMineSlots;
	public LinkedList<Building> normalSlots;
	public LinkedList<Building> mineSlots;
	public double hp = 100;
	public Point position;
	private boolean hasHQ = false;

	public Planet(int radius, Team team, int maxNormalSlots, int maxMineSlots, Point position) {
		this.radius = radius;
		this.team = team;
		this.maxNormalSlots = maxNormalSlots;
		this.maxMineSlots = maxMineSlots;
		this.position = position;
		normalSlots = new LinkedList<>();
		mineSlots = new LinkedList<>();
	}

	public static enum PlanetType {
		normal, station
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
		if(!canAddBuilding(t)) return false;
		if (t == Building.BuildingType.spiceMine) {
			addBuildingInternal(new Building(t), mineSlots);
		} else {
			if (t == Building.BuildingType.hq) hasHQ = true;
			addBuildingInternal(new Building(t), normalSlots);
		}
		return true;
	}
	
	private void addBuildingInternal(Building b, LinkedList<Building> l){
		ListIterator<Building> i = l.listIterator();
		while(i.hasNext()){
			Building b2 = i.next();
			if(b.type.ordinal() > b2.type.ordinal()) continue;
			i.previous();
			i.add(b);
			return;
		}
		l.addLast(b);
	}

	public void damageRandomBuilding(double damage, double accuracy){
		if(!hasHQ) return; // do not damage neutral planets
		Building b;
		if(accuracy >= 1){
			b = normalSlots.getFirst();
		} else if(accuracy <= 0){
			int r = SpiceWars.random.nextInt(normalSlots.size() + mineSlots.size());
			if(r < normalSlots.size()){
				b = normalSlots.get(r);
			} else {
				b = mineSlots.get(r - normalSlots.size());
			}
		} else {
			double r = accuracy + (1-accuracy)*(normalSlots.size() - 1 + mineSlots.size());
			r *= SpiceWars.random.nextDouble();
			if(r < accuracy){
				b = normalSlots.getFirst();
			} else {
				r -= accuracy;
				int i = (int)(r/(1 - accuracy)) + 1;
				if(i < normalSlots.size()){
					b = normalSlots.get(i);
				} else {
					b = mineSlots.get(i - normalSlots.size());
				}
			}
		}
		b.hp -= damage;
		if(b.hp <= 0){
			if(b.type == Building.BuildingType.hq){
				hasHQ = false;
				team = SpiceWars.teamNeutral;
			}
		}
	}
	
}
