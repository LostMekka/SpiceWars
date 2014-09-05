package com.lostmekkasoft.spicewars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * SpiceWars - com.lostmekkasoft.spicewars
 * @author Kilian Koeltzsch
 */

public class SWButton extends Actor {

	float posX;
	float posY;
	float width;
	float height;

	String label;
	Color color;

	Sidebar sidebar;

	public SWButton(float posX, float posY, float width, float height, String label, Color color, Sidebar sidebar) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.label = label;
		this.color = color;
		this.sidebar = sidebar;

		setBounds(posX, posY, width, height);
		setTouchable(Touchable.enabled);
	}

	public void setText(String newLabel) {
		label = newLabel;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// This is done manually in the sidebar
	}

	@Override
	public void act(float delta) {
		// Can buttons even act?
	}

//	public SpiceWars getGame() {
//		return sidebar.game;
//	}
}
