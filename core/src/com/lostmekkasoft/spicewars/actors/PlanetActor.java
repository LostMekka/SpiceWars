package com.lostmekkasoft.spicewars.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
	public boolean selected = false;

	public PlanetActor(Planet planet, TextureRegion textureRegion, float actorX, float actorY) {
		this.planet = planet;
		this.textureRegion = textureRegion;
		this.actorX = actorX;
		this.actorY = actorY;

		setBounds(actorX, actorY, planet.radius*2, planet.radius*2);
		addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				((PlanetActor)event.getTarget()).selected = true;
				return true;
			}
		});
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
