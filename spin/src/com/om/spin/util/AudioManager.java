package com.om.spin.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
	public static final AudioManager instance = new AudioManager();
	private Music playingMusic;
	private Sound playingSound;
	
	private AudioManager () { }
	public void play (Sound sound) {
		stopSound();
		this.playingSound=sound;
		play(sound, 1);
	}
	public void play (Sound sound, float volume) {
		play(sound, volume, 1);
	}
	public void play (Sound sound, float volume, float pitch) {
		play(sound, volume, pitch, 0);
	}
	public void play (Sound sound, float volume, float pitch,float pan) {
		sound.play(0.5F* volume,			pitch, pan);
	}

	public void play (Music music) {
		stopMusic();
		playingMusic = music;
		music.setLooping(true);
		music.play();
	}
	public void stopMusic () {
		if (playingMusic != null) playingMusic.stop();
	}
	public void stopSound () {
		if (playingSound != null) playingSound.stop();
	}
	public void dispose(){
//		playingMusic.dispose();
		playingSound.dispose();
	}
}
