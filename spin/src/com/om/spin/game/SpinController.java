package com.om.spin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.om.spin.util.AudioManager;
import com.om.spin.util.CameraHelper;

public class SpinController {
	private static final int _GRAD = 360;
	private static final int _SPEED_MAX = 100;
	private static final int _SPEED_INCREMENT = 10;
	private static final int _SPEED_DECREMENT = 7;
	private static final String TAG =SpinController.class.getName();
	private  Sprite [] testSprites;
	private  int selectedSprite;
	private  CameraHelper cameraHelper;
	private  float speed=0;
	private  float rotation;
	private int currentPosition;

	public  SpinController () {
		init();
	}
	private void init() { 
		cameraHelper = new CameraHelper();
		initSpriteObjects();
	}
	public CameraHelper getCameraHelper() {
		return cameraHelper;
	}

	public void update (float deltaTime) {
		handleUserInput(deltaTime);
		cameraHelper.update(deltaTime);
	}
	public Sprite[] getTestSprites() {
		return testSprites;
	}

	private void initSpriteObjects() {
		testSprites = new Sprite[2];
		// Create a list of texture regions
		Array<TextureRegion> regions = new Array<TextureRegion>();
		
		regions.add(Assets.instance.circle.head);
		regions.add(Assets.instance.bottle.head);
		//set properties of the circle
		Sprite spr = new Sprite(regions.get(0));
		spr.setSize(2, 2);
		spr.setOrigin(0f,0f);
		spr.setPosition(0, 0);
		testSprites[0] = spr;

		//set properties of the bottle
		Sprite spr1 = new Sprite(regions.get(1));
		spr1.setSize(0.5f, 0.8F);
		spr1.setOrigin(spr1.getWidth() / 2.0f,	spr1.getHeight() / 2.0f);
		spr1.setPosition(spr.getWidth() / 2.9f,	spr.getHeight() / 3.1f);
		testSprites[1] = spr1;
		selectedSprite = 1;
	}	

	private void handleUserInput (float deltaTime) {
		if (Gdx.input.isTouched()) {
			AudioManager.instance.stopMusic();
			spinBottleSprite(deltaTime);
		}else{
			if (isBottleMoving()){
				slowDownBottleRotation(deltaTime);
				if(!isBottleMoving()){
					playPositionSound();
				}
			}
		}	
		// Camera Controls (move)
		cameraMoveControl(deltaTime);
		// Camera Controls (zoom)
		cameraZoomControl(deltaTime);
	}
	private void cameraMoveControl(float deltaTime) {
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *=camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed,0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed,0);
		if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0,-camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))cameraHelper.setPosition(0, 0);
	}
	private void cameraZoomControl(float deltaTime) {
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *=camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))	cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
	}

	private void spinBottleSprite(float deltaTime) {
		speed = Math.max(speed, 10);
		speed = speed + deltaTime * _SPEED_INCREMENT;
		speed = Math.min(_SPEED_MAX, speed);
		rotation = (rotation + speed) % _GRAD;
		testSprites[selectedSprite].setRotation(rotation);
		setBottlePosition();
	}

    public void setBottlePosition(){
    	if(rotation >=0 && rotation <=90){
    		currentPosition=1;
    	}
    	if(rotation >90 && rotation <=180){
    		currentPosition=2;
	    	
    	}
    	if(rotation >180 && rotation <=270){
    		currentPosition=3;
	    }
    	if(rotation >270 && rotation <=360){
    		currentPosition=4;
	    }
    	
    }
    private void playPositionSound(){
    	switch (currentPosition) {
    	case  1:     		
    		AudioManager.instance.play(Assets.instance.sounds.green);
    		break;
    	case  2:
    		AudioManager.instance.play(Assets.instance.sounds.yellow);
    		break;
    	case  3:
    		AudioManager.instance.play(Assets.instance.sounds.blue);
    		break;
    	case  4:
    		AudioManager.instance.play(Assets.instance.sounds.red);
    		break;
    	default:
    		break;
    	}
    }
	private void slowDownBottleRotation(float deltaTime) {
		speed = speed - deltaTime * _SPEED_DECREMENT;
		speed = Math.max(speed, 0);
		rotation = (rotation + speed) % _GRAD;
		if(isBottleMoving()){
			testSprites[selectedSprite].setRotation(rotation);
			setBottlePosition();
		}
	}
	private boolean isBottleMoving() {
		return speed>0;
	}
	private void moveCamera (float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}
}
