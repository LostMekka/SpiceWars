/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

import java.util.LinkedList;

/**
 *
 * @author LostMekka
 */
public class Planet {
	public int radius;
	public Team team;
	public int maxNormalSlots;
	public int maxMineSlots;
	public LinkedList<Building> normalSlots;
	public LinkedList<Building> mineSlots;
	public double hp = 100;
	public Point position;
	
	public static enum PlanetType{
		normal,station
	}
	
}
