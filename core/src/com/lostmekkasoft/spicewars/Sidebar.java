package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.LinkedList;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author Kilian Koeltzsch
 */

public class Sidebar {

	SpiceWars game;

	LinkedList<SWButton> buttons = new LinkedList<>();

	public Sidebar(SpiceWars game) {
		this.game = game;

		SWButton button = new SWButton(game.WIDTH + 20, game.HEIGHT/2, 100, 30, "Click me", Color.DARK_GRAY, this);
		game.stage.addActor(button);

		button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				((SWButton)event.getTarget()).setText("You clicked me");
				return true;
			}
		});

		buttons.add(button);
	}

	public void draw() {
		game.shapes.begin(ShapeRenderer.ShapeType.Filled);

		// draw the background for the sidebar
		game.shapes.setColor(new Color(0x111111FF));
		game.shapes.box(game.WIDTH, 0, 0, game.WIDTH + 300, game.HEIGHT, 0);

		// Loop through the buttons to draw them
		for (SWButton button : buttons) {
			game.shapes.setColor(button.color);
			game.shapes.box(button.posX, button.posY, 0, button.width, button.height, 0);
		}

		game.shapes.end();

		// Unfortunately the labels are drawn in a SpriteBatch
		game.batch.begin();
		for (SWButton button : buttons) {
			game.font14.draw(game.batch, button.label, button.posX, button.posY+14);
		}
		game.batch.end();
	}

}
