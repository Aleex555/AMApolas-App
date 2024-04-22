package com.losamapolas.roscodrom;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.losamapolas.roscodrom.Roscodrom;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Roscodrom");
		config.setWindowedMode(550, 880);
		config.useVsync(true);
		new Lwjgl3Application(new Roscodrom(), config);
	}
}
