/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars;

import com.lostmekkasoft.spicewars.data.Planet;
import com.lostmekkasoft.spicewars.data.Team;

/**
 *
 * @author LostMekka
 */
public class AIPlayer {
	
	private SpiceWars game;
	private Team team;
	private Planet home = null, p = null;

	public AIPlayer(SpiceWars game, Team team) {
		this.game = game;
		this.team = team;
	}

	public void update(double time){
		if(home == null){
			home = game.getOwnPlanets(team).getFirst();
			p = game.getNeutralPlanets().getFirst();
		}
		if(game.getOwnMovingArmies(team).isEmpty()){
			Planet p1 = home.hasArmies() ? home : p;
			Planet p2 = home.hasArmies() ? p : home;
			p1.sendArmy(team, new double[]{1,1,1,1}, p2);
		}
	}
	
}
