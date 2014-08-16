package com.om.spin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.om.spin.util.Constants;

public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	public AssetBottle bottle;
	public AssetCircle circle;
	public AssetMusic music;
	public AssetSounds sounds;
	// singleton: prevent instantiation from other classes
	private Assets () {}
	public void init (AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		assetManager.load("music/11 - Bruno Mars  - locked out of heaven (2).mp3",
				Music.class);
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,
				TextureAtlas.class);
		
		// load sounds
		assetManager.load("sounds/start.wav", Sound.class);
		assetManager.load("sounds/green.wav", Sound.class);
		assetManager.load("sounds/red.wav", Sound.class);
		assetManager.load("sounds/yellow.wav", Sound.class);
		assetManager.load("sounds/blue.wav", Sound.class);
		
		
		// start loading assets and wait until finished
		assetManager.finishLoading();
		Gdx.app.debug(TAG, "# of assets loaded: "
				+ assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "asset: " + a);

		TextureAtlas atlas =
				assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		// create game resource objects
		bottle = new AssetBottle(atlas);
		circle = new AssetCircle(atlas);
		music = new AssetMusic(assetManager);
		sounds = new AssetSounds(assetManager);
	}
	@Override
	public void dispose () {
		assetManager.dispose();
	}
	@Override
	public void error (String filename, Class type,
			Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '"
				+ filename + "'", (Exception)throwable);
	}
	public class AssetBottle {
		public final AtlasRegion head;
		public AssetBottle (TextureAtlas atlas) {
			head = atlas.findRegion("bottle");
		}
	}

	public class AssetCircle {
		public final AtlasRegion head;
		public AssetCircle (TextureAtlas atlas) {
			head = atlas.findRegion("Circle");
		}
	}
	public class AssetMusic {
		public final Music song01;
		public AssetMusic (AssetManager am) {
			song01 = am.get("music/11 - Bruno Mars  - locked out of heaven (2).mp3",
					Music.class);
		}
	}
	public class AssetSounds {
		public final Sound start;
		public final Sound red;
		public final Sound yellow;
		public final Sound blue;
		public final Sound green;

		public AssetSounds (AssetManager am) {
			start = am.get("sounds/start.wav", Sound.class);
			red = am.get("sounds/red.wav",
					Sound.class);
			yellow = am.get("sounds/yellow.wav", Sound.class);
			blue = am.get("sounds/blue.wav",
					Sound.class);
			green = am.get("sounds/green.wav", Sound.class);
		}
	}
}
