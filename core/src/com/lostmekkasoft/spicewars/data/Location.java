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
public class Location {

	public Point position;
	public int radius;
	public LinkedList<Army> armies = new LinkedList<>();

	public Location(Point position, int radius) {
		this.position = position;
		this.radius = radius;
	}
	
	public void update(double time){
		// let armies fight/bombard
		if(armies.size() >= 2){
			// there are more than 1 army on this planet. let them fight!
			Army.fight(armies, time);
		}
	}
	
	public void receiveArmy(Army a){
		for(Army a2 : armies) if(a.team == a2.team){
			a2.add(a);
			return;
		}
		armies.add(a);
	}
	
	private Army split(Team team, double[] ratios){
		Army a = null;
		for(Army a2 : armies) if(a2.team == team){
			a = a2;
			break;
		}
		return a == null ? null : a.split(ratios);
	}
	
	public Army sendArmy(Team team, double[] ratios, Point target){
		Army a = split(team, ratios);
		if(a == null) return null;
		a.targetPoint = target;
		return a;
	}
	
	public Army sendArmy(Team team, double[] ratios, Planet target){
		if(target == this) return null;
		Army a = split(team, ratios);
		if(a == null) return null;
		a.targetPlanet = target;
		return a;
	}
	
	public boolean hasArmies(){
		return !armies.isEmpty();
	}
	
	public boolean overlapsWith(Location l){
		double dd1 = position.squaredDistanceTo(l.position);
		double d2 = radius + l.radius;
		return dd1 <= d2*d2;
	}
	
}
