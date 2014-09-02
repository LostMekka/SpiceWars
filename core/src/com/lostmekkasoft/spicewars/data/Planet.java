/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

import java.util.ArrayList;

/**
 *
 * @author LostMekka
 */
public class Planet {

	public int radius;
	public Team team;
	public int maxNormalSlots;
	public int maxMineSlots;
	public ArrayList<Building> normalSlots;
	public ArrayList<Building> mineSlots;
	public double hp = 100;
	public Point position;
	private boolean hasHQ = false;

	public void Planet(int radius, Team team, int maxNormalSlots, int maxMineSlots, Point position) {
		this.radius = radius;
		this.team = team;
		this.maxNormalSlots = maxNormalSlots;
		this.maxMineSlots = maxMineSlots;
		this.position = position;
		this.normalSlots = new ArrayList<>(this.maxNormalSlots);
		this.mineSlots = new ArrayList<>(this.maxMineSlots);
	}

	public static enum PlanetType {
		normal, station
	}

	public boolean canAddBuilding(Building.BuildingType t) {
		if (t != Building.BuildingType.spiceMine) {
			return (!hasHQ || t != Building.BuildingType.hq)
					&& (this.maxNormalSlots > this.normalSlots.size());
		} else {
			if (this.maxMineSlots > this.mineSlots.size()) {
				return true;
			}
			return false;
		}
	}

	public boolean addBuilding(Building.BuildingType t) {
		if (t != Building.BuildingType.spiceMine) {
			if (this.canAdd(t)) {
				if (t == Building.BuildingType.hq) {
					hasHQ = true;
				}
				Building build = new Building(t);
				this.normalSlots.add(build);
				return true;
			}
			return false;
		} else {
			if (this.canAdd(Building.BuildingType.spiceMine)) {
				Building mine = new Building(Building.BuildingType.spiceMine);
				this.mineSlots.add(mine);
				return true;
			}
			return false;
		}
	}
	/*
	public boolean canRemoveBuilding(Building.BuildingType t){
		
	}
	
	public boolean removeBuilding(Building.BuildingType t){
		
	}
}
*/