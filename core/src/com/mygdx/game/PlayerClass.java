package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class PlayerClass extends Sprite {

    //Singleton Object for Player
    static PlayerClass playerSingleObject = null;

    //Walk Animation instance
    private Animation<TextureRegion> Walk;
    //Idle Animation instance
    private Animation<TextureRegion> idle;
    //Punch Animation instance
    private Animation<TextureRegion> punch;
    //Kick Animation instance
    private Animation<TextureRegion> kick;
    //Dead Animation instance
    private Animation<TextureRegion> dead;
    //Win Animation instance
    private Animation<TextureRegion> win;
    //Special Power Animation instance
    private Animation<TextureRegion> special;
    //Damage Animation instance
    private Animation<TextureRegion> damage;
    //Defend Animation instance
    private Animation<TextureRegion> defend;
    //Loose Animation instance
    private Animation<TextureRegion> Loose;

    //Get Idle Animation Method
    public Animation<TextureRegion> getIdle() {
        return idle;
    }
    //Set Idle Animation Method
    public void setIdle(Animation<TextureRegion> idle) {
        this.idle = idle;
    }
    //Get Defend Animation Method
    public Animation<TextureRegion> getDefend() {
        return defend;
    }
    //Set Defend Animation Method
    public void setDefend(Animation<TextureRegion> defend) {
        this.defend = defend;
    }
    //Get Damage Animation Method
    public Animation<TextureRegion> getDamage() {
        return damage;
    }
    //Set Damage Animation Method
    public void setDamage(Animation<TextureRegion> damage) {
        this.damage = damage;
    }
    //Get Loose Animation Method
    public Animation<TextureRegion> getLoose() {
        return Loose;
    }
    //Set Loose Animation Method
    public void setLoose(Animation<TextureRegion> loose) {
        Loose = loose;
    }

    //Player Health
    private int health;
    //Constructor to set a Player
    public PlayerClass(Animation<TextureRegion> idle,Animation<TextureRegion> Walk, Animation<TextureRegion> punch, Animation<TextureRegion> kick, Animation<TextureRegion> dead, Animation<TextureRegion> win, Animation<TextureRegion> special,Animation<TextureRegion> Loose,Animation<TextureRegion> Damage, Animation<TextureRegion> defend, int health) {
        this.Walk = Walk;
        this.idle =idle;
        this.punch = punch;
        this.kick = kick;
        this.dead = dead;
        this.win = win;
        this.special = special;
        this.health = health;
        this.Loose = Loose;
        this.damage = Damage;
        this.damage = defend;

    }
    //Method to set Player Animations
    public static PlayerClass setPlayers(Animation<TextureRegion> idle,Animation<TextureRegion> walk, Animation<TextureRegion> punch, Animation<TextureRegion> kick, Animation<TextureRegion> dead, Animation<TextureRegion> win, Animation<TextureRegion> special, Animation<TextureRegion> Loose,Animation<TextureRegion> Damage, Animation<TextureRegion> defend, int health) {
        if (playerSingleObject == null)
            playerSingleObject = new PlayerClass(idle,walk, punch, kick, dead, win, special,Loose,Damage,defend,health);
        return playerSingleObject;
    }

    //Get Single Player instance
    public static PlayerClass getPlayers() {

        return playerSingleObject;
    }

    //Method to return walk animation
    public Animation<TextureRegion> getWalk() {
        return Walk;
    }
    //Method to set walk animation
    public void setWalk(Animation<TextureRegion> walk) {
        Walk = walk;
    }
    //Method to return punch animation
    public Animation<TextureRegion> getPunch() {
        return punch;
    }
    //Method to set punch animation
    public void setPunch(Animation<TextureRegion> punch) {
        this.punch = punch;
    }
    //Method to return kick animation
    public Animation<TextureRegion> getKick() {
        return kick;
    }
    //Method to set kick animation
    public void setKick(Animation<TextureRegion> kick) {
        this.kick = kick;
    }
    //Method to return dead animation
    public Animation<TextureRegion> getDead() {
        return dead;
    }
    //Method to set dead animation
    public void setDead(Animation<TextureRegion> dead) {
        this.dead = dead;
    }
    //Method to return win animation
    public Animation<TextureRegion> getWin() {
        return win;
    }
    //Method to set win animation
    public void setWin(Animation<TextureRegion> win) {
        this.win = win;
    }
    //Method to return special power animation
    public Animation<TextureRegion> getSpecial() {
        return special;
    }
    //Method to set special power animation
    public void setSpecial(Animation<TextureRegion> special) {
        this.special = special;
    }
    //Method to return health of the player
    public int getHealth() {
        return health;
    }
    //Method to set health of the player
    public void setHealth(int health) {
        this.health = health;
    }

}
