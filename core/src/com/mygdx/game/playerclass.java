package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class playerclass {
    MyGdxGame game;
    private Texture player1;
    private Texture player2;
    private Texture player3;
    private Texture player4;
    private TextureRegion[][] temp;
    private TextureRegion[] walkFrames;
    private TextureRegion standFrame;
    private Animation WalkAnimation;
    private float stateTime;
    private Sprite playerSprite;
    private SpriteBatch batch;



    private Texture fighter1;
    private Texture fighter2;
    private int select;

    public playerclass(MyGdxGame game) {

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

    public Texture getFighter1() {
        return fighter1;
    }

    public void setFighter1(Texture fighter1) {
        this.fighter1 = fighter1;
    }

    public Texture getFighter2() {
        return fighter2;
    }

    public void setFighter2(Texture fighter2) {
        this.fighter2 = fighter2;
    }


}
