/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lostmekkasoft.spicewars.data.Army;
import com.lostmekkasoft.spicewars.data.Planet;
import java.util.ArrayList;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author LostMekka
 */

public class GameplayScreen implements Screen {

	SpiceWars game;

	public int numPlanets;
	public ArrayList<Planet> planets = new ArrayList<>();
	public ArrayList<Army> armies = new ArrayList<>();

	public GameplayScreen(final SpiceWars game) {
		this.game = game;
		newLevel();
	}

	public void newLevel() {
		// Place planets as long as placePlanets returns true meaning it placed a planet
		numPlanets = game.random.nextInt(10) + 10;
		for (int i = 0; i < numPlanets; i++) {
			placePlanet();
		}
	}

	public void newLevel(){
		

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
			game.shapes.circle(planet.position.x, planet.position.y, planet.radius);
		}

		game.shapes.end();
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
