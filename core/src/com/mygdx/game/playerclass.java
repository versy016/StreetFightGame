package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class playerclass {
    MyGdxGame game;
    private Texture player;
    private TextureRegion[] walkFrames;
    private TextureRegion standFrame;
    private Animation<TextureRegion> Walk;
    private Animation<TextureRegion> punch;
    private Animation<TextureRegion> kick;
    private Animation<TextureRegion> dead;
    private Animation<TextureRegion> win;
    private Animation<TextureRegion> special;

    private int health;

    private float stateTime;

    public Animation<TextureRegion> getWalk() {
        return Walk;
    }

    public void setWalk(Animation<TextureRegion> walk) {
        Walk = walk;
    }

    public Animation<TextureRegion> getPunch() {
        return punch;
    }

    public void setPunch(Animation<TextureRegion> punch) {
        this.punch = punch;
    }

    public Animation<TextureRegion> getKick() {
        return kick;
    }

    public void setKick(Animation<TextureRegion> kick) {
        this.kick = kick;
    }

    public Animation<TextureRegion> getDead() {
        return dead;
    }

    public void setDead(Animation<TextureRegion> dead) {
        this.dead = dead;
    }

    public Animation<TextureRegion> getWin() {
        return win;
    }

    public void setWin(Animation<TextureRegion> win) {
        this.win = win;
    }

    public Animation<TextureRegion> getSpecial() {
        return special;
    }

    public void setSpecial(Animation<TextureRegion> special) {
        this.special = special;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public playerclass(int health) {
          this.health = health;
    }



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





