package com.lostmekkasoft.spicewars.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lostmekkasoft.spicewars.SpiceWars;
import com.lostmekkasoft.spicewars.data.Planet;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author Kilian Koeltzsch
 */

public class SelectionActor extends Actor {

	public static enum SelectionType {
		normal, alternative
	}

	TextureRegion textureRegion;

	public SelectionType type;
	public Planet selectedPlanet;

	public SelectionActor(TextureRegion textureRegion, Planet selectedPlanet, SelectionType type) {
		this.textureRegion = textureRegion;
		this.selectedPlanet = selectedPlanet;
		this.type = type;
	}

	@Override
	public void draw (Batch batch, float alpha) {
		if (type == SelectionType.normal) {
			batch.setColor(Color.WHITE);
		} else {
			batch.setColor(Color.PURPLE);
		}
		batch.draw(textureRegion, selectedPlanet.actor.actorX, selectedPlanet.actor.actorY, (selectedPlanet.radius * 2) / SpiceWars.planetTextureFactor, (selectedPlanet.radius * 2) / SpiceWars.planetTextureFactor);
		batch.setColor(Color.WHITE);
	}

}
