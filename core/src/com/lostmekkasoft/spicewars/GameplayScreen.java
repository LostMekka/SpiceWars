/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lostmekkasoft.spicewars.data.Army;
import com.lostmekkasoft.spicewars.data.Planet;
import com.lostmekkasoft.spicewars.data.Point;
import com.lostmekkasoft.spicewars.data.Team;

import java.util.ArrayList;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author LostMekka
 */

public final class GameplayScreen implements Screen {

	SpiceWars game;

	float timeForInput = 0;

	public int numPlanets;
	public ArrayList<Planet> planets = new ArrayList<>();
	public ArrayList<Army> armies = new ArrayList<>();

	public Team teamPlayer;
	public Team teamAI;

	public GameplayScreen(final SpiceWars game) {
		this.game = game;
		teamPlayer = new Team(1, Color.CYAN);
		teamAI = new Team(2, Color.RED);
		newLevel();
	}

	public void newLevel() {
		planets.clear();
		numPlanets = SpiceWars.random.nextInt(10) + 10;
		int counter = 0;
		while (planets.size() < numPlanets) {
			placePlanet();
			if (counter > 10000) break;
			counter++;
		}
	}

	private void placePlanet() {
		int firstRadius = 40;
		int firstNormalSlots = 5;
		int firstMineSlots = 5;

		if (planets.isEmpty()) {
			// Place the first planet in the lower left corner.
			// It's always the same for a new game.
			Point point = new Point(100, 100);
			Planet planet = new Planet(firstRadius, teamPlayer, firstNormalSlots, firstMineSlots, point, Planet.PlanetType.normal);
			planets.add(planet);
			return;
		} else if (planets.size() == 1) {
			// Place the second planet in the upper right corner.
			// This one is identical to the first one and the same for every game.
			Point point = new Point(game.WIDTH - 100, game.HEIGHT - 100);
			Planet planet = new Planet(firstRadius, teamAI, firstNormalSlots, firstMineSlots, point, Planet.PlanetType.normal);
			planets.add(planet);
			return;
		}

		// The first two planets have already been set, we can now place random
		// planets until the screen is filled.
		int randomRadius = SpiceWars.random.nextInt(80) + 50;
		int posX = SpiceWars.random.nextInt(game.WIDTH - randomRadius*2) + randomRadius;
		int posY = SpiceWars.random.nextInt(game.HEIGHT - randomRadius*2) + randomRadius;
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
			planets.add(new Planet(randomRadius, SpiceWars.teamNeutral, maxNormalSlots, maxMineSlots, randomPoint, Planet.PlanetType.normal));
		}
	}

	public void update(float delta) {

	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.shapes.begin(ShapeRenderer.ShapeType.Filled);

		// Render all planets as simple shapes
		for (Planet planet : planets) {
			game.shapes.setColor(planet.team.color);
			game.shapes.circle((float)planet.position.x, (float)planet.position.y, planet.radius);
		}

		//DEBUG: Press R to generate a new playing field or ESC to exit the game
		timeForInput += delta;
		if (Gdx.input.isKeyPressed(Input.Keys.R) && timeForInput > 0.1) {
			newLevel();
			timeForInput = 0;
		} else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		game.shapes.end();

		//DEBUG: Write the amount of slots on each planet
		game.batch.begin();
		game.font.setColor(Color.GREEN);
		game.font.setScale(1f);
		for (Planet planet : planets) {
			String planetSlots = String.format("R: %d - N:%d, M:%d", planet.radius, planet.maxNormalSlots, planet.maxMineSlots);
			game.font.draw(game.batch, planetSlots, (float)planet.position.x-55, (float)planet.position.y+5);
		}
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
