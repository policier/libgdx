package com.om.spin.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.om.spin.util.Constants;

public class SpinRenderer implements Disposable{

	private SpinController spinController;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	public SpinRenderer(SpinController  spinController){
		this.spinController=spinController;
		init();
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		
	}
	private void init() { 
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
		Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
	}
	public void render () { 
		renderSpinObjects();
	}
	public void resize (int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) *width;
		camera.update();
	}
	
	private void renderSpinObjects() {
		spinController.getCameraHelper().applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Sprite sprite : spinController.getTestSprites()) {
			sprite.draw(batch);
		}
		batch.end();
	}

}
