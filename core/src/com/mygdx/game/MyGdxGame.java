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
	public static PlayerClass pclass;
	public static GameClass gclass;
	public static Menu mclass;
	public static PlayerSelectMenu psmclass;
	@Override
	public void create () {
		gclass = new GameClass(this);
		mclass = new Menu(this);
		psmclass = new PlayerSelectMenu(this);

		setScreen(mclass);
	}

	@Override
	public void render () {
		super.render();

	}


	@Override
	public void dispose () {
	}
}
