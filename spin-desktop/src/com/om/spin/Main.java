package com.om.spin;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {
	private static boolean rebuildAtlas = true;
	private static boolean drawDebugOutline = true;
	public static void main(String[] args) {
		if (rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.debug = drawDebugOutline;
			TexturePacker2.process(settings, "assets-raw/images",
					"../spin-android/assets/images",
					"spin.pack");
		}
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "spin";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 480;
		

		new LwjglApplication(new MySpin(), cfg);
	}
}
