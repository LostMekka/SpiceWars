package com.lostmekkasoft.spicewars.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.lostmekkasoft.spicewars.SpiceWars;
import com.lostmekkasoft.spicewars.data.Planet;
import com.lostmekkasoft.spicewars.data.Point;


/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author Kilian Koeltzsch
 */

public class PlanetActor extends Actor {

	TextureRegion textureRegion;
	public float actorX;
	public float actorY;

	public float planetSize;

	public Planet planet;

	public PlanetActor(final Planet planet, TextureRegion textureRegion, float actorX, float actorY) {
		this.planet = planet;
		this.textureRegion = textureRegion;
		this.actorX = actorX - planet.radius / SpiceWars.planetTextureFactor;
		this.actorY = actorY - planet.radius / SpiceWars.planetTextureFactor;

		planetSize = (planet.radius * 2) / SpiceWars.planetTextureFactor;

		setBounds(this.actorX, this.actorY, planetSize, planetSize);
		addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Point pointMouse = new Point(x, y);
				Point pointPlanet = new Point(planetSize/2, planetSize/2);
				if (pointPlanet.squaredDistanceTo(pointMouse) < planet.radius*planet.radius) {
					((PlanetActor)event.getTarget()).planet.getGame().selectedPlanet = planet;
				}
				return true;
			}
		});
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.setColor(planet.team.color);
		batch.draw(textureRegion, actorX, actorY, planetSize, planetSize);
	}

	@Override
	public void act(float delta) {
		// Do some stuff...
	}
}
