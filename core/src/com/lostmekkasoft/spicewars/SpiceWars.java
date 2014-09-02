package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lostmekkasoft.spicewars.data.Team;

import java.util.Random;

public class SpiceWars extends Game {

	SpriteBatch batch;
	ShapeRenderer shapes;
	BitmapFont font;
	public static Random random = new Random();

	int WIDTH;
	int HEIGHT;

	public static Team teamNeutral;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		shapes = new ShapeRenderer();

		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		teamNeutral = new Team(-1, Color.DARK_GRAY);

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
