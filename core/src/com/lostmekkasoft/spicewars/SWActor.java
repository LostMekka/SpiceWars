package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author Kilian Koeltzsch
 */

public class SWActor extends Actor {
	Texture texture;
	float actorX;
	float actorY;

	public SWActor(TextureRegion texture, float actorX, float actorY) {
		this.texture = texture.getTexture();
		this.actorX = actorX;
		this.actorY = actorY;
		setBounds(actorX, actorY, texture.getRegionWidth(), texture.getRegionHeight());
	}

	@Override
	public void draw (Batch batch, float alpha) {
		batch.draw(texture, actorX, actorY, texture.getWidth(), texture.getHeight());
	}

	@Override
	public void act (float delta) {
		// DO STUFF!
	}
}
