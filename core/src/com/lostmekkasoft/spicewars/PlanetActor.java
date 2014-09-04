package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lostmekkasoft.spicewars.data.Planet;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author Kilian Koeltzsch
 */

public class PlanetActor extends SWActor {

	public PlanetActor(Planet actee, TextureRegion textureRegion, float actorX, float actorY) {
		super(actee, textureRegion, actorX, actorY);
//		setBounds(actorX - actee.radius, actorY - actee.radius, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
//		setScale(0.1f);
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.draw(textureRegion, actorX, actorY, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
	}

	@Override
	public void act(float delta) {
		// Do some stuff...
	}
}
