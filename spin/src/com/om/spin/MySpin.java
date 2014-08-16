package com.om.spin;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.om.spin.game.Assets;
import com.om.spin.game.SpinController;
import com.om.spin.game.SpinRenderer;
import com.om.spin.util.AudioManager;

public class MySpin implements ApplicationListener {

	private static final String TAG =MySpin.class.getName();
	private SpinController spinController;
	private SpinRenderer spinRenderer;
	private boolean paused;
	@Override
	public void create() {		
		// Set Libgdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Initialize controller and renderer
		Assets.instance.init(new AssetManager());
		spinController = new SpinController();
		spinRenderer = new SpinRenderer(spinController);
		AudioManager.instance.play(Assets.instance.sounds.start);
		// Game world is active on start
		paused = false;
	}
	@Override
	public void dispose() {
		spinRenderer.dispose();
		Assets.instance.dispose();
		AudioManager.instance.dispose();
	}
	@Override
	public void render() {		
		if(!paused){
			// Update game world by the time that has passed
			// since last rendered frame.
			spinController.update(Gdx.graphics.getDeltaTime());
		}
		// Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f,
				0xff/255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		spinRenderer.render();
	}
	@Override
	public void resize(int width, int height) {
		spinRenderer.resize(width, height);
	}

	@Override
	public void pause() {
		paused=true;
	}
	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
		paused=false;
	}
}
