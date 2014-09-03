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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

public class GameplayScreen implements Screen {

	SpiceWars game;

	float timeForInput = 0;

	public int numPlanets;
	public ArrayList<Planet> planets = new ArrayList<>();
	public ArrayList<Army> armies = new ArrayList<>();

	public Team teamPlayer;
	public Team teamAI;

	private TextureAtlas planetAtlas;

	public GameplayScreen(final SpiceWars game) {
		this.game = game;
		teamPlayer = new Team(1, Color.GREEN);
		teamAI = new Team(2, Color.RED);
		planetAtlas = new TextureAtlas("sprites/planets.txt");
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

		if (planets.size() == 0) {
			// Place the first planet in the lower left corner.
			// It's always the same for a new game.
			Point point = new Point(100, 100);
			Planet planet = new Planet(firstRadius, teamPlayer, firstNormalSlots, firstMineSlots, point);
			planets.add(planet);
			return;
		} else if (planets.size() == 1) {
			// Place the second planet in the upper right corner.
			// This one is identical to the first one and the same for every game.
			Point point = new Point(game.WIDTH - 100, game.HEIGHT - 100);
			Planet planet = new Planet(firstRadius, teamAI, firstNormalSlots, firstMineSlots, point);
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
			planets.add(new Planet(randomRadius, SpiceWars.teamNeutral, maxNormalSlots, maxMineSlots, randomPoint));
		}
	}

	public void update(float delta) {

	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		TextureRegion planetPlayer  = planetAtlas.findRegion("green");
		TextureRegion planetAI      = planetAtlas.findRegion("red");
		TextureRegion planetNeutral = planetAtlas.findRegion("grey");

		game.batch.begin();
		for (Planet planet : planets) {
			// The texture for a planet has its 0,0 coordinates in the lower left corner.
			// The planet position is however the middlepoint of a planet, that's why this has to be transformed.
			float posX = (float)planet.position.x - planet.radius;
			float posY = (float)planet.position.y - planet.radius;

			switch (planet.team.id) {
				case -1:
					game.batch.draw(planetNeutral, posX, posY, planet.radius*2, planet.radius*2);
					break;
				case 1:
					game.batch.draw(planetPlayer, posX, posY, planet.radius*2, planet.radius*2);
					break;
				case 2:
					game.batch.draw(planetAI, posX, posY, planet.radius*2, planet.radius*2);
					break;
			}
		}
		game.batch.end();

		//DEBUG: Press R to generate a new playing field or ESC to exit the game
		timeForInput += delta;
		if (Gdx.input.isKeyPressed(Input.Keys.R) && timeForInput > 0.1) {
			newLevel();
			timeForInput = 0;
		} else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		//DEBUG: Write the amount of slots on each planet
		game.batch.begin();
		game.font.setColor(Color.GREEN);
		game.font.setScale(1f);
		for (Planet planet : planets) {
			String planetSlots = String.format("R: %d - N:%d, M:%d", planet.radius, planet.maxNormalSlots, planet.maxMineSlots);
			game.font.draw(game.batch, planetSlots, (float)planet.position.x - game.font.getBounds(planetSlots).width, (float)planet.position.y+5);
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
