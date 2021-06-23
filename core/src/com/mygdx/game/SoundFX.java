package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;
public class SoundFX extends ApplicationAdapter {
    Sound sound;

    @Override
    public void create() {
        sound = (Sound) Gdx.audio.newSound(Gdx.files.internal("Starting Music.wav"));
        sound.play(1.0f, 0.0f, 0.8f);
    }


    @Override
    public void render() {

    }

    @Override
    public void dispose() {
        sound.dispose();
    }
}
