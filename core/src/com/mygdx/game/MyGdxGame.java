package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game implements ApplicationListener {
	SpriteBatch batch;
	Texture img;
	public static	playerclass pclass;
	public static GameClass gclass;

	@Override
	public void create () {
		pclass = new playerclass(this);
		gclass = new GameClass(this);
		setScreen(gclass);
	}

	@Override
	public void render () {
		super.render();

	}


	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
