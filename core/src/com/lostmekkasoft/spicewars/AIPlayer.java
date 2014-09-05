/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars;

import com.lostmekkasoft.spicewars.data.Army;
import com.lostmekkasoft.spicewars.data.Building;
import com.lostmekkasoft.spicewars.data.Location;
import com.lostmekkasoft.spicewars.data.Planet;
import com.lostmekkasoft.spicewars.data.Team;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author LostMekka
 */
public class AIPlayer {
	
	
	
	private SpiceWars game;
	private Team team;
	private Planet targetPlanet = null;
	private boolean targetHasHQ = false;
	private Random ran = new Random();
	private double buildTimer = 0, invadeTimer;
	private boolean isFirst = true;

	public AIPlayer(SpiceWars game, Team team) {
		this.game = game;
		this.team = team;
		invadeTimer = 60 + 30*ran.nextDouble();
		buildTimer = 20 + 10*ran.nextDouble();
		invadeTimer = 1;
	}

	public void update(double time){
		if(isFirst){
			isFirst = false;
			Planet home = game.getOwnPlanets(team).getFirst();
			home.addBuilding(Building.BuildingType.generator, team);
			home.addBuilding(Building.BuildingType.workerFactory, team);
			home.addBuilding(Building.BuildingType.fighterFactory, team);
		}
		buildTimer -= time;
		if(buildTimer <= 0){
			buildTimer = 30 + 15*ran.nextDouble();
			planBuildings();
		}
		invadeTimer -= time;
		if(invadeTimer <= 0){
			invadeTimer = 25 + 15*ran.nextDouble();
			if(targetPlanet == null){
				chosePlanetToInvade();
			} else {
				cancelInvade();
			}
		}
		if(targetPlanet != null){
			if(targetPlanet.team == team){
				// we captured the planet! yaay!
				targetHasHQ = false;
				targetPlanet = null;
			} else {
				// planet is not captured yet. try to build a hq if possible
				if(!targetHasHQ) targetPlanet.addBuilding(Building.BuildingType.hq, team);
			}
		}
		redistributeUnits();
	}
	
	public void planBuildings(){
		int n = ran.nextInt(game.getOwnPlanets(team).size())+1;
		for(int i=0; i<n; i++){
			Building.BuildingType t = getDesiredBuildingType();
			LinkedList<Planet> l = new LinkedList<>();
			for(Planet p : game.getOwnPlanets(team)){
				boolean b;
				if(t == Building.BuildingType.spiceMine){
					b = p.mineSlots.size() < p.maxMineSlots;
				} else {
					b = p.normalSlots.size() < p.maxNormalSlots;
				}
				if(b){
					Army a = p.getArmy(team);
					if(a != null && a.ships[0] > 0) l.add(p);
				}
			}
			if(l.isEmpty()) return; // no place to build!
			Planet p = l.get(ran.nextInt(l.size()));
			p.addBuilding(t, team);
		}
	}
	
	public Building.BuildingType getDesiredBuildingType(){
		LinkedList<Building.BuildingType> l = new LinkedList<>();
		// first see if we need eco
		if(team.lastEnergyEfficiency < 1) l.add(Building.BuildingType.generator);
		if(team.lastSpiceEfficiency < 1) l.add(Building.BuildingType.spiceMine);
		if(!l.isEmpty()) return l.get(ran.nextInt(l.size()));
		// no eco needed, build random other stuff
		if(workersNeeded > 0) l.add(Building.BuildingType.workerFactory);
		l.add(Building.BuildingType.battery);
		l.add(Building.BuildingType.spiceSilo);
		l.add(Building.BuildingType.fighterFactory); // *3
		l.add(Building.BuildingType.fighterFactory);
		l.add(Building.BuildingType.fighterFactory);
		l.add(Building.BuildingType.frigateFactory); // *2
		l.add(Building.BuildingType.frigateFactory);
		l.add(Building.BuildingType.destroyerFactory); // *1
		return l.get(ran.nextInt(l.size()));
	}
	
	public void redistributeUnits(){
		redistributeArmy();
		redistributeWorkers();
	}
	
	private static final double[] ALL_ARMY = {0,1,1,1};
	public void redistributeArmy(){
		if(targetPlanet != null){
			for(Planet p : game.getOwnPlanets(team)){
				p.sendArmy(team, ALL_ARMY, targetPlanet);
			}
		}
	}
	
	private int workersNeeded = 0;
	public void redistributeWorkers(){
		HashMap<Planet, Integer> needWorkers = new HashMap<>();
		HashMap<Planet, Integer> haveWorkers = new HashMap<>();
		LinkedList<Planet> l = game.getOwnPlanets(team);
		if(targetPlanet != null) l.add(targetPlanet);
		int workersNeeded = 0;
		for(Planet p : l){
			int w = getWorkersNeeded(p);
			workersNeeded += w;
			if(w > 0) needWorkers.put(p, w);
			if(w < 0) haveWorkers.put(p, w);
		}
		while(!needWorkers.isEmpty() && !haveWorkers.isEmpty()){
			Planet p1 = null;
			for(Planet p2 : needWorkers.keySet()){ p1 = p2; break; }
			int w1 = needWorkers.get(p1);
			Planet p2 = getPlanetClosestTo(p1, haveWorkers.keySet());
			if(p2 == null) break;
			int w2 = haveWorkers.get(p2);
			int w = w1 + w2;
			if(w >= 0) haveWorkers.remove(p2);
			if(w <= 0) needWorkers.remove(p1);
			if(w > 0) needWorkers.put(p1, w);
			if(w < 0) haveWorkers.put(p2, w);
			int wsend = Math.min(w1, -w2);
			Army a = p2.getArmy(team);
			if(a == null || a.ships[0] <= 0){
				haveWorkers.remove(p2);
				continue;
			}
			double r = wsend / a.ships[0];
			p2.sendArmy(team, new double[]{r, 0, 0, 0}, p1);
		}
		if(!haveWorkers.isEmpty()){
			// get at least one worker to every planet
			l.clear();
			// get list of all own planets without workers on them and without workers traveling to them
			for(Planet p : game.getOwnPlanets(team)){
				Army a = p.getArmy(team);
				if(a == null || a.ships[0] <= 0){
					boolean add = true;
					for(Army a2 : game.armies){
						if(a2.target == p && a2.team == team && a2.ships[0] > 0){
							add = false;
							break;
						}
					}
					if(add) l.add(p);
				}
			}
			if(!l.isEmpty()){
				// distribute the surplus workers
				for(Planet p1 : haveWorkers.keySet()){
					int w = -haveWorkers.get(p1) - 1;
					for(int i=0; i<w; i++){
						Planet p2 = l.removeFirst();
						p1.sendArmy(team, new double[]{1.0/(w+1),0,0,0}, p2);
						w--;
						if(l.isEmpty()) break;
					}
					if(l.isEmpty()) break;
				}
			}
		}
	}
	
	public void checkIfTargetHasHQ(){
		targetHasHQ = false;
		if(targetPlanet == null) return;
		for(Building b : targetPlanet.normalSlots){
			if(b.type == Building.BuildingType.hq && b.team == team){
				targetHasHQ = true;
				return;
			}
		}
	}
	
	public void chosePlanetToInvade(){
		Planet p = getUnownedPlanetClosestToAll();
		if(p == null) return;
		double ea = getEnemyArmyValue(p);
		double own = getTotalArmyValue(team);
		if(own >= ea*1.5) targetPlanet = p;
	}
	
	public void cancelInvade(){
		if(targetPlanet == null || targetPlanet.getArmy(team) != null || !hasEnemyArmy(targetPlanet)) return;
		targetPlanet = null;
	}
	
	public boolean hasEnemyArmy(Location l){
		for(Army a : l.armies) if(a.team != team) return true;
		return false;
	}
	
	public int getWorkersNeeded(Planet p){
		return (p == targetPlanet && !hasEnemyArmy(p) ? Building.MAX_WORKERS_PER_BUILDING : p.getJobCount(team)) - getWorkersPresent(p);
	}
	
	public int getWorkersPresent(Planet p){
		int ans = 0;
		Army a = p.getArmy(team);
		if(a != null) ans += a.ships[0];
		for(Army aa : game.getOwnMovingArmies(team)) if(aa.target == p) ans += aa.ships[0];
		return ans;
	}
	
	public double getTotalArmyValue(Team t){
		double ans = 0;
		for(Planet p : game.planets) ans += getArmyValue(p, t);
		for(Location l : game.locations) ans += getArmyValue(l, t);
		for(Army a : game.armies) if(a.team == t) ans += getArmyValue(a);
		return ans;
	}
	
	public double getArmyValue(Location l, Team t){
		for(Army a : l.armies) if(a.team == t) return getArmyValue(a);
		return 0;
	}
	
	public double getEnemyArmyValue(Location l){
		double ans = 0;
		for(Army a : l.armies) if(a.team != team) ans = Math.max(ans, getArmyValue(a));
		return ans;
	}
	
	public double getOwnArmyValue(Location l){
		for(Army a : l.armies) if(a.team == team) return getArmyValue(a);
		return 0;
	}
	
	public double getArmyValue(Army a){
		return a.ships[1] + 2*a.ships[2] + 5*a.ships[3];
	}
	
	public Planet getUnownedPlanetClosestToAll(){
		double dd = Double.MAX_VALUE;
		Planet ans = null;
		LinkedList<Planet> ol = game.getOwnPlanets(team);
		LinkedList<Planet> ul = game.getEnemyPlanets(team);
		ul.addAll(game.getNeutralPlanets());
		for(Planet uop : ul){
			double dd2 = 0;
			for(Planet op : ol) dd2 += op.position.squaredDistanceTo(uop.position);
			if(dd2 < dd){
				dd = dd2;
				ans = uop;
			}
		}
		return ans;
	}
	
	public Planet getOwnPlanetClosestTo(Planet p){
		return getPlanetClosestTo(p, game.getOwnPlanets(team));
	}
	
	public Planet getPlanetClosestTo(Planet p, Collection<Planet> l){
		double dd = Double.MAX_VALUE;
		Planet ans = null;
		for(Planet op : l){
			double dd2 = op.position.squaredDistanceTo(p.position);
			if(dd2 < dd){
				dd = dd2;
				ans = op;
			}
		}
		return ans;
	}
	
	public int getBuildingCount(Building.BuildingType t, boolean finished){
		int ans = 0;
		for(Planet p : game.getOwnPlanets(team)){
			for(Building b : p.normalSlots) if(b.type == t && b.isFinishedBuilding == finished) ans++;
			for(Building b : p.mineSlots) if(b.type == t && b.isFinishedBuilding == finished) ans++;
		}
		return ans;
	}
	
}
