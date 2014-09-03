/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

/**
 *
 * @author LostMekka
 */
public class Building {
	
	public static enum BuildingType{ hq, 
		fabberFactory, fighterFactory, frigateFactory, destroyerFactory,
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
	public static final double SILO_STORAGE = 100;
	public static final double BATTERY_STORAGE = 500;
	public static final double DEFAULT_SPICE_STORAGE = 50;
	public static final double DEFAULT_ENERGY_STORAGE = 200;

	public BuildingType type;
	public double hp = 100;
	public double buildState = 0;
	public boolean isActive = false;

	public Building(BuildingType type) {
		this.type = type;
	}
	
	public void update(double time){
		
	}
	
	public boolean isMine(){
		return type == BuildingType.spiceMine;
	}
	
}
