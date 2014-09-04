/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

import com.lostmekkasoft.spicewars.GameplayScreen;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author LostMekka
 */
public class Location {

	public Point position;
	public int radius = 20;
	public LinkedList<Army> armies = new LinkedList<>();
	private GameplayScreen parent;

	public Location(Point position, GameplayScreen parentScreen) {
		this.position = position;
		parent = parentScreen;
	}

	public GameplayScreen getParent() {
		return parent;
	}
	
	public void update(double time){
		// let armies fight/bombard
		if(armies.size() >= 2){
			// there are more than 1 army on this planet. let them fight!
			Army.fight(armies, time);
			ListIterator<Army> i = armies.listIterator();
			while(i.hasNext()){
				if(i.next().isEmpty()) i.remove();
			}
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
	
	public boolean sendArmy(Team team, double[] ratios, Point targetPoint){
		Location l = new Location(position, parent);
		return sendArmy(team, ratios, l);
	}
	
	public boolean sendArmy(Team team, double[] ratios, Location target){
		if(target == this) return false;
		Army a = split(team, ratios);
		if(a == null) return false;
		a.target = target;
		parent.addMovingArmy(a);
		return true;
	}
	
	public boolean hasArmies(){
		return !armies.isEmpty();
	}
	
	public boolean overlapsWith(Location l){
		double dd1 = position.squaredDistanceTo(l.position);
		double d2 = radius + l.radius;
		return dd1 <= d2*d2;
	}
	
	public void transferAllArmiesTo(Location l){
		for(Army a : armies) l.receiveArmy(a);
		armies.clear();
	}
	
}
