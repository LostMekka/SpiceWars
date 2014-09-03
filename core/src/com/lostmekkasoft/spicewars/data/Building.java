/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

/**
 *
 * @author LostMekka
 */
public final class Building {
	
	public static enum BuildingType{ hq, 
		workerFactory, fighterFactory, frigateFactory, destroyerFactory,
		spiceMine, spiceSilo, generator, battery, 
		artillery, 
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

	public BuildingType type;
	public double hp;
	public double buildProgress = 0, factoryProgress = 0;
	public boolean isActive = false;
	public Team team;

	public Building(BuildingType type, Team team) {
		this.type = type;
		this.team = team;
		hp = getMaxHp();
	}
	
	public int getMaxHp(){
		return 100;
	}
	
	public int getCost(){
		return 100;
	}
	
	public void update(double time){
		
	}
	
	public boolean isMine(){
		return type == BuildingType.spiceMine;
	}
	
}
