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
	
	public BuildingType type;
	public double hp = 100;
	public double buildState = 0;
	public static final double singleMineIncome = 15;
	public static final double singleGeneratorIncome = 10; 

	public Building(BuildingType type) {
		this.type = type;
	}
	
	public boolean isMine(){
		return type == BuildingType.spiceMine;
	}
	
}
