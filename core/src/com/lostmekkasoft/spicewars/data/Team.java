/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

import com.badlogic.gdx.graphics.Color;

/**
 *
 * @author LostMekka
 */
public class Team {
	public int id;
	public Color color;
	public int maxSpiceStorage = Building.DEFAULT_SPICE_STORAGE;
	public int maxEnergyStorage = Building.DEFAULT_ENERGY_STORAGE;
	public double spiceStored = 0;
	public double spiceIncome = 0;
	public double energyStored = 0;
	public double energyIncome = 0;
	public double lastEfficiency = 1;
	
	public Team(int id, Color color) {
		this.id = id;
		this.color = color;
	}
	
	public boolean isNeutral(){
		return id < 0;
	}
	
}
