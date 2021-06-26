package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlayerSelectMenu implements Screen {

    MyGdxGame game;
    private Texture background;
    private Texture menuBG;
    private Texture kingpotrait;
    private Texture Robertpotrait;
    private Texture Ryupotrait;
    private Texture Sakapotrait;

    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;

    Image imgBackground;
    Image menuBackground;
    Image kingimg;
    Image Robertimg;
    Image Ryuimg;
    Image Sakaimg;

    //Animation<TextureRegion> animation;
    float elapsed;
    TextButton king;
    Sound audio1;
    TextButton Robert;
    TextButton Sakazaki;
    TextButton Ryuhaku;

    boolean playerselected = false;

    PlayerClass thePlayer =  PlayerClass.setPlayers(null,null,null,null,null,null,null,null,100);
    OpponentClass theOpponent = OpponentClass.setOpponent(null,null,null,null,null,null,null,null,100);;

    public PlayerSelectMenu(MyGdxGame game)
    {
        this.game = game;
    }

    public void create()
    {
        batch = new SpriteBatch();
        stage = new Stage();
        menuBG = new Texture("Starting Assets/assets/finishedbg.png");
        background = new Texture("Starting Assets/assets/menuBackGround.gif");
        skin = new Skin(Gdx.files.internal("Starting Assets/assets/uiskin.json"));
        king = new TextButton("King", skin, "default");
        Robert = new TextButton("Robert",skin,"default");
        Sakazaki = new TextButton("Sakazaki", skin, "default");
        Ryuhaku = new TextButton("Ryuhaku", skin, "default");

        audio1 = Gdx.audio.newSound(Gdx.files.internal("Starting Assets/assets/buttonsound.wav"));
        kingpotrait = new Texture("Kingpotrait.png");
        Robertpotrait = new Texture("Robertpotrait.png");
        Ryupotrait = new Texture("Ryuhakupotrait.png");
        Sakapotrait = new Texture("Sakazakipotrait.png");


        imgBackground = new Image(background);
        imgBackground.setSize(2300,1100);
        imgBackground.setZIndex(1);

        menuBackground = new Image(menuBG);
        menuBackground.setSize(1800,900);
        menuBackground.setX(200);
        menuBackground.setY(200);
        menuBackground.setZIndex(2);

        kingimg = new Image(kingpotrait);
        kingimg.setSize(300,200);
        kingimg.setX(600);
        kingimg.setY(700);

        Robertimg = new Image(Robertpotrait);
        Robertimg.setSize(300,200);
        Robertimg.setX(1300);
        Robertimg.setY(700);

        Ryuimg = new Image(Ryupotrait);
        Ryuimg.setSize(300,200);
        Ryuimg.setX(600);
        Ryuimg.setY(400);

        Sakaimg = new Image(Sakapotrait);
        Sakaimg.setSize(300,200);
        Sakaimg.setX(1300);
        Sakaimg.setY(400);

        styleButton(king);
        king.setPosition(600, 650);

        styleButton(Robert);
        Robert.setPosition(1300, 650);

        styleButton(Ryuhaku);
        Ryuhaku.setPosition(600, 350);

        styleButton(Sakazaki);
        Sakazaki.setPosition(1300, 350);


            king.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // btnSound.play(1.0f);

                    if(!playerselected){
                        assignPlayer("king");
                        playerselected = true;
                    }
                    else {
                        assignOpponent("king");
                        game.setScreen(MyGdxGame.gclass);
                    }
                }
            });
            Robert.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // btnSound.play(1.0f);
                    if(!playerselected){
                        assignPlayer("robert");
                        playerselected = true;
                    }
                    else {
                        assignOpponent("robert");
                        game.setScreen(MyGdxGame.gclass);
                    }

                }
            });
            Ryuhaku.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // btnSound.play(1.0f);
                    if(!playerselected){
                        assignPlayer("ryu");
                        playerselected = true;
                    }
                    else {
                        assignOpponent("ryu");
                        game.setScreen(MyGdxGame.gclass);
                    }

                }
            });

            Sakazaki.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // btnSound.play(1.0f);
                    if(!playerselected){
                        assignPlayer("ryo");
                        playerselected = true;
                    }
                    else {
                        assignOpponent("ryo");
                        game.setScreen(MyGdxGame.gclass);
                    }
                }
            });


        stage.addActor(imgBackground);
        stage.addActor(menuBackground);
        stage.addActor(kingimg);
        stage.addActor(Ryuimg);
        stage.addActor(Robertimg);
        stage.addActor(Sakaimg);
        stage.addActor(king);
        stage.addActor(Robert);
        stage.addActor(Ryuhaku);
        stage.addActor(Sakazaki);

        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void show() {
        Gdx.app.log("MenuScreen: ","MenuScreen Show Called");
        create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        stage.draw();
        batch.end();
    }

    public void styleButton(TextButton btn){
        btn.setWidth(300f);
        btn.setHeight(60f);
        btn.getLabel().setFontScale(3);
        btn.setColor(Color.GOLD);
    }

    @Override
    public void resize(int width, int height) {

    }
    public void assignPlayer(String player_name){



        switch (player_name){

            case "king":
               thePlayer.setIdle(createanimation(new Texture(Gdx.files.internal("King/kingIdle.png")),4,1, 0.33f)); // #9
               thePlayer.setWalk(createanimation(new Texture(Gdx.files.internal("King/kingWalk.png")),4,1, 0.33f)); // #9
               thePlayer.setKick(createanimation(new Texture(Gdx.files.internal("King/kingKick.png")),3,1, 0.33f));
               thePlayer.setPunch(createanimation(new Texture(Gdx.files.internal("King/kingPunch.png")),3,1, 0.33f));
               break;

            case "robert":
                thePlayer.setIdle(createanimation(new Texture(Gdx.files.internal("Robertidle.png")),6,1, 0.33f)); // #9
                thePlayer.setWalk(createanimation(new Texture(Gdx.files.internal("robertwalk.png")),3,1, 0.33f)); // #9
                thePlayer.setKick(createanimation(new Texture(Gdx.files.internal("robertkick.png")),3,1, 1f));
                thePlayer.setPunch(createanimation(new Texture(Gdx.files.internal("robertpunch.png")),3,1, 1f));
                break;

            case "ryo":
                thePlayer.setIdle(createanimation(new Texture(Gdx.files.internal("ryoIdle.png")),4,1, 0.33f)); // #9
                thePlayer.setWalk(createanimation(new Texture(Gdx.files.internal("ryowalk.png")),4,1, 0.33f)); // #9
                thePlayer.setKick(createanimation(new Texture(Gdx.files.internal("ryokick.png")),4,1, 0.33f));
                thePlayer.setPunch(createanimation(new Texture(Gdx.files.internal("ryopunch.png")),3,1, 0.33f));
                break;

            case "ryu":
                thePlayer.setIdle(createanimation(new Texture(Gdx.files.internal("Ryu/ryuIdle.png")),4,1, 0.33f)); // #9
                thePlayer.setWalk(createanimation(new Texture(Gdx.files.internal("Ryu/ryuWalk.png")),5,1, 0.33f)); // #9
                thePlayer.setKick(createanimation(new Texture(Gdx.files.internal("Ryu/ryuKick2.png")),1,1, 0.33f));
                thePlayer.setPunch(createanimation(new Texture(Gdx.files.internal("Ryu/ryuPunch.png")),3,1, 0.33f));
                break;
        }

    }

    public void assignOpponent(String player_name){



        switch (player_name){

            case "king":
                theOpponent.setIdle(createanimation(new Texture(Gdx.files.internal("King/kingIdle.png")),4,1, 0.33f )); // #9
                theOpponent.setWalk(createanimation(new Texture(Gdx.files.internal("King/kingWalk.png")),4,1, 1f)); // #9
                theOpponent.setKick(createanimation(new Texture(Gdx.files.internal("King/kingKick.png")),3,1, 0.025f));
                theOpponent.setPunch(createanimation(new Texture(Gdx.files.internal("King/kingPunch.png")),3,1, 0.025f));
                break;

            case "robert":
                theOpponent.setIdle(createanimation(new Texture(Gdx.files.internal("Robertidle.png")),6,1, 0.33f)); // #9
                theOpponent.setWalk(createanimation(new Texture(Gdx.files.internal("robertwalk.png")),3,1, 0.33f)); // #9
                theOpponent.setKick(createanimation(new Texture(Gdx.files.internal("robertkick.png")),4,1, 1f));
                theOpponent.setPunch(createanimation(new Texture(Gdx.files.internal("robertpunch.png")),3,1, 1f));
                break;

            case "ryo":
                theOpponent.setIdle(createanimation(new Texture(Gdx.files.internal("ryoIdle.png")),4,1, 0.33f)); // #9
                theOpponent.setWalk(createanimation(new Texture(Gdx.files.internal("ryowalk.png")),4,1, 0.33f)); // #9
                theOpponent.setKick(createanimation(new Texture(Gdx.files.internal("ryokick.png")),5,1, 0.33f));
                theOpponent.setPunch(createanimation(new Texture(Gdx.files.internal("ryopunch.png")),3,1, 0.33f));
                break;

            case "ryu":
                theOpponent.setIdle(createanimation(new Texture(Gdx.files.internal("Ryu/ryuIdle.png")),4,1, 0.33f)); // #9
                theOpponent.setWalk(createanimation(new Texture(Gdx.files.internal("Ryu/ryuWalk.png")),5,1, 0.33f)); // #9
                theOpponent.setKick(createanimation(new Texture(Gdx.files.internal("Ryu/ryuKick.png")),4,1, 1f));
                theOpponent.setPunch(createanimation(new Texture(Gdx.files.internal("Ryu/ryuPunch.png")),3,1, 1f));
                break;
        }

    }

    public Animation<TextureRegion> createanimation(Texture player, int width, int height, float time){


        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(player,
                player.getWidth() / width,
                player.getHeight() / height);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] playerFrames = new TextureRegion[width * height];
        int index = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                playerFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        return new Animation<TextureRegion>(time, playerFrames);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
