package com.lostmekkasoft.spicewars.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lostmekkasoft.spicewars.data.Army;

/**
 * SpiceWars - com.lostmekkasoft.spicewars.actors
 * @author Kilian Koeltzsch
 */

public class ArmyActor extends Actor {

	TextureRegion textureRegion;
	public float actorX;
	public float actorY;

	public Army army;

	public ArmyActor(Army army, TextureRegion textureRegion, float actorX, float actorY) {
		this.army = army;
		this.textureRegion = textureRegion;
		this.actorX = actorX;
		this.actorY = actorY;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(army.team.color);
		batch.draw(textureRegion, actorX, actorY, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
	}

	@Override
	public void act(float delta) {
		// And action!
	}
}
