package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lostmekkasoft.spicewars.data.Planet;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author Kilian Koeltzsch
 */

public class PlanetActor extends Actor {

	TextureRegion textureRegion;
	float actorX;
	float actorY;
	float textureFactor = 2f;

	public Planet planet;

	public PlanetActor(Planet planet, TextureRegion textureRegion, float actorX, float actorY) {
		this.planet = planet;
		this.textureRegion = textureRegion;
		this.actorX = actorX;
		this.actorY = actorY;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(textureRegion, actorX, actorY, planet.radius * textureFactor, planet.radius * textureFactor);
	}

	@Override
	public void act(float delta) {
		// Do some stuff...
	}
}
