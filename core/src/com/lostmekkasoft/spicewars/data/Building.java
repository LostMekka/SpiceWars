/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

import com.lostmekkasoft.spicewars.GameplayScreen;

/**
 *
 * @author LostMekka
 */
public final class Building {
	
	public static enum BuildingType{ hq, 
		workerFactory, fighterFactory, frigateFactory, destroyerFactory,
		spiceMine, spiceSilo, generator, battery, 
		artillery, deathlaser
	}
	
	public static final int MAX_WORKERS_PER_BUILDING = 5;
	public static final double MINE_INCOME = 5;
	public static final double GENERATOR_INCOME = 10;
	public static final double FACTORY_SPICE_USAGE = 2;
	public static final double FACTORY_ENERGY_USAGE = 6;
	public static final double WORKER_SPICE_USAGE = 1;
	public static final double WORKER_ENERGY_USAGE = 8;
	public static final int SILO_STORAGE = 100;
	public static final int BATTERY_STORAGE = 500;
	public static final int DEFAULT_SPICE_STORAGE = 50;
	public static final int DEFAULT_ENERGY_STORAGE = 200;
	public static final double ARTILLERY_SHOTDELAY = 10;
	public static final double DEATHLASER_SHOTDELAY = 30;

	public BuildingType type;
	public double hp;
	public double progress = 0;
	public boolean isActive = false, isFinishedBuilding;
	public Team team;
	private Planet parent;

	public Building(BuildingType type, Team team, Planet parentPlanet) {
		this.type = type;
		this.team = team;
		parent = parentPlanet;
		hp = 1; // hp is low at the beginning of construction
	}

	public Planet getParent() {
		return parent;
	}
	
	public GameplayScreen getParentScreen() {
		return parent.getParent();
	}
	
	public int getMaxHp(){
		return 100;
	}
	
	public int getCost(){
		switch(type){
			case hq: return 150;
			case workerFactory: 
			case fighterFactory:
			case frigateFactory:
			case destroyerFactory: return 180;
			case generator: 
			case spiceSilo:
			case battery: return 130;
			case spiceMine: return 60;
			case artillery: return 500;
			case deathlaser: return 2000;
			default: throw new RuntimeException();		
		}
	}
	
	public void update(double time){
		Planet target = parent.getSuperWeaponTarget();
		if(type == BuildingType.artillery && isFinishedBuilding){
			progress = Math.min(1, progress + time/ARTILLERY_SHOTDELAY);
			if(progress >= 1 && target != null){
				Projectile p = new Projectile(parent, target, Projectile.ProjectileType.artilleryShell);
				getParentScreen().addProjectile(p);
				progress = 0;
			}
		}
		if(type == BuildingType.deathlaser && isFinishedBuilding){
			progress = Math.min(1, progress + time/DEATHLASER_SHOTDELAY);
			if(progress >= 1 && target != null){
				Projectile p = new Projectile(parent, target, Projectile.ProjectileType.deathLaser);
				getParentScreen().addProjectile(p);
				progress = 0;
			}
		}
	}
	
	public boolean isMine(){
		return type == BuildingType.spiceMine;
	}
	
	private void addTeamEffect(Team t){
		switch(type){
			case spiceMine:
				t.spiceIncome += Building.MINE_INCOME;
				break;
			case spiceSilo:
				t.maxSpiceStorage += Building.SILO_STORAGE;
				break;
			case generator:
				t.energyIncome += Building.GENERATOR_INCOME;
				break;
			case battery:
				t.maxEnergyStorage += Building.BATTERY_STORAGE;
				break;
		}
	}
	
	private void removeTeamEffect(Team t){
		switch(type){
			case spiceMine:
				t.spiceIncome -= Building.MINE_INCOME;
				break;
			case spiceSilo:
				t.maxSpiceStorage -= Building.SILO_STORAGE;
				if(t.spiceStored > t.maxSpiceStorage) t.spiceStored = t.maxSpiceStorage;
				break;
			case generator:
				t.energyIncome -= Building.GENERATOR_INCOME;
				break;
			case battery:
				t.maxEnergyStorage -= Building.BATTERY_STORAGE;
				if(t.energyStored > t.maxEnergyStorage) t.energyStored = t.maxEnergyStorage;
				break;
		}
	}
	
	public void onDestroy(){
		if(isFinishedBuilding) removeTeamEffect(team);
	}
	
	public void onFinishBuilding(){
		progress = 0;
		isFinishedBuilding = true;
		addTeamEffect(team);
	}
	
	public void changeTeam(Team t){
		if(isFinishedBuilding) removeTeamEffect(team);
		team = t;
		if(isFinishedBuilding) addTeamEffect(team);
	}
	
}
