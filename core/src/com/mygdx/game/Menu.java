package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

public class Menu implements Screen
{
    //MyGdxGame Instance
    MyGdxGame game;
    //Texture for background of the MainMenuUI
    private Texture background;
    //Texture for background of the Menu
    private Texture menuBG;
    //Texture for Credits Background
    private Texture credits;
    //Instance of SpriteBatch
    private SpriteBatch batch;
    //Stage instance for main stage
    private Stage stage;
    //Instance of Skin Class
    private Skin skin;
    //Instance of Music Class for MainMenu Background Music
    private Music music;
    //Image for Background
    Image imgBackground;
    //Image for menuBackGround
    Image menuBackground;
    //Image for credits background
    Image creditsBg;
    //TextButton for Play
    TextButton btnPlay;
    //Sound for button click
    Sound btnSound;
    //Text Button for How to play
    TextButton btnHowToPlay;
    //Text Button for Credits
    TextButton btnCredits;
    //Text Button for Exit
    TextButton btnExit;
    //Label for Messages in Messagebox
    Label message;
    //Text Button for MessageBox OK button
    TextButton btnMessageBox;
    //Image for MessageBox Background
    Image messageBoxBackGround;
    //Stage for messageBox
    Stage messageBox;
    //Timer for schedule task
    static Timer timer;

    //Constructor for initialize current game object
    public Menu(MyGdxGame game)
    {
        this.game = game;
    }
    //Create method for initialize everything
    public void create()
    {
        timer = new Timer();
        batch = new SpriteBatch();
        stage = new Stage();
        messageBox = new Stage();
        credits = new Texture("Starting Assets/assets/Credits.png");
        menuBG = new Texture("Starting Assets/assets/finishedbg.png");
        background = new Texture("Starting Assets/assets/menuBackground.png");
        skin = new Skin(Gdx.files.internal("Starting Assets/assets/uiskin.json"));
        btnPlay = new TextButton("Play", skin, "default");
        btnHowToPlay = new TextButton("How To Play",skin,"default");
        btnCredits = new TextButton("Credits",skin,"default");
        btnExit = new TextButton("Exit", skin, "default");
        btnSound = Gdx.audio.newSound(Gdx.files.internal("Starting Assets/assets/buttonsound.wav"));

        //***********************************************************************MessageBoxForHowToPlay*********************************************************************************************
        message = new Label("Touch S :- Special Power Attack\nTouch P :- Punch\nTouch K :- Kick\nTouch D :- Defence\nTouch --> :- Move Forward.\nTouch <-- : Move Backward." ,new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        btnMessageBox = new TextButton("OK", skin, "default");
        messageBoxBackGround = new Image(menuBG);
        messageBoxBackGround.setSize(1200,600);
        messageBoxBackGround.setX(475);
        messageBoxBackGround.setY(300);
        messageBoxBackGround.setZIndex(2);
        messageBoxBackGround.setVisible(false);

        message.setFontScale(2,2);
        message.setPosition(875,550);
        message.setVisible(false);

        styleButton(btnMessageBox);
        btnMessageBox.setPosition(900, 400);
        btnMessageBox.setVisible(false);

        btnMessageBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                messageBoxBackGround.setVisible(false);
                message.setVisible(false);
                btnMessageBox.setVisible(false);
                Gdx.input.setInputProcessor(stage);
            }
        });
        //***********************************************************************MessageBox*********************************************************************************************

        imgBackground = new Image(background);
        imgBackground.setSize(2300,1100);
        imgBackground.setZIndex(1);


        menuBackground = new Image(menuBG);
        menuBackground.setSize(1800,900);
        menuBackground.setX(200);
        menuBackground.setY(200);
        menuBackground.setZIndex(2);

        creditsBg = new Image(credits);
        creditsBg.setSize(1800,900);
        creditsBg.setX(200);
        creditsBg.setY(100);
        creditsBg.setZIndex(2);
        creditsBg.setVisible(false);

        //music
        music= Gdx.audio.newMusic(Gdx.files.internal("Starting Music.wav"));

        music.setVolume(1.0f);
        music.setLooping(true);
        music.play();


        btnPlay.setWidth(600f);
        btnPlay.setHeight(100f);
        btnPlay.getLabel().setFontScale(5);
        btnPlay.setColor(Color.GOLD);
        btnPlay.setPosition(750, 800);

        btnHowToPlay.setWidth(600f);
        btnHowToPlay.setHeight(100f);
        btnHowToPlay.getLabel().setFontScale(5);
        btnHowToPlay.setColor(Color.GOLD);
        btnHowToPlay.setPosition(750, 650);

        btnCredits.setWidth(600f);
        btnCredits.setHeight(100f);
        btnCredits.getLabel().setFontScale(5);
        btnCredits.setColor(Color.GOLD);
        btnCredits.setPosition(750, 500);


        btnExit.setWidth(600f);
        btnExit.setHeight(100f);
        btnExit.getLabel().setFontScale(5);
        btnExit.setColor(Color.GOLD);
        btnExit.setPosition(750, 350);

        //Play button Click Event
        btnPlay.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                btnSound.play(1.0f);
                game.setScreen(MyGdxGame.playerSelectMenu);
                music.stop();

            }
        });

        //Exit button Click Event
        btnExit.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                Gdx.app.exit();
                btnSound.play(1.0f);

            }
        });

        //Credits button Click Event
        btnCredits.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                btnSound.play(1.0f);
                //Hides current menu from the stage
                btnPlay.setVisible(false);
                btnCredits.setVisible(false);
                btnExit.setVisible(false);
                btnHowToPlay.setVisible(false);
                menuBackground.setVisible(false);
                creditsBg.setVisible(true);
                //Delay 10 seconds and go back to previous menu
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        //Shows current menu from the stage
                        creditsBg.setVisible(false);
                        btnPlay.setVisible(true);
                        btnCredits.setVisible(true);
                        btnExit.setVisible(true);
                        btnHowToPlay.setVisible(true);
                        menuBackground.setVisible(true);
                    }
                },10);


            }
        });

        //HowToPlay Button Click Event
        btnHowToPlay.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                Gdx.input.setInputProcessor(messageBox);
                btnSound.play(1.0f);
                messageBoxBackGround.setVisible(true);
                message.setVisible(true);
                btnMessageBox.setVisible(true);

            }
        });

        //Add all actors to main stage
        stage.addActor(imgBackground);
        stage.addActor(menuBackground);
        stage.addActor(creditsBg);
        stage.addActor(btnPlay);
        stage.addActor(btnExit);
        stage.addActor(btnHowToPlay);
        stage.addActor(btnCredits);

        //Add messageBox Actors to MessageBox Stage
        messageBox.addActor(messageBoxBackGround);
        messageBox.addActor(btnMessageBox);
        messageBox.addActor(message);

        Gdx.input.setInputProcessor(stage);
    }

    //Method to style button identically
    public void styleButton(TextButton btn){
        btn.setWidth(300f);
        btn.setHeight(60f);
        btn.getLabel().setFontScale(3);
        btn.setColor(Color.GOLD);
    }
    @Override
    public void show()
    {
        Gdx.app.log("MenuScreen: ","MenuScreen Show Called");
        create();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        stage.draw();
        messageBox.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        background.dispose();
        skin.dispose();
        menuBG.dispose();
        btnSound.dispose();
        music.dispose();
    }
}
