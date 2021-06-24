package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class PlayerClass extends Sprite {

    static PlayerClass playerSingleObject = null;
    MyGdxGame game;
    private Texture player;
    private TextureRegion[] walkFrames;
    private TextureRegion standFrame;
    private Animation<TextureRegion> Walk;

    public Animation<TextureRegion> getIdle() {
        return idle;
    }

    public void setIdle(Animation<TextureRegion> idle) {
        this.idle = idle;
    }

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> punch;
    private Animation<TextureRegion> kick;
    private Animation<TextureRegion> dead;
    private Animation<TextureRegion> win;
    private Animation<TextureRegion> special;

    public Animation<TextureRegion> getLoose() {
        return Loose;
    }

    public void setLoose(Animation<TextureRegion> loose) {
        Loose = loose;
    }

    private Animation<TextureRegion> Loose;

    private int health;

    public PlayerClass(Animation<TextureRegion> idle,Animation<TextureRegion> Walk, Animation<TextureRegion> punch, Animation<TextureRegion> kick, Animation<TextureRegion> dead, Animation<TextureRegion> win, Animation<TextureRegion> special,Animation<TextureRegion> Loose, int health) {
        this.Walk = Walk;
        this.idle =idle;
        this.punch = punch;
        this.kick = kick;
        this.dead = dead;
        this.win = win;
        this.special = special;
        this.health = health;
    }

    public static PlayerClass setPlayers(Animation<TextureRegion> idle,Animation<TextureRegion> walk, Animation<TextureRegion> punch, Animation<TextureRegion> kick, Animation<TextureRegion> dead, Animation<TextureRegion> win, Animation<TextureRegion> special, Animation<TextureRegion> Loose, int health) {
        if (playerSingleObject == null)
            playerSingleObject = new PlayerClass(idle,walk, punch, kick, dead, win, special,Loose ,health);
        return playerSingleObject;
    }


    public static PlayerClass getPlayers() {

        return playerSingleObject;
    }


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

}
