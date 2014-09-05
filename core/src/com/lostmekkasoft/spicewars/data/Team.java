/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

import com.badlogic.gdx.graphics.Color;
import com.lostmekkasoft.spicewars.AIPlayer;
import com.lostmekkasoft.spicewars.SpiceWars;

/**
 *
 * @author LostMekka
 */
public class Team {
	public int id;
	public Color color;
	public int maxSpiceStorage = Building.DEFAULT_SPICE_STORAGE;
	public int maxEnergyStorage = Building.DEFAULT_ENERGY_STORAGE;
	public double spiceStored = maxSpiceStorage * 0.75;
	public double spiceIncome = 0;
	public double energyStored = maxEnergyStorage * 0.75;
	public double energyIncome = 0;
	public double lastEfficiency = 1, lastSpiceEfficiency = 1, lastEnergyEfficiency = 1;
	public double lastSpiceUsage = 0, lastEnergyUsage = 0;
	public double lastSpiceDelta = 0, lastEnergyDelta = 0;
	public AIPlayer ai = null;

	public static Team createAITeam(int id, Color c, SpiceWars game){
		Team t = new Team(id, c);
		t.ai = new AIPlayer(game, t);
		return t;
	}
	
	public Team(int id, Color color) {
		this.id = id;
		this.color = color;
	}
	
	public boolean isAITeam(){
		return ai != null;
	}
	
	public boolean isNeutral(){
		return id < 0;
	}
	
}
