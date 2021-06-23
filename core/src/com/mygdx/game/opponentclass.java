package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class opponentclass {
    MyGdxGame game;
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

    public opponentclass( Animation<TextureRegion> Walk, Animation<TextureRegion> punch, Animation<TextureRegion> kick, Animation<TextureRegion> dead, Animation<TextureRegion> win, Animation<TextureRegion> special, int health) {

        this.Walk = Walk;
        this.punch = punch;
        this.kick = kick;
        this.dead = dead;
        this.win = win;
        this.special = special;
        this.health = health;    }


}
