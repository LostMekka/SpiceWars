/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

import com.lostmekkasoft.spicewars.SpiceWars;

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
	private SpiceWars game;

	public Location(Point position, SpiceWars parent) {
		this.position = position;
		this.game = parent;
	}

	public SpiceWars getGame() {
		return game;
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
		Location l = new Location(position, game);
		return sendArmy(team, ratios, l);
	}
	
	public boolean sendArmy(Team team, double[] ratios, Location target){
		if(target == this) return false;
		Army a = split(team, ratios);
		if(a == null) return false;
		a.target = target;
		a.position = position.clone();
		game.addMovingArmy(a);
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
	
	public Army getArmy(Team t){
		for(Army a : armies) if(a.team == t) return a;
		return null;
	}
	

	public boolean buildStation(Team team){
		Army army = getArmy(team);
		if(army.ships[0] == 0)return false;
		Planet planet = new Planet(Planet.STATION_RADIUS, team, Planet.STATION_NORMAL_SLOTS, 0,position, Planet.PlanetType.station, game);
		game.addPlanet(planet);
		return true;
	}
}
