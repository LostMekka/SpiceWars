package com.lostmekkasoft.spicewars.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lostmekkasoft.spicewars.data.Planet;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author Kilian Koeltzsch
 */

public class SelectionActor extends Actor {

	TextureRegion textureRegion;
	float actorX;
	float actorY;
	float textureFactor = 2f;

	boolean visible = false;
	Planet selectedPlanet;

	public SelectionActor(Planet selectedPlanet) {
		this.selectedPlanet = selectedPlanet;
	}

	@Override
	public void draw (Batch batch, float alpha) {
		batch.draw(textureRegion, selectedPlanet.actor.actorX, selectedPlanet.actor.actorY, selectedPlanet.radius * textureFactor, selectedPlanet.radius * textureFactor);
	}

}
