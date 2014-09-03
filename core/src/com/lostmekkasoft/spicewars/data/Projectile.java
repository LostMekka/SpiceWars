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
	public boolean arrived = false;

	public Projectile(Planet source, Planet target, ProjectileType type) {
		this.target = target;
		this.type = type;
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
	
	public void update(double time){
		if(arrived) return;
		double len = getSpeed() * time;
		double d1 = len + target.radius;
		double dd2 = position.squaredDistanceTo(target.position);
		if(dd2 <= d1*d1){
			// arrived, deal damage to destination
			arrived = true;
			target.hp -= getPlanetDamage();
			target.damageRandomBuilding(getBuildingDamage(), 0);
		} else {
			// not arrived, move
			Point p = target.position.clone();
			p.subtract(position);
			p.multiply(len / p.length());
			position.add(p);
		}
	}
	
}
