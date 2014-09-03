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
public class Army {
	
	// order in arrays: worker, fighter, frigate, destroyer
	public static final double[] accuracy = new double[]{
		0.0, 0.8, 0.5, 0.2
	};
	public static final double[] cost = new double[]{
		20.0, 5.0, 40.0, 100.0
	};
	public static final double[] dps = new double[]{
		0.0, cost[1]*1.0, cost[2]*1.2, cost[3]*1.4
	};
	public static final double[][] dpsMods = new double[][]{
		{0.0, 0.0, 0.0, 0.0},
		{2.0, 1.0, 0.5, 1.5},
		{2.0, 1.5, 1.0, 0.5},
		{2.0, 0.5, 1.5, 1.0}
	};
	public static final int[][] priorities = new int[][]{
		{0, 0, 0, 0},
		{3, 1, 2, 0},
		{1, 2, 3, 0},
		{2, 3, 1, 0}
	};
	
	public double[] ships = new double[4];
	
	public static void fight(LinkedList<Army> armies, double time){
		int s = armies.size();
		if(s <= 1) return;
		Army[] oldArmies = new Army[s];
		oldArmies = armies.toArray(oldArmies);
		double[][] newArmies = new double[s][4];
		for(int i=0; i<s; i++) System.arraycopy(oldArmies[i].ships, 0, newArmies[i], 0, 4);
		for(int srcI=1; srcI<4; srcI++){
			for(int army=0; army<s; army++){
				double dmg = Math.ceil(oldArmies[army].ships[srcI]) * dps[srcI] * time;
				for(int trgtI : priorities[srcI]){
					double modDmg = dmg * dpsMods[srcI][trgtI];
					double totalhp = 0;
					for(int i=0; i<s; i++) if(i != army) totalhp = oldArmies[i].ships[trgtI];
					if(modDmg > totalhp){
						for(int i=0; i<s; i++) if(i != army) newArmies[i][trgtI] = 0;
						dmg -= totalhp / dpsMods[srcI][trgtI];
					} else {
						for(int i=0; i<s; i++) if(i != army){
							newArmies[i][trgtI] -= modDmg * newArmies[i][trgtI] / totalhp;
						}
						dmg = 0;
					}
				}
			}
		}
		for(int i=0; i<s; i++) oldArmies[i].ships = newArmies[i];
	}
	
	public void fight(Army a, double time){
		double[] own = new double[4];
		double[] enemy = new double[4];
		for(int srcI=1; srcI<4; srcI++){
			double dmg1 = Math.ceil(ships[srcI]) * dps[srcI] * time;
			double dmg2 = Math.ceil(ships[srcI]) * dps[srcI] * time;
			for(int trgtI : priorities[srcI]){
				if(dmg1 * dpsMods[srcI][trgtI] > a.ships[trgtI]){
					enemy[trgtI] = 0;
					dmg1 -= a.ships[trgtI] / dpsMods[srcI][trgtI];
				} else {
					enemy[trgtI] = a.ships[trgtI] - dmg1 * dpsMods[srcI][trgtI];
					dmg1 = 0;
				}
				if(dmg2 * dpsMods[srcI][trgtI] > ships[trgtI]){
					own[trgtI] = 0;
					dmg2 -= ships[trgtI] / dpsMods[srcI][trgtI];
				} else {
					own[trgtI] = ships[trgtI] - dmg2 * dpsMods[srcI][trgtI];
					dmg2 = 0;
				}
			}
		}
		ships = own;
		a.ships = enemy;
	}
	
	public void bombardPlanet(Planet p, double time){
		for(int i=1; i<4; i++) if(!p.team.isNeutral()){
			p.damageRandomBuilding(Math.ceil(ships[i]) * dps[i] * time, accuracy[i]);
		}
	}
	
}
