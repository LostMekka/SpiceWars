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

	TextureRegion textureRegion;

	boolean visible = false;
	public Planet selectedPlanet;

	public SelectionActor(TextureRegion textureRegion, Planet selectedPlanet) {
		this.textureRegion = textureRegion;
		this.selectedPlanet = selectedPlanet;
	}

	@Override
	public void draw (Batch batch, float alpha) {
		batch.setColor(Color.WHITE);
		batch.draw(textureRegion, selectedPlanet.actor.actorX, selectedPlanet.actor.actorY, (selectedPlanet.radius * 2) / SpiceWars.planetTextureFactor, (selectedPlanet.radius * 2) / SpiceWars.planetTextureFactor);
	}

}
