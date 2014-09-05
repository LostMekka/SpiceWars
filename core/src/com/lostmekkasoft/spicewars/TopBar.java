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
		game.shapes.box(200, game.HEIGHT + 10, 0, 400, 30, 0);
		game.shapes.setColor(new Color(0xb4521eFF)); // Spice color stolen directly from Dune screenshots btw
		game.shapes.box(200, game.HEIGHT + 10, 0, (float)(game.teamPlayer.spiceStored / game.teamPlayer.maxSpiceStorage * 400), 30, 0);

		// bar for the energy
		game.shapes.setColor(Color.GRAY);
		game.shapes.box(800, game.HEIGHT + 10, 0, 400, 30, 0);
		game.shapes.setColor(Color.BLUE);
		game.shapes.box(800, game.HEIGHT + 10, 0, (float)(game.teamPlayer.energyStored / game.teamPlayer.maxEnergyStorage * 400), 30, 0);

		game.shapes.end();


		game.batch.begin();

		game.font22.draw(game.batch, "SpiceWars", 20, game.HEIGHT + 30);

		// display numerical values for spice and energy stored
		game.font14.draw(game.batch, String.format("Spice: %.0f/%d", game.teamPlayer.spiceStored, game.teamPlayer.maxSpiceStorage), 204, game.HEIGHT + 24);
		game.font14.draw(game.batch, String.format("Energy: %.0f/%d", game.teamPlayer.energyStored, game.teamPlayer.maxEnergyStorage), 804, game.HEIGHT + 24);

		// display numerical values for spice and energy income
		game.font14.draw(game.batch, String.format("Income: %.0f", game.teamPlayer.spiceIncome), 204, game.HEIGHT + 36);
		game.font14.draw(game.batch, String.format("Income: %.0f", game.teamPlayer.energyIncome), 804, game.HEIGHT + 36);

		// display efficiencies
		game.font14.setColor(new Color(0xb4521eFF));
		game.font14.draw(game.batch, String.format("%.0f%%", game.teamPlayer.lastSpiceEfficiency * 100), 620, game.HEIGHT + 30);
		game.font14.setColor(Color.WHITE);
		game.font14.draw(game.batch, String.format("%.0f%%", game.teamPlayer.lastEfficiency * 100), 680, game.HEIGHT + 30);
		game.font14.setColor(Color.BLUE);
		game.font14.draw(game.batch, String.format("%.0f%%", game.teamPlayer.lastEnergyEfficiency * 100), 740, game.HEIGHT + 30);

		game.batch.end();
	}
}
