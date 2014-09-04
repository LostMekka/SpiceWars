package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lostmekkasoft.spicewars.actors.ArmyActor;
import com.lostmekkasoft.spicewars.actors.PlanetActor;
import com.lostmekkasoft.spicewars.actors.SelectionActor;
import com.lostmekkasoft.spicewars.data.*;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class SpiceWars implements ApplicationListener {

	SpriteBatch batch;
	ShapeRenderer shapes;
	FreeTypeFontGenerator fontGenerator;
	BitmapFont font14;
	BitmapFont font48;
	public static Random random = new Random();

	int WIDTH;
	int HEIGHT;
	public static float planetTextureFactor = 0.595703125f;

	private Stage stage;

	float timeForInput = 0;

	public int numPlanets;
	public LinkedList<Planet> planets = new LinkedList<>();
	public LinkedList<Army> armies = new LinkedList<>();
	public LinkedList<Team> teams = new LinkedList<>();
	public LinkedList<Projectile> projectiles = new LinkedList<>();
	public LinkedList<Location> locations = new LinkedList<>();

	public static Team teamNeutral;
	public Team teamPlayer;
	public Team teamAI;

	public SelectionActor selectionActor;
	public Planet selectedPlanet;

	TextureAtlas textureAtlas;
	TextureRegion planetTexture;
	TextureRegion planetSelectionTexture;
	TextureRegion armyTexture;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapes = new ShapeRenderer();

		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/imagine_font/imagine_font.ttf"));
		fontParameter.size = 14;
		font14 = fontGenerator.generateFont(fontParameter);
		fontParameter.size = 48;
		font48 = fontGenerator.generateFont(fontParameter);

		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		// Create Teams
		teamNeutral = new Team(-1, new Color(0.6f, 0.6f, 0.6f, 1f));
		teamPlayer = new Team(1, new Color(0f, 0.8f, 0f, 1f));
		teamAI = Team.createAITeam(2, new Color(1f, 0.1f, 0.1f, 1f), this);
		teams.add(teamNeutral);
		teams.add(teamPlayer);
		teams.add(teamAI);

		// Set up stage and prepare for inputs
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		Gdx.input.setInputProcessor(stage);

		// Get and assign all three possible planet textures
		textureAtlas = new TextureAtlas("spritesheet.txt");
		planetTexture = textureAtlas.findRegion("planet");
		planetSelectionTexture = textureAtlas.findRegion("planetSelection");
		armyTexture = textureAtlas.findRegion("armies");

		newLevel();
	}

	public void update(float delta) {
		double time = delta;
		// tick ai players
		for(Team t : teams) if(t.isAITeam()) t.ai.update(time);
		// manage economy: build and repair buildings
		for(Team t : teams){
			double spiceUsage = 0, energyUsage = 0;
			for(Planet p : planets){
				int w = p.getWorkingWorkers(t);
				int f = p.getWorkingFactories(t);
				spiceUsage += f * Building.FACTORY_SPICE_USAGE * time;
				spiceUsage += w * Building.WORKER_SPICE_USAGE * time;
				energyUsage += f * Building.FACTORY_ENERGY_USAGE * time;
				energyUsage += w * Building.WORKER_ENERGY_USAGE * time;
			}
			double spiceDelta = t.spiceIncome * time - spiceUsage;
			double energyDelta = t.energyIncome * time - energyUsage;
			double effSp = Math.min(1, (t.spiceStored + t.spiceIncome * time) / spiceUsage);
			double effEn = Math.min(1, (t.energyStored + t.energyIncome * time) / energyUsage);
			double efficiency = Math.min(effSp, effEn);
			t.lastEfficiency = efficiency;
			for(Planet p : planets) p.buildStuff(t, efficiency, time);
			t.spiceStored = Math.min(t.spiceStored + spiceDelta*efficiency, t.maxSpiceStorage);
			t.energyStored = Math.min(t.energyStored + energyDelta*efficiency, t.maxEnergyStorage);
		}
		// move armies and projectiles
		ListIterator<Army> armyIter = armies.listIterator();
		while(armyIter.hasNext()){
			Army a = armyIter.next();
			if(a.update(time)){
				armyIter.remove();
				onArmyArrive(a);
			}
		}
		ListIterator<Projectile> projectileIter = projectiles.listIterator();
		while(projectileIter.hasNext()){
			Projectile p = projectileIter.next();
			if(p.update(time)){
				projectileIter.remove();
				onProjectileArrive(p);
			}
		}
		// update planets and remove destroyed ones
		ListIterator<Planet> planetIter = planets.listIterator();
		while(planetIter.hasNext()){
			Planet p = planetIter.next();
			p.update(time);
			if(p.hp <= 0){
				planetIter.remove();
				p.onDestroy();
				Location l = null;
				if(p.hasArmies()){
					l = new Location(p.position, this);
					p.transferAllArmiesTo(l);
				}
				for(Army a : armies) if(a.target == p){
					if(l == null) l = new Location(p.position, this);
					a.target = l;
				}
				if(l != null) addLocation(l);
			}
		}
		// remove orphan locations
		ListIterator<Location> locIter = locations.listIterator();
		while(locIter.hasNext()){
			Location l = locIter.next();
			if(l.hasArmies()) continue;
			for(Army a : armies) if(a.target == l) continue;
			locIter.remove();
		}
	}
	
	public LinkedList<Army> getOwnMovingArmies(Team team){
		LinkedList<Army> ans = new LinkedList<>();
		for(Army a : armies) if(a.team == team) ans.add(a);
		return ans;
	}
	
	public LinkedList<Army> getEnemyMovingArmies(Team team){
		LinkedList<Army> ans = new LinkedList<>();
		for(Army a : armies) if(a.team != team && a.team != teamNeutral) ans.add(a);
		return ans;
	}
	
	public LinkedList<Planet> getOwnPlanets(Team team){
		LinkedList<Planet> ans = new LinkedList<>();
		for(Planet p : planets) if(p.team == team) ans.add(p);
		return ans;
	}

	public LinkedList<Planet> getNeutralPlanets(){
		LinkedList<Planet> ans = new LinkedList<>();
		for(Planet p : planets) if(p.team == teamNeutral) ans.add(p);
		return ans;
	}

	public LinkedList<Planet> getEnemyPlanets(Team team){
		LinkedList<Planet> ans = new LinkedList<>();
		for(Planet p : planets) if(p.team != team && p.team != teamNeutral) ans.add(p);
		return ans;
	}

	public Location getCollidingLocation(Location l){
		for(Location l2 : planets) if(l != l2 && l.overlapsWith(l2)) return l2;
		for(Location l2 : locations) if(l != l2 && l.overlapsWith(l2)) return l2;
		return null;
	}

	public void addProjectile(Projectile p){
		projectiles.add(p);
	}

	public void addLocation(Location l){
		if(l instanceof Planet) throw new IllegalArgumentException();
		locations.add(l);
	}

	public void addMovingArmy(Army a){
		armies.add(a);
		Location l = getCollidingLocation(a.target);
		if(l != null && l instanceof Planet){
			a.target = l;
		} else {
			if(!(a.target instanceof Planet)) addLocation(a.target);
		}
		a.actor = new ArmyActor(a, armyTexture, (float)a.position.x, (float)a.position.y);
		stage.addActor(a.actor);
	}

	public void addPlanet(Planet p){
		planets.add(p);
		ListIterator<Location> i = locations.listIterator();
		while(i.hasNext()){
			Location l = i.next();
			if(l.overlapsWith(p)){
				// planet overlaps with a previously set location!
				// transfer all standing armies of that location to the planet
				l.transferAllArmiesTo(p);
				// redirect all armies on route to the location to the planet
				for(Army a : armies) if(a.target == l) a.target = p;
				// remove location
				i.remove();
			}
		}
	}
	
	public void onArmyArrive(Army a){
		a.actor.remove();
	}
	
	public void onProjectileArrive(Projectile p){
		
	}

	public Team getWinningTeam(){
		LinkedList<Team> l = new LinkedList<>();
		for(Planet p : planets){
			if(!p.team.isNeutral() && !l.contains(p.team)) l.add(p.team);
		}
		if(l.size() == 1) return l.getFirst();
		return null;
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());

		selectionActor.selectedPlanet = selectedPlanet;

		stage.draw();

		//DEBUG: Press R to generate a new playing field or ESC to exit the game
		timeForInput += Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.R) && timeForInput > 0.1) {
			newLevel();
			timeForInput = 0;
		} else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		//DEBUG: Write the amount of slots on each planet
		batch.begin();
		for (Planet planet : planets) {
			String planetSlots = String.format("R: %d - N:%d, M:%d", planet.radius, planet.maxNormalSlots, planet.maxMineSlots);
			drawCenteredString14(planetSlots, Color.GREEN, (float) planet.position.x, (float) planet.position.y);
		}
		batch.end();

		//DEBUG: Visualize certain points on the map
//		batch.begin();
//		drawCenteredString14("X", Color.MAGENTA, 100, 100); // middle point of the player's starting planet
//		drawCenteredString14("X", Color.CYAN, 120, 120);    // edge point of the player's starting planet
//		batch.end();

		//DEBUG: Draw rectamgles to visualize the planet bounds
//		float x = planets.getLast().actor.actorX;
//		float y = planets.getLast().actor.actorY;
//		float s = planets.getLast().actor.planetSize;
//		shapes.begin(ShapeRenderer.ShapeType.Line);
//		shapes.setColor(Color.WHITE);
//		shapes.box(x, y, 0, s, s, 0);
//		shapes.line(x, y, x+s, y+s);
//		shapes.line(x+s, y, x, y+s);
//		shapes.circle((float)planets.getLast().position.x, (float)planets.getLast().position.y, planets.getLast().radius);
//		shapes.end();
	}

	public void newLevel() {
		planets.clear();
		stage.clear();
		numPlanets = SpiceWars.random.nextInt(10) + 10;
		int counter = 0;
		while (planets.size() < numPlanets) {
			placePlanet();
			if (counter > 10000) break;
			counter++;
		}

		// Cycle through planets, assign teams, create actors, add them to the stage
		for (Planet planet : planets) {
			// The texture for a planet has its 0,0 coordinates in the lower left corner.
			// The planet position is however the middlepoint of a planet, that's why this has to be transformed.
			float posX = (float)planet.position.x;
			float posY = (float)planet.position.y;

			PlanetActor planetActor = new PlanetActor(planet, planetTexture, posX, posY);

			// Don't know if having this is necessary or not, but it might be helpful
			planet.actor = planetActor;

			stage.addActor(planetActor);
			planetActor.setTouchable(Touchable.enabled);
		}

		// Set the players planet to be select per default
		selectedPlanet = planets.getFirst();
		selectionActor = new SelectionActor(planetSelectionTexture, selectedPlanet);
		stage.addActor(selectionActor);
	}

	private void placePlanet() {
		int firstRadius = 40;
		int firstNormalSlots = 5;
		int firstMineSlots = 5;

		if (planets.isEmpty()) {
			// Place the first planet in the lower left corner.
			// It's always the same for a new game.
			Point point = new Point(100, 100);
			Planet planet = new Planet(firstRadius, teamPlayer, firstNormalSlots, firstMineSlots, point, Planet.PlanetType.normal, this);
			planets.add(planet);
			return;
		} else if (planets.size() == 1) {
			// Place the second planet in the upper right corner.
			// This one is identical to the first one and the same for every game.
			Point point = new Point(WIDTH - 100, HEIGHT - 100);
			Planet planet = new Planet(firstRadius, teamAI, firstNormalSlots, firstMineSlots, point, Planet.PlanetType.normal, this);
			planets.add(planet);
			Army a = new Army(teamAI);
			a.ships[1] = 1000;
			planet.receiveArmy(a);
			return;
		}

		// The first two planets have already been set, we can now place random
		// planets until the screen is filled.
		int randomRadius = SpiceWars.random.nextInt(80) + 50;
		int posX = SpiceWars.random.nextInt(WIDTH - randomRadius*2) + randomRadius;
		int posY = SpiceWars.random.nextInt(HEIGHT - randomRadius*2) + randomRadius;
		Point randomPoint = new Point(posX, posY);

		int minDistance = 100;
		int distance;
		int isGood = 0; // behaves like a boolean, anything below planets.size is false
		for (Planet planet : planets) {
			distance = planet.radius + randomRadius + minDistance;
			if ((planet.position.squaredDistanceTo(randomPoint) >= (distance * distance))) {
				isGood++;
			}
		}

		// Only instantiate and add the new planet if the position is found to be valid
		if (isGood == planets.size()) {
			int jitter = SpiceWars.random.nextInt(2) - SpiceWars.random.nextInt(4); // there's probably an easier way to get a random number between -2 and 2
			int maxNormalSlots = randomRadius / 8 + jitter*2;
			int maxMineSlots = randomRadius / 8 + jitter;
			planets.add(new Planet(randomRadius, SpiceWars.teamNeutral, maxNormalSlots, maxMineSlots, randomPoint, Planet.PlanetType.normal, this));
		}
	}

	// draws a given string centered to the given position
	// must be called from inside the batch
	public void drawCenteredString14(String string, Color color, float posX, float posY) {
		font14.setColor(color);
		font14.draw(batch, string, posX - font14.getBounds(string).width/2, posY + font14.getBounds(string).height/2);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		fontGenerator.dispose();
		textureAtlas.dispose();
	}
}
