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
	public int maxStorage;
	public int maxEnergy;
	public float spiceStored = 0;
	public float spiceIncome = 0;
	public float energyStored = 0;
	public float energyIncome = 0;
	
	
	public Team(int id, Color color) {
		this.id = id;
		this.color = color;
	}
	
}
