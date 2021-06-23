package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class opponentclass {
    MyGdxGame game;
    private Animation Walk;
    private Animation punch;
    private Animation kick;
    private Animation dead;
    private Animation win;
    private Animation special;

    private int health;

    private float stateTime;

    public Animation getWalk() {
        return Walk;
    }

    public void setWalk(Animation walk) {
        Walk = walk;
    }

    public Animation getPunch() {
        return punch;
    }

    public void setPunch(Animation punch) {
        this.punch = punch;
    }

    public Animation getKick() {
        return kick;
    }

    public void setKick(Animation kick) {
        this.kick = kick;
    }

    public Animation getDead() {
        return dead;
    }

    public void setDead(Animation dead) {
        this.dead = dead;
    }

    public Animation getWin() {
        return win;
    }

    public void setWin(Animation win) {
        this.win = win;
    }

    public Animation getSpecial() {
        return special;
    }

    public void setSpecial(Animation special) {
        this.special = special;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public opponentclass(Animation walk, Animation punch, Animation kick, Animation dead, Animation win, Animation special, int health) {
        this.Walk = walk;
        this.punch = punch;
        this.kick = kick;
        this.dead = dead;
        this.win = win;
        this.special = special;
        this.health = health;
    }

    public opponentclass(MyGdxGame game) {

        this.game = game;
    }

    public void create(){

//        player1 = new Texture(Gdx.files.internal("player1walk.png"));
//        player2 = new Texture(Gdx.files.internal("Robert Garcia.png"));
//        player3 = new Texture(Gdx.files.internal("Ryo Sakazaki.png"));
//        player4 = new Texture(Gdx.files.internal("Ryuhaku Todoh.png"));
//
//
//        switch (select){
//
//            case 1:
//                player1 = new Texture(Gdx.files.internal("player1walk.png")); // #9
//                TextureRegion[][] temp = TextureRegion.split(player1,
//                        player1.getWidth() / 4,
//                        player1.getHeight() / 1);
//
//                standFrame = new TextureRegion();
//                standFrame = temp[0][0];
//
//                break;
//
//            case 2:
//                player2 = new Texture(Gdx.files.internal("player2walk.png")); // #9
//                TextureRegion[][] temp = TextureRegion.split(player2,
//                        player2.getWidth() / 4,
//                        player2.getHeight() / 1);
//
//                standFrame = new TextureRegion();
//                standFrame = temp[0][0];
//
//                break;
//
//        }
//        player1 = new Texture(Gdx.files.internal("player1walk.png")); // #9
//        TextureRegion[][] temp = TextureRegion.split(player1,
//                player1.getWidth() / 4,
//                player1.getHeight() / 1);
//
//        walkFrames = new TextureRegion[4];
//
//        int index = 0;
//        for (int i = 0; i < 1; i++) {
//            for (int j = 0; j < 4; j++) {
//                walkFrames[index++] = temp[i][j];
//            }
//
//        }
//        WalkAnimation = new Animation(0.33f, walkFrames);
//
//        stateTime = 0.0f;
    }




}
