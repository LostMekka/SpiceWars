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

	LinkedList<SWButton> neutralBuildButtons = new LinkedList<>();
	LinkedList<SWButton> buildButtons = new LinkedList<>();
	LinkedList<SWButton> attackButtons = new LinkedList<>();

	public double[] armyToSend;

	public Sidebar(final SpiceWars game) {
		this.game = game;
		armyToSend = new double[4];

		/**
		Building Stuff
		**/

		// If you love hardcoded position values clap your hands!
		// If you love hardcoded position values clap your hands!
		// If you love hardcoded position values and only die a little inside
		// CLAP YOUR HANDS!
		// It might also be a little to late for correct lyrics...
		int buttonPosY = 350;
		SWButton buttonHQ = new SWButton(game.WIDTH + 20, game.HEIGHT - buttonPosY, 260, 22, "HQ", Color.DARK_GRAY, this);
		neutralBuildButtons.add(buttonHQ);
		buttonHQ.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("Build HQ");
				game.selectedPlanet.addBuilding(Building.BuildingType.hq, game.teamPlayer);
				return true;
			}
		});
		buttonPosY += 24;
		SWButton buttonWorkerFactory = new SWButton(game.WIDTH + 20, game.HEIGHT - buttonPosY, 260, 22, "Worker Factory", Color.DARK_GRAY, this);
		buildButtons.add(buttonWorkerFactory);
		buttonWorkerFactory.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.workerFactory, game.teamPlayer);
				return true;
			}
		});
		buttonPosY += 24;
		SWButton buttonFighterFactory = new SWButton(game.WIDTH + 20, game.HEIGHT - buttonPosY, 260, 22, "Fighter Factory", Color.DARK_GRAY, this);
		buildButtons.add(buttonFighterFactory);
		buttonFighterFactory.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.fighterFactory, game.teamPlayer);
				return true;
			}
		});
		buttonPosY += 24;
		SWButton buttonFrigateFactory = new SWButton(game.WIDTH + 20, game.HEIGHT - buttonPosY, 260, 22, "Frigate Factory", Color.DARK_GRAY, this);
		buildButtons.add(buttonFrigateFactory);
		buttonFrigateFactory.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.frigateFactory, game.teamPlayer);
				return true;
			}
		});
		buttonPosY += 24;
		SWButton buttonDestroyerFactory = new SWButton(game.WIDTH + 20, game.HEIGHT - buttonPosY, 260, 22, "Destroyer Factory", Color.DARK_GRAY, this);
		buildButtons.add(buttonDestroyerFactory);
		buttonDestroyerFactory.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.destroyerFactory, game.teamPlayer);
				return true;
			}
		});
		buttonPosY += 24;
		SWButton buttonGenerator = new SWButton(game.WIDTH + 20, game.HEIGHT - buttonPosY, 260, 22, "Generator", Color.DARK_GRAY, this);
		buildButtons.add(buttonGenerator);
		buttonGenerator.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.generator, game.teamPlayer);
				return true;
			}
		});
		buttonPosY += 24;
		SWButton buttonSpiceSilo = new SWButton(game.WIDTH + 20, game.HEIGHT - buttonPosY, 260, 22, "Spice Silo", Color.DARK_GRAY, this);
		buildButtons.add(buttonSpiceSilo);
		buttonSpiceSilo.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.spiceSilo, game.teamPlayer);
				return true;
			}
		});
		buttonPosY += 24;
		SWButton buttonBattery = new SWButton(game.WIDTH + 20, game.HEIGHT - buttonPosY, 260, 22, "Battery", Color.DARK_GRAY, this);
		buildButtons.add(buttonBattery);
		buttonBattery.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.battery, game.teamPlayer);
				return true;
			}
		});
		buttonPosY += 24;
		SWButton buttonSpiceMine = new SWButton(game.WIDTH + 20, game.HEIGHT - buttonPosY, 260, 22, "Spice Mine", Color.DARK_GRAY, this);
		buildButtons.add(buttonSpiceMine);
		buttonSpiceMine.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.spiceMine, game.teamPlayer);
				return true;
			}
		});
		buttonPosY += 24;
		SWButton buttonArtillery = new SWButton(game.WIDTH + 20, game.HEIGHT - buttonPosY, 260, 22, "Artillery", Color.DARK_GRAY, this);
		buildButtons.add(buttonArtillery);
		buttonArtillery.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.addBuilding(Building.BuildingType.artillery, game.teamPlayer);
				return true;
			}
		});
		buttonPosY += 24;
		SWButton buttonDeathLaser = new SWButton(game.WIDTH + 20, game.HEIGHT - buttonPosY, 260, 22, "Death Laser", Color.DARK_GRAY, this);
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

		SWButton buttonSendAll = new SWButton(game.WIDTH + 20, 20, 260, 60, "Attack!", Color.DARK_GRAY, this);
		attackButtons.add(buttonSendAll);
		buttonSendAll.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				game.selectedPlanet.sendArmy(game.teamPlayer, armyToSend, game.selectedPlanetAlt);
				return true;
			}
		});

		SWButton buttonSelectWorkers = new SWButton(game.WIDTH + 28, 100, 54, 180, "", Color.DARK_GRAY, this);
		attackButtons.add(buttonSelectWorkers);
		buttonSelectWorkers.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				armyToSend[0] = y / 180;
				return true;
			}
		});
		SWButton buttonSelectFighters = new SWButton(game.WIDTH + 92, 100, 54, 180, "", Color.DARK_GRAY, this);
		attackButtons.add(buttonSelectFighters);
		buttonSelectFighters.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				armyToSend[1] = y / 180;
				return true;
			}
		});
		SWButton buttonSelectFrigates = new SWButton(game.WIDTH + 155, 100, 54, 180, "", Color.DARK_GRAY, this);
		attackButtons.add(buttonSelectFrigates);
		buttonSelectFrigates.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				armyToSend[2] = y / 180;
				return true;
			}
		});
		SWButton buttonSelectDestroyers = new SWButton(game.WIDTH + 220, 100, 54, 180, "", Color.DARK_GRAY, this);
		attackButtons.add(buttonSelectDestroyers);
		buttonSelectDestroyers.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				armyToSend[3] = y / 180;
				return true;
			}
		});

		// Events won't work if the buttons aren't staged
		for (SWButton button : neutralBuildButtons) {
			game.stage.addActor(button);
		}
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
			game.shapes.setColor(Color.CYAN);
			//FIXME: adding the counter to the height explicitly is stoopid. And it fails to fix the problem entirely as well.
			game.shapes.box(game.WIDTH + 20, game.HEIGHT - (114 + 22*counter3) + counter3, 0, (float)building.progress * 160, 2, 0);
			game.shapes.setColor(Color.RED);
			game.shapes.box(game.WIDTH + 20, game.HEIGHT - (116 + 24*counter3) + counter3, 0, (float)(building.hp/building.getMaxHp() * 160) , 2, 0);
			counter3++;
		}

		int counter4 = 0; // STILL NO SHAME!
		for (Building building : game.selectedPlanet.mineSlots) {
			game.shapes.setColor(Color.CYAN);
			//FIXME: See above.
			game.shapes.box(game.WIDTH + 200, game.HEIGHT - (114 + 22*counter4) + counter4, 0, (float)building.progress * 80, 2, 0);
			game.shapes.setColor(Color.RED);
			game.shapes.box(game.WIDTH + 200, game.HEIGHT - (116 + 24*counter4) + counter4, 0, (float)(building.hp/building.getMaxHp() * 80), 2, 0);
			counter4++;
		}

		if (game.selectedPlanet.team.isNeutral()) {
			for (SWButton button : neutralBuildButtons) {
				game.shapes.setColor(button.color);
				game.shapes.box(button.posX, button.posY, 0, button.width, button.height, 0);
			}
		}

		if (game.selectedPlanet.team.id == 1) {
			// Loop through the buildButtons to draw them
			for (SWButton button : buildButtons) {
				game.shapes.setColor(button.color);
				game.shapes.box(button.posX, button.posY, 0, button.width, button.height, 0);
			}
		}

		if (game.selectedPlanet.getArmy(game.teamPlayer) != null) {
			// Planet has an army belonging to the player => Display the army stuff
			for (SWButton button : attackButtons) {
				game.shapes.setColor(button.color);
				game.shapes.box(button.posX, button.posY, 0, button.width, button.height, 0);
			}

			// army selection

			game.shapes.setColor(Color.GRAY);
			game.shapes.box(game.WIDTH + 28,  100, 0, 54, (float)(armyToSend[0]*180), 0); // selection bar workers
			game.shapes.box(game.WIDTH + 92,  100, 0, 54, (float)(armyToSend[1]*180), 0); // selection bar fighters
			game.shapes.box(game.WIDTH + 155, 100, 0, 54, (float)(armyToSend[2]*180), 0); // selection bar frigates
			game.shapes.box(game.WIDTH + 220, 100, 0, 54, (float)(armyToSend[3]*180), 0); // selection bar destroyers
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
		game.font22.draw(game.batch, String.format("Planet X:%d Y:%d", (int) game.selectedPlanet.position.x, (int) game.selectedPlanet.position.y), game.WIDTH + 20, game.HEIGHT - 10);
		game.font22.setColor(Color.WHITE);
		game.font12.setColor(game.selectedPlanet.team.color);
		game.font12.draw(game.batch, String.format("Normal Slots: %d, Mine Slots: %d", game.selectedPlanet.maxNormalSlots, game.selectedPlanet.maxMineSlots), game.WIDTH + 30, game.HEIGHT - 30);
		game.font12.setColor(Color.WHITE);

		if (game.selectedPlanet.team.isNeutral()) {
			for (SWButton button : neutralBuildButtons) {
				game.font14.draw(game.batch, button.label, button.posX + 5, button.posY+14);
			}
		}

		if (game.selectedPlanet.team.id == 1) {
			// Loop through the buildButtons to draw their labels
			for (SWButton button : buildButtons) {
				game.font14.draw(game.batch, button.label, button.posX + 5, button.posY+14);
			}
		}

		if (game.selectedPlanet.getArmy(game.teamPlayer) != null) {
			// Planet has an army belonging to the player => Display the army stuff
			for (SWButton button : attackButtons) {
				game.font14.draw(game.batch, button.label, button.posX + 5, button.posY+14);
			}
			game.font14.draw(game.batch, String.format("WO: %.0f%%", armyToSend[0]*100), game.WIDTH + 30, 300);
			game.font14.draw(game.batch, String.format("FI: %.0f%%", armyToSend[1]*100), game.WIDTH + 95, 300);
			game.font14.draw(game.batch, String.format("FR: %.0f%%", armyToSend[2]*100), game.WIDTH + 155, 300);
			game.font14.draw(game.batch, String.format("DE: %.0f%%", armyToSend[3]*100), game.WIDTH + 220, 300);

			game.font14.draw(game.batch, String.format("%.0f", Math.ceil(game.selectedPlanet.getArmy(game.teamPlayer).ships[0] * armyToSend[0])), game.WIDTH + 28, 110);
			game.font14.draw(game.batch, String.format("%.0f", Math.ceil(game.selectedPlanet.getArmy(game.teamPlayer).ships[1] * armyToSend[1])), game.WIDTH + 92, 110);
			game.font14.draw(game.batch, String.format("%.0f", Math.ceil(game.selectedPlanet.getArmy(game.teamPlayer).ships[2] * armyToSend[2])), game.WIDTH + 155, 110);
			game.font14.draw(game.batch, String.format("%.0f", Math.ceil(game.selectedPlanet.getArmy(game.teamPlayer).ships[3] * armyToSend[3])), game.WIDTH + 220, 110);
		}

		game.batch.end();
	}

}
