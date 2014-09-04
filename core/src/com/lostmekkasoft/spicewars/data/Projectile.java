/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

/**
 *
 * @author LostMekka
 */
public class Projectile {
	
	public static enum ProjectileType{
		artilleryShell, deathLaser
	}
	
	public Point position;
	public Planet target;
	public ProjectileType type;
	public Team team;

	public Projectile(Planet source, Planet target, ProjectileType type, Team team) {
		this.target = target;
		this.type = type;
		this.position = source.position.clone();
		this.team = team;
	}
	
	public double getSpeed(){
		switch(type){
			case artilleryShell: return 100;
			case deathLaser: return 300;
		}
		throw new RuntimeException("projectile type not recognized!");
	}
	
	public double getPlanetDamage(){
		switch(type){
			case artilleryShell: return 10;
			case deathLaser: return 50;
		}
		throw new RuntimeException("projectile type not recognized!");
	}
	
	public double getBuildingDamage(){
		switch(type){
			case artilleryShell: return 45;
			case deathLaser: return 80;
		}
		throw new RuntimeException("projectile type not recognized!");
	}
	
	public boolean update(double time){
		double len = getSpeed() * time;
		if(position.moveTo(target.position, len, target.radius)){
			// arrived, deal damage to destination
			target.hp -= getPlanetDamage();
			target.damageRandomBuilding(getBuildingDamage(), 0);
			return true;
		}
		return false;
	}
	
}
