/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

import com.lostmekkasoft.spicewars.actors.ArmyActor;

import java.util.LinkedList;

/**
 *
 * @author LostMekka
 */
public class Army {

	public ArmyActor actor;
	
	// order in arrays: worker, fighter, frigate, destroyer
	public static final double speedBase = 50;
	public static final double[] speedMod = new double[]{
		1.0, 1.5, 0.9, 0.5
	};
	public static final double[] accuracy = new double[]{
		0.0, 0.8, 0.5, 0.2
	};
	public static final double[] cost = new double[]{
		20.0, 5.0, 40.0, 90.0
	};
	public static final double[] dps = new double[]{
		0.0, cost[1]*0.02, cost[2]*0.024, cost[3]*0.028
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

	public Army(Team team) {
		this.team = team;
	}
	
	public double[] ships = new double[4];
	public Team team;
	public Point position = null;
	public Location target = null;
	
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
				if(dmg == 0) continue;
				for(int trgtI : priorities[srcI]){
					double modDmg = dmg * dpsMods[srcI][trgtI];
					double totalhp = 0;
					for(int i=0; i<s; i++) if(i != army) totalhp = oldArmies[i].ships[trgtI];
					if(totalhp == 0) continue;
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
	
	public boolean update(double time){
		if(target == null) return false; // nothing to do here
		// get length traveled (army moves at speed of slowest ship)
		double len = speedMod[1];
		if(ships[0] > 0) len = speedMod[0];
		if(ships[2] > 0) len = speedMod[2];
		if(ships[3] > 0) len = speedMod[3];
		len *= speedBase * time;
		if(position.moveTo(target.position, len, target.radius)){
			// arrived, add to destination
			target.receiveArmy(this);
			target = null;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString(){
		return String.format("%d, %d, %d, %d", 
				(int)Math.ceil(ships[0]), (int)Math.ceil(ships[1]), 
				(int)Math.ceil(ships[2]), (int)Math.ceil(ships[3]));
	}
	
	public String toDoubleString(){
		return String.format("%f, %f, %f, %f", ships[0], ships[1], ships[2], ships[3]);
	}
	
	public void bombardPlanet(Planet p, double time){
		for(int i=1; i<4; i++) if(!p.team.isNeutral()){
			p.damageRandomBuilding(Math.ceil(ships[i]) * dps[i] * time, accuracy[i]);
			p.hp -= dps[i] * (1 - accuracy[i]) * 0.1;
		}
	}
	
	public boolean add(Army a){
		if(team != a.team) return false;
		for(int i=0; i<4; i++){
			double s1 = Math.ceil(ships[i]);
			double s2 = Math.ceil(a.ships[i]);
			ships[i] += a.ships[i];
			if(Math.ceil(ships[i]) < s1+s2) ships[i] = Math.ceil(ships[i])+0.01;
			a.ships[i] = 0;
		}
		return true;
	}
	
	public Army split(double[] ratio){
		Army a = new Army(team);
		boolean b = false;
		for(int i=0; i<4; i++){
			double n = Math.round(Math.ceil(ships[i]) * ratio[i]);
			if(n == 0){
				a.ships[i] = 0;
			} else if(a.ships[i] == 1){
				a.ships[i] = ships[i];
				ships[i] = 0;
			} else {
				a.ships[i] = n;
				ships[i] = Math.ceil(ships[i]) - n;
			}
			if(n > 0) b = true;
		}
		return b ? a : null;
	}
	
	public boolean isEmpty(){
		return ships[0] <= 0 && ships[1] <= 0 && ships[2] <= 0 && ships[3] <= 0;
	}
	
}
