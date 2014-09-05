/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;


import com.lostmekkasoft.spicewars.SpiceWars;

/**
 *
 * @author LostMekka
 */
public final class Building {
	
	public static enum BuildingType{ hq, 
		spiceMine, generator, spiceSilo, battery, 
		workerFactory, fighterFactory, frigateFactory, destroyerFactory,
		artillery, deathlaser
	}
	
	public static final int MAX_WORKERS_PER_BUILDING = 5;
	public static final double MINE_INCOME = 3.5;
	public static final double GENERATOR_INCOME = 8.5;
	public static final double FACTORY_SPICE_USAGE = 2.5;
	public static final double FACTORY_ENERGY_USAGE = 5;
	public static final double WORKER_SPICE_USAGE = 1;
	public static final double WORKER_ENERGY_USAGE = 4;
	public static final int SILO_STORAGE = 250;
	public static final int BATTERY_STORAGE = 500;
	public static final int DEFAULT_SPICE_STORAGE = 50;
	public static final int DEFAULT_ENERGY_STORAGE = 200;
	public static final double ARTILLERY_SHOTDELAY = 10;
	public static final double DEATHLASER_SHOTDELAY = 30;

	public BuildingType type;
	public double hp;
	public double progress = 0;
	public boolean isActive = true, isFinishedBuilding = false;
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
	
	public SpiceWars getGame() {
		return parent.getGame();
	}
	
	public int getMaxHp(){
		return 100;
	}
	
	public int getCost(){
		switch(type){
			case hq: return 150;
			case workerFactory: 
			case fighterFactory: return 100;
			case frigateFactory: return 150;
			case destroyerFactory: return 200;
			case generator: 
			case spiceSilo:
			case battery: return 75;
			case spiceMine: return 50;
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
				Projectile p = new Projectile(parent, target, Projectile.ProjectileType.artilleryShell, team);
				getGame().addProjectile(p);
				progress = 0;
			}
		}
		if(type == BuildingType.deathlaser && isFinishedBuilding){
			progress = Math.min(1, progress + time/DEATHLASER_SHOTDELAY);
			if(progress >= 1 && target != null){
				Projectile p = new Projectile(parent, target, Projectile.ProjectileType.deathLaser, team);
				getGame().addProjectile(p);
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
	
	public boolean isFactory(){
		return type == BuildingType.workerFactory ||
				type == BuildingType.fighterFactory ||
				type == BuildingType.frigateFactory ||
				type == BuildingType.destroyerFactory;
	}
	
}
