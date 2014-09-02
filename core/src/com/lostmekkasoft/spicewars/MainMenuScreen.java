package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * Created by Kilian Koeltzsch on 2014-09-02.
 */

public class MainMenuScreen implements Screen {

	final SpiceWars game;

	OrthographicCamera camera;

	public MainMenuScreen(final SpiceWars game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 768);
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.begin();
		game.font.setColor(Color.WHITE);
		game.font.setScale(4f);
		game.font.draw(game.batch, "Spice Wars", 310, 330);
		game.batch.end();

		if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
			dispose();
		}
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
