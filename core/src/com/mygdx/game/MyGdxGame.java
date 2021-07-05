package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public class MyGdxGame extends Game implements ApplicationListener {

	//Static instance for GameClass
	public static GameClass gameClass;
	//Static instance for MenuClass
	public static Menu menuClass;
	//Static instance for PlayerSelectMenu Class
	public static PlayerSelectMenu playerSelectMenu;


	@Override
	public void create () {
		//Create Game Class instance using MyGdxGame
		gameClass = new GameClass(this);
		//Create Menu Class instance using MyGdxGame
		menuClass = new Menu(this);
		//Create PlayerSelectMenu instance using MyGdxGame
		playerSelectMenu = new PlayerSelectMenu(this);

		//Set first screen as MenuClass.
		setScreen(menuClass);
	}

	@Override
	public void render () {
		super.render();

	}


	@Override
	public void dispose () {
		gameClass.dispose();
		menuClass.dispose();
		playerSelectMenu.dispose();
	}
}
