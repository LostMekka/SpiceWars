package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lostmekkasoft.spicewars.data.Army;
import com.lostmekkasoft.spicewars.data.Planet;
import java.util.ArrayList;

public class SpiceWars extends Game {

	public ArrayList<Planet> planets = new ArrayList<>();
	public ArrayList<Army> armies = new ArrayList<>();
	
	SpriteBatch batch;
	BitmapFont font;

	public void newLevel(){
		
	}
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
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
