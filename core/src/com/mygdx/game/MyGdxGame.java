package com.mygdx.game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game implements ApplicationListener
{
	SpriteBatch batch;
	Texture img;
	public static Menu menu;

	@Override
	public void create ()
	{
		Gdx.app.log("MyGdxGame: "," Create");
		menu = new Menu(this);
		setScreen(menu);
	}

	@Override
	public void render ()
	{
		super.render();
	}

	@Override
	public void dispose ()
	{
		super.dispose();
	}
}
