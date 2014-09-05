package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author Kilian Koeltzsch
 */

public class TopBar {
	SpiceWars game;

	public TopBar(SpiceWars game) {
		this.game = game;
	}

	public void draw() {

		game.shapes.begin(ShapeRenderer.ShapeType.Filled);

		// draw the background for the overlay
		game.shapes.setColor(new Color(0x111111FF));
		game.shapes.box(0, game.HEIGHT, 0, game.WIDTH + 300, 50, 0);

		game.shapes.end();


		game.batch.begin();

		game.font22.draw(game.batch, "SpiceWars", 20, game.HEIGHT + 30);

		game.batch.end();
	}
}
