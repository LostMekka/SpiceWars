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

		// bar for the spice
		game.shapes.setColor(Color.GRAY);
		game.shapes.box(200, game.HEIGHT + 10, 0, 250, 30, 0);
		game.shapes.setColor(Color.MAROON);
		game.shapes.box(200, game.HEIGHT + 10, 0, (float)(game.teamPlayer.spiceStored / game.teamPlayer.maxSpiceStorage * 250), 30, 0);
		game.shapes.box(200, game.HEIGHT - 20, 0, (float)(game.teamAI.spiceStored / game.teamAI.maxSpiceStorage * 250), 20, 0); //DEBUG

		// bar for the energy
		game.shapes.setColor(Color.GRAY);
		game.shapes.box(500, game.HEIGHT + 10, 0, 250, 30, 0);
		game.shapes.setColor(Color.BLUE);
		game.shapes.box(500, game.HEIGHT + 10, 0, (float)(game.teamPlayer.energyStored / game.teamPlayer.maxEnergyStorage * 250), 30, 0);
		game.shapes.box(500, game.HEIGHT - 20, 0, (float)(game.teamAI.energyStored / game.teamAI.maxEnergyStorage * 250), 20, 0); //DEBUG

		game.shapes.end();


		game.batch.begin();

		game.font22.draw(game.batch, "SpiceWars", 20, game.HEIGHT + 30);

		// display numerical values for spice and energy stored
		game.font14.draw(game.batch, String.format("Spice: %.0f/%d", game.teamPlayer.spiceStored, game.teamPlayer.maxSpiceStorage), 200, game.HEIGHT + 22);
		game.font14.draw(game.batch, String.format("Energy: %.0f/%d", game.teamPlayer.energyStored, game.teamPlayer.maxEnergyStorage), 500, game.HEIGHT + 22);

		// display numerical values for spice and energy income
		game.font14.draw(game.batch, String.format("Income: %.0f", game.teamPlayer.spiceIncome), 200, game.HEIGHT + 38);
		game.font14.draw(game.batch, String.format("Income: %.0f", game.teamPlayer.energyIncome), 500, game.HEIGHT + 38);

		game.batch.end();
	}
}
