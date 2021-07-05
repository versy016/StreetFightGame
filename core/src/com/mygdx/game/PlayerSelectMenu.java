package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PlayerSelectMenu implements Screen {

    MyGdxGame game;
    private Texture background;
    private Texture menuBG;
    private Texture kingpotrait;
    private Texture Robertpotrait;
    private Texture Ryupotrait;
    private Texture Kenpotrait;

    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;

    Image imgBackground;
    Image menuBackground;
    Image messageBoxBackGround;
    Image kingimg;
    Image Robertimg;
    Image Ryuimg;
    Image Kenimg;
    Label message;

    //Animation<TextureRegion> animation;
    float elapsed;
    TextButton king;
    Sound audio1;
    TextButton Robert;
    TextButton Ken;
    TextButton Ryuhaku;
    TextButton btnMessageBox;

    boolean playerselected = false;

    PlayerClass thePlayer =  PlayerClass.setPlayers(null,null,null,null,null,null,null,null,null,null,100);
    OpponentClass theOpponent = OpponentClass.setOpponent(null,null,null,null,null,null,null,null,null,null,100);

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
        Ken = new TextButton("Ken", skin, "default");
        Ryuhaku = new TextButton("Ryu", skin, "default");

        audio1 = Gdx.audio.newSound(Gdx.files.internal("Starting Assets/assets/buttonsound.wav"));
        kingpotrait = new Texture("King/kingPotrait.png");
        Robertpotrait = new Texture("Robertpotrait.png");
        Ryupotrait = new Texture("Ryuhakupotrait.png");
        Kenpotrait = new Texture("Sakazakipotrait.png");




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
        kingimg.setX(620);
        kingimg.setY(700);

        Robertimg = new Image(Robertpotrait);
        Robertimg.setSize(300,200);
        Robertimg.setX(1300);
        Robertimg.setY(700);

        Ryuimg = new Image(Ryupotrait);
        Ryuimg.setSize(300,200);
        Ryuimg.setX(600);
        Ryuimg.setY(400);

        Kenimg = new Image(Kenpotrait);
        Kenimg.setSize(300,200);
        Kenimg.setX(1300);
        Kenimg.setY(400);

        styleButton(king);
        king.setPosition(600, 650);

        styleButton(Robert);
        Robert.setPosition(1300, 650);

        styleButton(Ryuhaku);
        Ryuhaku.setPosition(600, 350);

        styleButton(Ken);
        Ken.setPosition(1300, 350);


            king.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // btnSound.play(1.0f);

                    if(!playerselected){
                        assignPlayer("king");
                        messageBoxBackGround.setVisible(true);
                        message.setVisible(true);
                        btnMessageBox.setVisible(true);
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
                        messageBoxBackGround.setVisible(true);
                        message.setVisible(true);
                        btnMessageBox.setVisible(true);
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
                        messageBoxBackGround.setVisible(true);
                        message.setVisible(true);
                        btnMessageBox.setVisible(true);
                        playerselected = true;
                    }
                    else {
                        assignOpponent("ryu");
                        game.setScreen(MyGdxGame.gclass);
                    }

                }
            });

            Ken.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // btnSound.play(1.0f);
                    if(!playerselected){
                        assignPlayer("ken");
                        messageBoxBackGround.setVisible(true);
                        message.setVisible(true);
                        btnMessageBox.setVisible(true);
                        playerselected = true;
                    }
                    else {
                        assignOpponent("ken");
                        game.setScreen(MyGdxGame.gclass);
                    }
                }
            });
        //***********************************************************************MessageBox*********************************************************************************************
        message = new Label("Please Select Your Opponent Now!",new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        btnMessageBox = new TextButton("OK", skin, "default");
        messageBoxBackGround = new Image(menuBG);
        messageBoxBackGround.setSize(900,450);
        messageBoxBackGround.setX(650);
        messageBoxBackGround.setY(450);
        messageBoxBackGround.setZIndex(2);
        messageBoxBackGround.setVisible(false);


        message.setFontScale(2,2);
        message.setPosition(875,700);
        message.setVisible(false);

        styleButton(btnMessageBox);
        btnMessageBox.setPosition(950, 550);
        btnMessageBox.setVisible(false);

        btnMessageBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                messageBoxBackGround.setVisible(false);
                message.setVisible(false);
                btnMessageBox.setVisible(false);
            }
        });
        //***********************************************************************MessageBox*********************************************************************************************


        stage.addActor(imgBackground);
        stage.addActor(menuBackground);
        stage.addActor(kingimg);
        stage.addActor(Ryuimg);
        stage.addActor(Robertimg);
        stage.addActor(Kenimg);
        stage.addActor(king);
        stage.addActor(Robert);
        stage.addActor(Ryuhaku);
        stage.addActor(Ken);
        stage.addActor(messageBoxBackGround);
        stage.addActor(message);
        stage.addActor(btnMessageBox);

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
               thePlayer.setIdle(createAnimation(new Texture(Gdx.files.internal("King/kingIdle.png")),4,1, 0.33f)); // #9
               thePlayer.setWalk(createAnimation(new Texture(Gdx.files.internal("King/kingWalk.png")),4,1, 0.33f)); // #9
               thePlayer.setSpecial(createAnimation(new Texture(Gdx.files.internal("King/kingKick.png")),3,1, 0.33f));
               thePlayer.setPunch(createAnimation(new Texture(Gdx.files.internal("King/kingPunch.png")),3,1, 0.33f));

               TextureRegion[] specialFrames = new TextureRegion[6];
               Texture specialSheet = new Texture("King/kingSpecial.png");
                specialFrames[0] = new TextureRegion(specialSheet,1,1,58,100);
                specialFrames[1] = new TextureRegion(specialSheet,65,1,54,100);
                specialFrames[2] = new TextureRegion(specialSheet,119,1,56,100);
                specialFrames[3] = new TextureRegion(specialSheet,175,1,58,100);
                specialFrames[4] = new TextureRegion(specialSheet,231,1,114,100);
                specialFrames[5] = new TextureRegion(specialSheet,342,1,58,100);

                thePlayer.setKick(new Animation<>(0.25f, specialFrames));
                thePlayer.setDead(createAnimation(new Texture(Gdx.files.internal("King/kingDead.png")),3,1, 0.33f));
                thePlayer.setWin(createAnimation(new Texture(Gdx.files.internal("King/kingWin.png")),3,1, 0.33f));
                thePlayer.setLoose(createAnimation(new Texture(Gdx.files.internal("King/kingLoose.png")),3,1, 0.33f));
                thePlayer.setDamage(createAnimation(new Texture(Gdx.files.internal("King/kingDamage.png")),3,1, 0.33f));
                thePlayer.setDefend(createAnimation(new Texture(Gdx.files.internal("King/kingDefend.png")),1,1, 0.33f));

                break;

            case "robert":
                thePlayer.setIdle(createanimation(new Texture(Gdx.files.internal("Robert/robertIdle.png")),6,1, 0.33f)); // #9

                Texture robertwalksheet = new Texture("Robert/robertWalk.png");

                TextureRegion[] robertWalkFrames = new TextureRegion[3];

                robertWalkFrames[0] = new TextureRegion(robertwalksheet,1,1,90,155);
                robertWalkFrames[1] = new TextureRegion(robertwalksheet,90,1,110,135);
                robertWalkFrames[2] = new TextureRegion(robertwalksheet,205,1,90,155);

                thePlayer.setWalk(new Animation<TextureRegion>(0.33f,robertWalkFrames)); // #9


                Texture robertkicksheet = new Texture("Robert/robertKick.png");

                TextureRegion[] robertKickFrames = new TextureRegion[3];

                robertKickFrames[0] = new TextureRegion(robertkicksheet,1,1,100,155);
                robertKickFrames[1] = new TextureRegion(robertkicksheet,110,1,110,135);
                robertKickFrames[2] = new TextureRegion(robertkicksheet,215,1,160,155);

                thePlayer.setKick(new Animation<TextureRegion>(0.33f,robertKickFrames));


                Texture punchsheet = new Texture("Robert/robertPunch.png");

                TextureRegion[] robertPunchFrames = new TextureRegion[2];


                robertPunchFrames[0] = new TextureRegion(punchsheet,1,1,100,155);
                robertPunchFrames[1] = new TextureRegion(punchsheet,105,1,140,155);

                thePlayer.setPunch(new Animation<TextureRegion>(0.33f,robertPunchFrames));
                thePlayer.setSpecial(createanimation(new Texture(Gdx.files.internal("Robert/robertSpecial.png")),1,1, 0.33f));
                thePlayer.setDamage(createanimation(new Texture(Gdx.files.internal("Robert/robertDamage.png")),3,1, 0.33f));
                thePlayer.setDead(createanimation(new Texture(Gdx.files.internal("Robert/robDead.png")),6,1, 0.33f));
                thePlayer.setWin(createanimation(new Texture(Gdx.files.internal("Robert/robertWin.png")),3,1, 0.33f));
                thePlayer.setLoose(createanimation(new Texture(Gdx.files.internal("Robert/robertLose.jpg")),4,1, 0.33f));
                thePlayer.setDefend(createanimation(new Texture(Gdx.files.internal("Robert/robertDefend.png")),1,1, 0.33f));

                break;

            case "ken":
                thePlayer.setIdle(createAnimation(new Texture(Gdx.files.internal("Ken/kenIdle.png")),4,1, 0.33f)); // #9
                thePlayer.setWalk(createAnimation(new Texture(Gdx.files.internal("Ken/kenWalk.png")),5,1, 0.33f)); // #9

                TextureRegion[] kenKickFrames = new TextureRegion[3];
                Texture kenKickSheet = new Texture("Ken/kenKick.png");
                kenKickFrames[0] = new TextureRegion(kenKickSheet,1,1,43,88);
                kenKickFrames[1] = new TextureRegion(kenKickSheet,45,1,45,88);
                kenKickFrames[2] = new TextureRegion(kenKickSheet,90,1,70,88);
                thePlayer.setKick(new Animation<>(0.33f, kenKickFrames));

                thePlayer.setPunch(createAnimation(new Texture(Gdx.files.internal("Ken/kenPunch.png")),2,1, 0.25f));

                TextureRegion[] kenSpecialFrames = new TextureRegion[3];
                Texture kenSpecialSheet = new Texture("Ken/kenSpecial.png");
                kenSpecialFrames[0] = new TextureRegion(kenSpecialSheet,1,1,60,88);
                kenSpecialFrames[1] = new TextureRegion(kenSpecialSheet,60,1,60,88);
                kenSpecialFrames[2] = new TextureRegion(kenSpecialSheet,130,1,105,88);
                thePlayer.setSpecial(new Animation<>(0.25f, kenSpecialFrames));
                thePlayer.setDamage(createAnimation(new Texture(Gdx.files.internal("Ken/kenDamage.png")),2,1, 0.33f));
                thePlayer.setDead(createAnimation(new Texture(Gdx.files.internal("Ken/kenDead.png")),2,1, 0.33f));
                thePlayer.setWin(createAnimation(new Texture(Gdx.files.internal("Ken/kenWin.png")),3,1, 0.33f));

                Texture walkSheet = new Texture("Ken/kenWalk.png");
                Texture looseSheet = new Texture("Ken/kenLoose.png");

                TextureRegion[] kenLooseFrames = new TextureRegion[3];

                kenLooseFrames[0] = new TextureRegion(walkSheet,1,1,40,88);
                kenLooseFrames[1] = new TextureRegion(walkSheet,40,1,40,88);
                kenLooseFrames[2] = new TextureRegion(looseSheet,2,1,40,88);
                thePlayer.setLoose(new Animation<>(0.33f, kenLooseFrames));
                thePlayer.setDefend(createAnimation(new Texture(Gdx.files.internal("Ken/kenDefend.jpg")),1,1, 0.33f));

                break;

            case "ryu":
                thePlayer.setIdle(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuIdle.png")),4,1, 0.33f)); // #9
                thePlayer.setWalk(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuWalk.png")),5,1, 0.33f)); // #9
                thePlayer.setKick(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuKick2.png")),1,1, 0.33f));
                thePlayer.setPunch(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuPunch.png")),3,1, 0.33f));
                thePlayer.setSpecial(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuUlt.png")),3,1, 0.33f));
                thePlayer.setDamage(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuDamage.png")),3,1, 0.33f));
                thePlayer.setDead(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuDead.png")),2,1, 0.33f));
                thePlayer.setWin(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuWin.png")),3,1, 0.33f));
                Texture ryuwalkSheet = new Texture("Ryu/ryuIdle.png");
                Texture ryulooseSheet = new Texture("Ryu/ryuLoose.png");

                TextureRegion[] ryuLooseFrames = new TextureRegion[2];

                ryuLooseFrames[0] = new TextureRegion(ryuwalkSheet,1,1,45,88);
                ryuLooseFrames[1] = new TextureRegion(ryulooseSheet,70,1,50,88);
                thePlayer.setLoose(new Animation<>(0.33f, ryuLooseFrames));
                thePlayer.setDefend(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuDefend.png")),1,1, 0.33f));

                break;
        }

    }

    public void assignOpponent(String player_name){



        switch (player_name){

            case "king":
                theOpponent.setIdle(createAnimation(new Texture(Gdx.files.internal("King/kingIdle.png")),4,1, 0.33f)); // #9
                theOpponent.setWalk(createAnimation(new Texture(Gdx.files.internal("King/kingWalk.png")),4,1, 0.33f)); // #9
                theOpponent.setSpecial(createAnimation(new Texture(Gdx.files.internal("King/kingKick.png")),3,1, 0.33f));
                theOpponent.setPunch(createAnimation(new Texture(Gdx.files.internal("King/kingPunch.png")),3,1, 0.33f));

                TextureRegion[] specialFrames = new TextureRegion[6];
                Texture specialSheet = new Texture("King/kingSpecial.png");
                specialFrames[0] = new TextureRegion(specialSheet,1,1,58,100);
                specialFrames[1] = new TextureRegion(specialSheet,65,1,54,100);
                specialFrames[2] = new TextureRegion(specialSheet,119,1,56,100);
                specialFrames[3] = new TextureRegion(specialSheet,175,1,58,100);
                specialFrames[4] = new TextureRegion(specialSheet,231,1,114,100);
                specialFrames[5] = new TextureRegion(specialSheet,342,1,58,100);

                theOpponent.setKick(new Animation<>(0.25f, specialFrames));
                theOpponent.setDead(createAnimation(new Texture(Gdx.files.internal("King/kingDead.png")),3,1, 0.33f));
                theOpponent.setWin(createAnimation(new Texture(Gdx.files.internal("King/kingWin.png")),3,1, 0.33f));
                theOpponent.setLoose(createAnimation(new Texture(Gdx.files.internal("King/kingLoose.png")),3,1, 0.33f));
                theOpponent.setDamage(createAnimation(new Texture(Gdx.files.internal("King/kingDamage.png")),3,1, 0.33f));
                theOpponent.setDefend(createAnimation(new Texture(Gdx.files.internal("King/kingDefend.png")),1,1, 0.33f));

                break;

            case "robert":
                theOpponent.setIdle(createanimation(new Texture(Gdx.files.internal("Robert/robertIdle.png")),6,1, 0.33f)); // #9

                Texture robertwalksheet = new Texture("Robert/robertWalk.png");

                TextureRegion[] robertWalkFrames = new TextureRegion[3];

                robertWalkFrames[0] = new TextureRegion(robertwalksheet,1,1,90,155);
                robertWalkFrames[1] = new TextureRegion(robertwalksheet,90,1,110,135);
                robertWalkFrames[2] = new TextureRegion(robertwalksheet,205,1,90,155);

                theOpponent.setWalk(new Animation<TextureRegion>(0.33f,robertWalkFrames)); // #9


                Texture robertkicksheet = new Texture("Robert/robertKick.png");

                TextureRegion[] robertKickFrames = new TextureRegion[3];

                robertKickFrames[0] = new TextureRegion(robertkicksheet,1,1,100,155);
                robertKickFrames[1] = new TextureRegion(robertkicksheet,110,1,110,135);
                robertKickFrames[2] = new TextureRegion(robertkicksheet,215,1,160,155);

                theOpponent.setKick(new Animation<TextureRegion>(0.33f,robertKickFrames));


                Texture punchsheet = new Texture("Robert/robertPunch.png");

                TextureRegion[] robertPunchFrames = new TextureRegion[2];


                robertPunchFrames[0] = new TextureRegion(punchsheet,1,1,100,155);
                robertPunchFrames[1] = new TextureRegion(punchsheet,105,1,140,155);

                theOpponent.setPunch(new Animation<TextureRegion>(0.33f,robertPunchFrames));
                theOpponent.setSpecial(createanimation(new Texture(Gdx.files.internal("Robert/robertSpecial.png")),1,1, 0.33f));
                theOpponent.setDamage(createanimation(new Texture(Gdx.files.internal("Robert/robertDamage.png")),3,1, 0.33f));
                theOpponent.setDead(createanimation(new Texture(Gdx.files.internal("Robert/robDead.png")),6,1, 0.33f));
                theOpponent.setWin(createanimation(new Texture(Gdx.files.internal("Robert/robertWin.png")),3,1, 0.33f));
                theOpponent.setLoose(createanimation(new Texture(Gdx.files.internal("Robert/robertLose.jpg")),4,1, 0.33f));
                theOpponent.setDefend(createanimation(new Texture(Gdx.files.internal("Robert/robertDefend.png")),1,1, 0.33f));

                break;

            case "ken":
                theOpponent.setIdle(createAnimation(new Texture(Gdx.files.internal("Ken/kenIdle.png")),4,1, 0.33f)); // #9
                theOpponent.setWalk(createAnimation(new Texture(Gdx.files.internal("Ken/kenWalk.png")),5,1, 0.33f)); // #9

                TextureRegion[] kenKickFrames = new TextureRegion[3];
                Texture kenKickSheet = new Texture("Ken/kenKick.png");
                kenKickFrames[0] = new TextureRegion(kenKickSheet,1,1,43,88);
                kenKickFrames[1] = new TextureRegion(kenKickSheet,45,1,45,88);
                kenKickFrames[2] = new TextureRegion(kenKickSheet,90,1,70,88);

                theOpponent.setKick(new Animation<>(0.33f, kenKickFrames));
                theOpponent.setPunch(createAnimation(new Texture(Gdx.files.internal("Ken/kenPunch.png")),2,1, 0.25f));
                TextureRegion[] kenSpecialFrames = new TextureRegion[3];
                Texture kenSpecialSheet = new Texture("Ken/kenSpecial.png");
                kenSpecialFrames[0] = new TextureRegion(kenSpecialSheet,1,1,60,88);
                kenSpecialFrames[1] = new TextureRegion(kenSpecialSheet,60,1,60,88);
                kenSpecialFrames[2] = new TextureRegion(kenSpecialSheet,130,1,105,88);
                theOpponent.setSpecial(new Animation<>(0.25f, kenSpecialFrames));
                theOpponent.setDamage(createAnimation(new Texture(Gdx.files.internal("Ken/kenDamage.png")),2,1, 0.33f));
                theOpponent.setDead(createAnimation(new Texture(Gdx.files.internal("Ken/kenDead.png")),2,1, 0.33f));
                theOpponent.setWin(createAnimation(new Texture(Gdx.files.internal("Ken/kenWin.png")),3,1, 0.33f));

                Texture walkSheet = new Texture("Ken/kenWalk.png");
                Texture looseSheet = new Texture("Ken/kenLoose.png");

                TextureRegion[] kenLooseFrames = new TextureRegion[3];

                kenLooseFrames[0] = new TextureRegion(walkSheet,1,1,40,88);
                kenLooseFrames[1] = new TextureRegion(walkSheet,40,1,40,88);
                kenLooseFrames[2] = new TextureRegion(looseSheet,2,1,40,88);
                theOpponent.setLoose(new Animation<>(0.33f, kenLooseFrames));
                theOpponent.setDefend(createAnimation(new Texture(Gdx.files.internal("Ken/kenDefend.jpg")),1,1, 0.33f));

                break;

            case "ryu":
                theOpponent.setIdle(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuIdle.png")),4,1, 0.33f)); // #9
                theOpponent.setWalk(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuWalk.png")),5,1, 0.33f)); // #9
                theOpponent.setKick(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuKick.png")),4,1, 1f));
                theOpponent.setPunch(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuPunch.png")),3,1, 1f));
                theOpponent.setSpecial(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuUlt.png")),3,1, 0.33f));
                theOpponent.setDamage(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuDamage.png")),3,1, 0.33f));
                theOpponent.setDead(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuDead.png")),2,1, 0.33f));
                theOpponent.setWin(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuWin.png")),3,1, 0.33f));
                Texture ryuwalkSheet = new Texture("Ryu/ryuIdle.png");
                Texture ryulooseSheet = new Texture("Ryu/ryuLoose.png");

                TextureRegion[] ryuLooseFrames = new TextureRegion[2];

                ryuLooseFrames[0] = new TextureRegion(ryuwalkSheet,1,1,45,88);
                ryuLooseFrames[1] = new TextureRegion(ryulooseSheet,70,1,50,88);
                theOpponent.setLoose(new Animation<>(0.33f, ryuLooseFrames));
                theOpponent.setDefend(createAnimation(new Texture(Gdx.files.internal("Ryu/ryuDefend.png")),1,1, 0.33f));

                break;
        }


    }

    public Animation<TextureRegion> createAnimation(Texture player, int width, int height, float time){


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
        return new Animation<>(time, playerFrames);
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
        playerselected = false;

    }
}
