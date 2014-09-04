package com.lostmekkasoft.spicewars.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lostmekkasoft.spicewars.data.Army;
import com.lostmekkasoft.spicewars.data.Projectile;

/**
 * SpiceWars - com.lostmekkasoft.spicewars.actors
 * @author Kilian Koeltzsch
 */

public class ProjectileActor extends Actor {

	public static final float ICON_SCALE = 0.3f;
	
	TextureRegion textureRegion;
	public float actorX;
	public float actorY;

	public Projectile projectile;

	public ProjectileActor(Projectile projectile, TextureRegion textureRegion, float actorX, float actorY) {
		this.projectile = projectile;
		this.textureRegion = textureRegion;
		this.actorX = actorX;
		this.actorY = actorY;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		actorX = (float)projectile.position.x;
		actorY = (float)projectile.position.y;
		float w = textureRegion.getRegionWidth() * ICON_SCALE;
		float h = textureRegion.getRegionWidth() * ICON_SCALE;
		batch.setColor(projectile.team.color);
		batch.draw(textureRegion, actorX - w/2, actorY - h/2, w, h);
	}

	@Override
	public void act(float delta) {
		// And action!
	}
}
