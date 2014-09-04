package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.lostmekkasoft.spicewars.data.Building;

import java.util.LinkedList;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author Kilian Koeltzsch
 */

public class Sidebar {

	SpiceWars game;

	LinkedList<SWButton> buildButtons = new LinkedList<>();

	public Sidebar(final SpiceWars game) {
		this.game = game;

		SWButton buttonWorkerFactory = new SWButton(game.WIDTH + 20, game.HEIGHT - 250, 260, 22, "Worker Factory", Color.DARK_GRAY, this);
		buildButtons.add(buttonWorkerFactory);
		SWButton buttonFighterFactory = new SWButton(game.WIDTH + 20, game.HEIGHT - 250, 260, 22, "Fighter Factory", Color.DARK_GRAY, this);
		buildButtons.add(buttonFighterFactory);
		SWButton buttonFrigateFactory = new SWButton(game.WIDTH + 20, game.HEIGHT - 250, 260, 22, "Frigate Factory", Color.DARK_GRAY, this);
		buildButtons.add(buttonFrigateFactory);
		SWButton buttonDestroyerFactory = new SWButton(game.WIDTH + 20, game.HEIGHT - 250, 260, 22, "Destroyer Factory", Color.DARK_GRAY, this);
		buildButtons.add(buttonDestroyerFactory);
		SWButton buttonGenerator = new SWButton(game.WIDTH + 20, game.HEIGHT - 250, 260, 22, "Generator", Color.DARK_GRAY, this);
		buildButtons.add(buttonGenerator);
		SWButton buttonSpiceSilo = new SWButton(game.WIDTH + 20, game.HEIGHT - 250, 260, 22, "Spice Silo", Color.DARK_GRAY, this);
		buildButtons.add(buttonSpiceSilo);
		SWButton buttonBattery = new SWButton(game.WIDTH + 20, game.HEIGHT - 250, 260, 22, "Battery", Color.DARK_GRAY, this);
		buildButtons.add(buttonBattery);
		SWButton buttonSpiceMine = new SWButton(game.WIDTH + 20, game.HEIGHT - 250, 260, 22, "Spice Mine", Color.DARK_GRAY, this);
		buildButtons.add(buttonSpiceMine);
		SWButton buttonArtillery = new SWButton(game.WIDTH + 20, game.HEIGHT - 250, 260, 22, "Artillery", Color.DARK_GRAY, this);
		buildButtons.add(buttonArtillery);
		SWButton buttonDeathLaser = new SWButton(game.WIDTH + 20, game.HEIGHT - 250, 260, 22, "Death Laser", Color.DARK_GRAY, this);
		buildButtons.add(buttonDeathLaser);

		// Events won't work if the buttons aren't staged
		for (SWButton button : buildButtons) {
			game.stage.addActor(button);
		}

		//FIXME: This event listener isn't firing when clicking the button - Why?
		buttonWorkerFactory.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				((SWButton)event.getTarget()).setText("DONE!");
				game.selectedPlanet.addBuilding(Building.BuildingType.workerFactory, game.teamPlayer);
				System.out.println("Adding a worker factory");
				return true;
			}
		});
	}

	public void draw() {
		game.shapes.begin(ShapeRenderer.ShapeType.Filled);

		// draw the background for the sidebar
		game.shapes.setColor(new Color(0x111111FF));
		game.shapes.box(game.WIDTH, 0, 0, game.WIDTH + 300, game.HEIGHT, 0);

		if (game.selectedPlanet.team.id == 1) {
			// Loop through the buildButtons to draw them
			int buildButtonsCounter = 0;
			for (SWButton button : buildButtons) {
				game.shapes.setColor(button.color);
				game.shapes.box(button.posX, button.posY - (24 * buildButtonsCounter), 0, button.width, button.height, 0);
				buildButtonsCounter++;
			}


		}

		game.shapes.end();


		// Unfortunately the labels are drawn in a SpriteBatch
		game.batch.begin();

		// Loop through buildings for a planet, also create their buttons
		int counter = 0;
		for (Building building : game.selectedPlanet.normalSlots) {
			game.font22.draw(game.batch, building.type.name(), game.WIDTH + 10, game.HEIGHT - (100 + 26*counter));
			// Add buttons
//			Color buttonColor;
//			buttonColor = building.isActive ? Color.GREEN : Color.DARK_GRAY;
//			buttons.add(new SWButton(game.WIDTH + 200, game.HEIGHT - (100 + 26*counter + 16), 70, 22, "Toggle", buttonColor, this));
//			game.stage.addActor(buttons.getLast());
//			buttons.getLast().addListener(new InputListener() {
//				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//					event.getTarget().setColor(Color.GREEN);
//					return true;
//				}
//			});
			counter++;
			//TODO: Stefan will hier noch nen Lebensbalken haben... Ja genau :D
		}

		// Title for the selected in game item
		game.font22.setColor(game.selectedPlanet.team.color);
		game.font22.draw(game.batch, String.format("Planet X:%d Y:%d", (int)game.selectedPlanet.position.x, (int)game.selectedPlanet.position.y), game.WIDTH + 10, game.HEIGHT - 50);
		game.font22.setColor(Color.WHITE);

		if (game.selectedPlanet.team.id == 1) {
			// Loop through the buildButtons to draw their labels
			int buildButtonsCounterFont = 0;
			for (SWButton button : buildButtons) {
				game.font14.draw(game.batch, button.label, button.posX + 5, button.posY+14 - (24 * buildButtonsCounterFont));
				buildButtonsCounterFont++;
			}
		}
		game.batch.end();
	}

}
