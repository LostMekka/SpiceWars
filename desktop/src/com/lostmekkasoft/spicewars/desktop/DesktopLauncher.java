package com.lostmekkasoft.spicewars.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lostmekkasoft.spicewars.SpiceWars;

public class DesktopLauncher {

	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "SpiceWars";
		config.width = 1280;
		config.height = 960;

		new LwjglApplication(new SpiceWars(), config);
	}
}
