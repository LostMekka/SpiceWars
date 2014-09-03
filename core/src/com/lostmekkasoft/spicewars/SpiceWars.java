package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.lostmekkasoft.spicewars.data.Team;

import java.util.Random;

public class SpiceWars extends Game {

	SpriteBatch batch;
	FreeTypeFontGenerator fontGenerator;
	BitmapFont font;
	BitmapFont fontLarge;
	public static Random random = new Random();

	int WIDTH;
	int HEIGHT;

	public static Team teamNeutral;

	@Override
	public void create () {
		batch = new SpriteBatch();

		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = 14;
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/imagine_font/imagine_font.ttf"));
		font = fontGenerator.generateFont(fontParameter);
		fontParameter.size = 48;
		fontLarge = fontGenerator.generateFont(fontParameter);

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
		batch.dispose();
		fontGenerator.dispose();

		super.dispose();
	}
}
