/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars.data;

/**
 *
 * @author LostMekka
 */
public class Army {
	
	// worker, fighter, frigate, destroyer
	private double[][] dpsMods = new double[][]{
		{0.0, 0.0, 0.0, 0.0},
		{2.0, 1.0, 0.5, 1.5},
		{2.0, 1.5, 1.0, 0.5},
		{2.0, 0.5, 1.5, 1.0}
	};
	
	public double workers, fighters, frigates, destroyers;
	
}
