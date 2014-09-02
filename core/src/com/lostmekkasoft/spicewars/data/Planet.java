/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

import com.badlogic.gdx.graphics.Color;

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

	public Planet(int radius, Team team, int maxNormalSlots, int maxMineSlots, Point position) {
		this.radius = radius;
		this.team = team;
		this.maxNormalSlots = maxNormalSlots;
		this.maxMineSlots = maxMineSlots;
		this.position = position;
		this.normalSlots = new ArrayList<>(this.maxNormalSlots);
		this.mineSlots = new ArrayList<>(this.maxMineSlots);
	}

	// this is only for random dummy planets, please don't hate me
	public Planet(int radius, Point position) {
		this.radius = radius;
		this.position = position;
		this.team = new Team(1, new Color(Color.CYAN));
		this.maxNormalSlots = 0;
		this.maxMineSlots = 0;
		this.normalSlots = null;
		this.mineSlots = null;
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
			if (this.canAddBuilding(t)) {
				if (t == Building.BuildingType.hq) {
					hasHQ = true;
				}
				Building build = new Building(t);
				this.normalSlots.add(build);
				return true;
			}
			return false;
		} else {
			if (this.canAddBuilding(Building.BuildingType.spiceMine)) {
				Building mine = new Building(Building.BuildingType.spiceMine);
				this.mineSlots.add(mine);
				return true;
			}
			return false;
		}
	}

	public boolean canRemoveBuilding(Building.BuildingType t){
		return true;
	}
	
	public boolean removeBuilding(Building.BuildingType t){
		return true;
	}
}
