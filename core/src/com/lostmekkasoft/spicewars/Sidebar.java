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
	LinkedList<SWButton> attackButtons = new LinkedList<>();

	public Sidebar(final SpiceWars game) {
		this.game = game;

		/**
		Building Stuff
		**/

		// If you love hardcoded position values clap your hands!
		// If you love hardcoded position values clap your hands!
		// If you love hardcoded position values and only die a little inside
		// CLAP YOUR HANDS!
		// It might also be a little to late for correct lyrics...
		SWButton buttonWorkerFactory = new SWButton(game.WIDTH + 20, game.HEIGHT - 250, 260, 22, "Worker Factory", Color.DARK_GRAY, this);
		buildButtons.add(buttonWorkerFactory);
		buttonWorkerFactory.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.workerFactory, game.teamPlayer);
				return true;
			}
		});
		SWButton buttonFighterFactory = new SWButton(game.WIDTH + 20, game.HEIGHT - 274, 260, 22, "Fighter Factory", Color.DARK_GRAY, this);
		buildButtons.add(buttonFighterFactory);
		buttonFighterFactory.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.fighterFactory, game.teamPlayer);
				return true;
			}
		});
		SWButton buttonFrigateFactory = new SWButton(game.WIDTH + 20, game.HEIGHT - 298, 260, 22, "Frigate Factory", Color.DARK_GRAY, this);
		buildButtons.add(buttonFrigateFactory);
		buttonFrigateFactory.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.frigateFactory, game.teamPlayer);
				return true;
			}
		});
		SWButton buttonDestroyerFactory = new SWButton(game.WIDTH + 20, game.HEIGHT - 322, 260, 22, "Destroyer Factory", Color.DARK_GRAY, this);
		buildButtons.add(buttonDestroyerFactory);
		buttonDestroyerFactory.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.destroyerFactory, game.teamPlayer);
				return true;
			}
		});
		SWButton buttonGenerator = new SWButton(game.WIDTH + 20, game.HEIGHT - 346, 260, 22, "Generator", Color.DARK_GRAY, this);
		buildButtons.add(buttonGenerator);
		buttonGenerator.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.generator, game.teamPlayer);
				return true;
			}
		});
		SWButton buttonSpiceSilo = new SWButton(game.WIDTH + 20, game.HEIGHT - 370, 260, 22, "Spice Silo", Color.DARK_GRAY, this);
		buildButtons.add(buttonSpiceSilo);
		buttonSpiceSilo.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.spiceSilo, game.teamPlayer);
				return true;
			}
		});
		SWButton buttonBattery = new SWButton(game.WIDTH + 20, game.HEIGHT - 394, 260, 22, "Battery", Color.DARK_GRAY, this);
		buildButtons.add(buttonBattery);
		buttonBattery.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.battery, game.teamPlayer);
				return true;
			}
		});
		SWButton buttonSpiceMine = new SWButton(game.WIDTH + 20, game.HEIGHT - 418, 260, 22, "Spice Mine", Color.DARK_GRAY, this);
		buildButtons.add(buttonSpiceMine);
		buttonSpiceMine.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.spiceMine, game.teamPlayer);
				return true;
			}
		});
		SWButton buttonArtillery = new SWButton(game.WIDTH + 20, game.HEIGHT - 442, 260, 22, "Artillery", Color.DARK_GRAY, this);
		buildButtons.add(buttonArtillery);
		buttonArtillery.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.artillery, game.teamPlayer);
				return true;
			}
		});
		SWButton buttonDeathLaser = new SWButton(game.WIDTH + 20, game.HEIGHT - 466, 260, 22, "Death Laser", Color.DARK_GRAY, this);
		buildButtons.add(buttonDeathLaser);
		buttonDeathLaser.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.deathlaser, game.teamPlayer);
				return true;
			}
		});

		/**
		Attack Stuff
		**/

		SWButton buttonSendAll = new SWButton(game.WIDTH + 20, game.HEIGHT - 750, 260, 60, "Attack!", Color.DARK_GRAY, this);
		attackButtons.add(buttonSendAll);
		buttonSendAll.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.sendArmy(game.teamPlayer, new double[]{1, 1, 1, 1}, game.selectedPlanetAlt);
				return true;
			}
		});

		// Events won't work if the buttons aren't staged
		for (SWButton button : buildButtons) {
			game.stage.addActor(button);
		}
		for (SWButton button : attackButtons) {
			game.stage.addActor(button);
		}

	}

	public void draw() {
		game.shapes.begin(ShapeRenderer.ShapeType.Filled);

		// draw the background for the sidebar
		game.shapes.setColor(new Color(0x111111FF));
		game.shapes.box(game.WIDTH, 0, 0, game.WIDTH + 300, game.HEIGHT, 0);

		int counter3 = 0; // I WILL REFUSE TO TAKE BLAME FOR THIS!
		for (Building building : game.selectedPlanet.normalSlots) {
			game.shapes.setColor(Color.BLUE);
			game.shapes.box(game.WIDTH + 20, game.HEIGHT - (114 + 22*counter3) + counter3, 0, (float)building.progress * 160, 2, 0);
			game.shapes.setColor(Color.RED);
			game.shapes.box(game.WIDTH + 20, game.HEIGHT - (116 + 24*counter3) + counter3, 0, (float)(building.hp/building.getMaxHp() * 160) , 2, 0);
			counter3++;
		}

		int counter4 = 0; // STILL NO SHAME!
		for (Building building : game.selectedPlanet.mineSlots) {
			game.shapes.setColor(Color.BLUE);
			game.shapes.box(game.WIDTH + 200, game.HEIGHT - (114 + 22*counter4) + counter4, 0, (float)building.progress * 80, 2, 0);
			game.shapes.setColor(Color.RED);
			game.shapes.box(game.WIDTH + 200, game.HEIGHT - (116 + 24*counter4) + counter4, 0, (float)(building.hp/building.getMaxHp() * 80), 2, 0);
			counter4++;
		}

		if (game.selectedPlanet.team.id == 1) {
			// Loop through the buildButtons to draw them
			for (SWButton button : buildButtons) {
				game.shapes.setColor(button.color);
				game.shapes.box(button.posX, button.posY, 0, button.width, button.height, 0);
			}
		}

		if (game.selectedPlanet.getArmy(game.teamPlayer) != null) {
			for (SWButton button : attackButtons) {
				game.shapes.setColor(button.color);
				game.shapes.box(button.posX, button.posY, 0, button.width, button.height, 0);
			}
		}

		game.shapes.end();


		// Unfortunately the labels are drawn in a SpriteBatch
		// "Unfortunately" is an enormous understatement by the way
		// this splitting this shit up into two different renderers is inefficient and annoying
		game.batch.begin();

		// Loop through building slots for a planet, also create their buttons
		int counter = 0;
		for (Building building : game.selectedPlanet.normalSlots) {
			game.font12.draw(game.batch, building.type.name(), game.WIDTH + 20, game.HEIGHT - (100 + 22*counter));
			counter++;
		}
		int counter2 = 0;
		for (Building building : game.selectedPlanet.mineSlots) {
			game.font12.draw(game.batch, building.type.name(), game.WIDTH + 200, game.HEIGHT - (100 + 22*counter2));
			counter2++;
		}

		// Title for the selected in game item
		game.font22.setColor(game.selectedPlanet.team.color);
		game.font22.draw(game.batch, String.format("Planet X:%d Y:%d", (int)game.selectedPlanet.position.x, (int)game.selectedPlanet.position.y), game.WIDTH + 20, game.HEIGHT - 50);
		game.font22.setColor(Color.WHITE);

		if (game.selectedPlanet.team.id == 1) {
			// Loop through the buildButtons to draw their labels
			for (SWButton button : buildButtons) {
				game.font14.draw(game.batch, button.label, button.posX + 5, button.posY+14);
			}
		}

		if (game.selectedPlanet.getArmy(game.teamPlayer) != null) {
			for (SWButton button : attackButtons) {
				game.font14.draw(game.batch, button.label, button.posX + 5, button.posY+14);
			}
		}

		game.batch.end();
	}

}
