package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author Kilian Koeltzsch
 */

abstract class GenericActor extends Actor {
	TextureRegion textureRegion;
	float actorX;
	float actorY;

	public Object actee;

	public GenericActor(Object actee, TextureRegion textureRegion, float actorX, float actorY) {
		this.actee = actee;
		this.textureRegion = textureRegion;
		this.actorX = actorX;
		this.actorY = actorY;
	}
}
