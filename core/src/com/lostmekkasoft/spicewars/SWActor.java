package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author Kilian Koeltzsch
 */

abstract class SWActor extends Actor {
	TextureRegion textureRegion;
	float actorX;
	float actorY;

	public Object actee;

	public SWActor(Object actee, TextureRegion textureRegion, float actorX, float actorY) {
		this.actee = actee;
		this.textureRegion = textureRegion;
		this.actorX = actorX;
		this.actorY = actorY;
//		setBounds(actorX, actorY, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
	}

	@Override
	public void draw (Batch batch, float alpha) {
		batch.draw(textureRegion, actorX, actorY, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
	}

}
