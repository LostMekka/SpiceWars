/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lostmekkasoft.spicewars.data;

/**
 *
 * @author fine
 */
public class Point {
	public double x = 0;
	public double y = 0;
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Point(){	}
	
	public double squaredDistanceTo(Point point){
		 double distx = point.x - this.x ;
		 double disty = point.y - this.y ;
		 return distx*distx + disty*disty;
	}
	
	public double distanceTo(Point point){
		double distx = point.x - this.x ;
		double disty = point.y - this.y ;
		double square = distx*distx + disty*disty;
		return Math.sqrt(square);
	}
	
	public double length(){
		double square = this.x*this.x + this.y*this.y;
		return Math.sqrt(square);
	}
	
	public double squareLength(){
		return this.x*this.x + this.y*this.y;
	}
	
	@Override
	public Point clone(){
		return new Point(x, y);
	}
	
	public void add(Point p){
		x += p.x;
		y += p.y;
	}
	
	public void subtract(Point p){
		x -= p.x;
		y -= p.y;
	}
	
	public void multiply(double value){
		x *= value;
		y *= value;
	}
	
	public void set(Point p){
		x = p.x;
		y = p.y;
	}
	
	public boolean moveTo(Point p, double length, double targetRadius){
		double l = length + targetRadius;
		if(squaredDistanceTo(p) <= l*l){
			double d = distanceTo(p);
			Point p2 = p.clone();
			p2.subtract(this);
			p2.multiply((d-targetRadius) / d);
			add(p2);
			return true;
		} else {
			Point p2 = p.clone();
			p2.subtract(this);
			p2.multiply(length / p2.length());
			add(p2);
			return false;
		}
	}
	
}
