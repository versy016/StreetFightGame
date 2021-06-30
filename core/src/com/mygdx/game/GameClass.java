package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;


public class GameClass implements Screen {

    MyGdxGame game;
    public enum GameState { PLAYING, ROUND_FINISHED,COMPLETE ,PAUSE}
    public static final float MOVEMENT_SPEED = 4.0f;
    GameState gameState = GameState.PLAYING;

    public enum State {Idle,Walking, Kicking, Punching, Special, Dead, Loose, Damage, Win}

    State player_CurrentState;
    State opponent_CurrentState;


    private float stateTime;
    private Sprite playerSprite;
    private Sprite opponentSprite;

    float dt;               //game delata time variable
    private SpriteBatch batch;                   // Spritebatch for rendering

    TextureRegion Player_Frame;
    TextureRegion Opponent_Frame;

    TiledMap tiledMap;                  //tiled map
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer tiledMapRenderer; //tiled map renderer

    Vector2 playerDelta;
    Vector2 opponentDelta;

    //UI Buttons
    private Button moveLeftButton;
    private Button moveRightButton;
    private TextButton kickButton;
    private TextButton punchButton;
    private TextButton specialButton;

    Image menuBackground;
    Image winOnePlayer;
    Image winTwoPlayer;
    Image winOneOpponent;
    Image winTwoOpponent;
    Image winOnePlayergrey;
    Image winTwoPlayergrey;
    Image winOneOpponentgrey;
    Image winTwoOpponentgrey;

    TextButton btnRestartGame;
    Sound btnSound;
    TextButton btnHowToPlay;
    TextButton btnUnpause;
    TextButton btnPause;
    TextButton mainMenuButton;
    TextButton psmMenuButton;


    Label playerRoundWins;
    Label opponentRoundWins;
    Label roundTime;
    Label Countdown;
    Label fightlabel;
    Label roundLabel;

    private Stage pauseMenuStage;
    static long roundTim;
    static Timer timer;

    int playerWinCount=0;
    int opponentWinCount=0;

    private Stage stage;

    HealthBar playerHealthBar;
    HealthBar opponentHealthBar;
    int hits;
    int check=0;
    int thedamage=0;
    int Rounds =0;

    Texture winRadioBtn;
    Texture looseRadioBtn;

    Skin skin;

    double opponentHealth;
    public GameClass(MyGdxGame game) {
        this.game = game;
    }

    public void create() {


        //***********************************************************************CompleteMenuStage**************************************************************************************
        Texture comMenuBG = new Texture("Starting Assets/assets/finishedbg.png");
         winRadioBtn = new Texture("radiobutton.png");
         looseRadioBtn = new Texture("radiobuttongrey.png");

        skin = new Skin(Gdx.files.internal("Starting Assets/assets/uiskin.json"));
        btnRestartGame = new TextButton("Restart", skin, "default");
        btnHowToPlay = new TextButton("HowToPlay", skin,"default");
        btnUnpause = new TextButton("Unpause", skin, "default");
        kickButton = new TextButton("K", skin, "default");
        punchButton = new TextButton("P", skin, "default");
        specialButton = new TextButton("SP", skin, "default");
        psmMenuButton = new TextButton("Select Player", skin, "default");
        mainMenuButton = new TextButton("Main Menu", skin, "default");
        btnSound = Gdx.audio.newSound(Gdx.files.internal("Starting Assets/assets/buttonsound.wav"));
        btnPause = new TextButton("||", skin,"default");

        menuBackground = new Image(comMenuBG);
        menuBackground.setSize(1800,900);
        menuBackground.setX(200);
        menuBackground.setY(200);
        menuBackground.setZIndex(2);
        menuBackground.setVisible(false);

        winOnePlayer = makeImage(winRadioBtn,50,50,850,950);
        winTwoPlayer = makeImage(winRadioBtn,50,50,910,950);
        winOneOpponent = makeImage(winRadioBtn,50,50,1150,950);
        winTwoOpponent = makeImage(winRadioBtn,50,50,1210,950);

        winOnePlayergrey = makeImage(looseRadioBtn,50,50,850,950);
        winOnePlayergrey.setVisible(true);
        winTwoPlayergrey = makeImage(looseRadioBtn,50,50,910,950);
        winTwoPlayergrey.setVisible(true);

        winOneOpponentgrey = makeImage(looseRadioBtn,50,50,1150,950);
        winOneOpponentgrey.setVisible(true);
        winTwoOpponentgrey = makeImage(looseRadioBtn,50,50,1210,950);
        winTwoOpponentgrey.setVisible(true);

        btnRestartGame.setWidth(600f);
        btnRestartGame.setHeight(100f);
        btnRestartGame.getLabel().setFontScale(5);
        btnRestartGame.setColor(Color.GOLD);
        btnRestartGame.setPosition(750, 900);
        btnRestartGame.setVisible(false);

        btnHowToPlay.setWidth(600f);
        btnHowToPlay.setHeight(100f);
        btnHowToPlay.getLabel().setFontScale(5);
        btnHowToPlay.setColor(Color.GOLD);
        btnHowToPlay.setPosition(750, 750);
        btnHowToPlay.setVisible(false);

        btnUnpause.setWidth(600f);
        btnUnpause.setHeight(100f);
        btnUnpause.getLabel().setFontScale(5);
        btnUnpause.setColor(Color.GOLD);
        btnUnpause.setPosition(750, 300);
        btnUnpause.setVisible(false);

        btnPause.setWidth(60f);
        btnPause.setHeight(60f);
        btnPause.getLabel().setFontScale(2);
        btnPause.setColor(Color.BLACK);
        btnPause.setPosition(2000, 1000);
        btnPause.setVisible(true);

        psmMenuButton.setWidth(600f);
        psmMenuButton.setHeight(100f);
        psmMenuButton.getLabel().setFontScale(5);
        psmMenuButton.setColor(Color.GOLD);
        psmMenuButton.setPosition(750, 450);
        psmMenuButton.setVisible(false);

        mainMenuButton.setWidth(600f);
        mainMenuButton.setHeight(100f);
        mainMenuButton.getLabel().setFontScale(5);
        mainMenuButton.setColor(Color.GOLD);
        mainMenuButton.setPosition(750, 600);
        mainMenuButton.setVisible(false);


        punchButton.setWidth(80f);
        punchButton.setHeight(80f);
        punchButton.getLabel().setFontScale(3);
        punchButton.setColor(Color.BLACK);
        punchButton.setPosition(50, 300);
        punchButton.setVisible(false);

        kickButton.setWidth(80f);
        kickButton.setHeight(80f);
        kickButton.getLabel().setFontScale(3);
        kickButton.setColor(Color.BLACK);
        kickButton.setPosition(200, 300);
        kickButton.setVisible(false);

        specialButton.setWidth(80f);
        specialButton.setHeight(80f);
        specialButton.getLabel().setFontScale(3);
        specialButton.setColor(Color.BLACK);
        specialButton.setPosition(100, 400);
        specialButton.setVisible(false);

        btnPause.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                roundTime.setVisible(false);
                btnSound.play(1.0f);
                menuBackground.setVisible(true);
                btnRestartGame.setVisible(true);
                btnHowToPlay.setVisible(true);
                btnUnpause.setVisible(true);
                gameState = GameState.PAUSE;
            }
        });

        btnRestartGame.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                btnSound.play(1.0f);
                newGame();
            }
        });

        btnUnpause.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                btnSound.play(1.0f);
                roundTime.setVisible(true);
                menuBackground.setVisible(false);
                btnRestartGame.setVisible(false);
                btnHowToPlay.setVisible(false);
                btnUnpause.setVisible(false);
                gameState = GameState.PLAYING;
            }
        });

        btnHowToPlay.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                btnSound.play(1.0f);
                //TODO Show Game Help Menu
            }
        });

        //***********************************************************************CompleteMenuStage**************************************************************************************


        timer = new Timer();
        stage = new Stage();
        pauseMenuStage = new Stage();
        //UI textures
        Texture buttonSquareTextureForForward = new Texture("rightArrow.png");
        Texture buttonSquareTextureForBackward = new Texture("leftArrow.png");
        playerRoundWins = new Label("Wins "+playerWinCount, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        opponentRoundWins = new Label("Wins "+opponentWinCount, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        roundTime = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Countdown = new Label("", new Label.LabelStyle(new BitmapFont(), Color.RED));
        fightlabel = new Label("FIGHT", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        roundLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.GOLD));

        playerRoundWins.setFontScale(3,2);
        playerRoundWins.setPosition(100,1000);
        playerRoundWins.setVisible(true);

        roundLabel.setFontScale(12,6);
        roundLabel.setPosition((float)Gdx.graphics.getWidth()/3,(float)Gdx.graphics.getHeight()/1.5f);
        roundLabel.setVisible(true);

        opponentRoundWins.setFontScale(3,2);
        opponentRoundWins.setPosition(1300,1000);
        opponentRoundWins.setVisible(true);

        roundTime.setFontScale(8,6);
        roundTime.setPosition(1050,950);

        Countdown.setFontScale(20,16);
        Countdown.setPosition(975,800);

        fightlabel.setFontScale(12,6);
        fightlabel.setPosition((float)Gdx.graphics.getWidth()/2,(float)Gdx.graphics.getHeight()/1.5f);


        float h = Gdx.graphics.getHeight();

        //Buttons
        float buttonSize = h * 0.1f;
        moveLeftButton = new Button(80, 150, buttonSize, buttonSize, buttonSquareTextureForBackward, buttonSquareTextureForBackward);
        moveRightButton = new Button(90+buttonSize, 150, buttonSize, buttonSize, buttonSquareTextureForForward, buttonSquareTextureForForward);

        playerHealthBar = new HealthBar();
        playerHealthBar.setX(100);
        playerHealthBar.setY(950);
        playerHealthBar.setWidthInner(700);
        playerHealthBar.setHeightInner(40);
        playerHealthBar.setWidthOuter(710);
        playerHealthBar.setHeightOuter(50);

        opponentHealthBar = new HealthBar();
        opponentHealthBar.setX(1300);
        opponentHealthBar.setY(950);
        opponentHealthBar.setWidthInner(700);
        opponentHealthBar.setHeightInner(40);
        opponentHealthBar.setWidthOuter(710);
        opponentHealthBar.setHeightOuter(50);

        opponentHealth =700;
        hits =0;
        batch = new SpriteBatch();                // #12
        playerDelta = new Vector2();
        opponentDelta = new Vector2();
        playerSprite = new Sprite(PlayerClass.getPlayers().getIdle().getKeyFrames()[0]);
        opponentSprite = new Sprite(OpponentClass.getOpponent().getIdle().getKeyFrames()[0]);
        playerSprite.setX((Gdx.graphics.getWidth()/2.0f)-500);
        opponentSprite.setX((Gdx.graphics.getWidth()/2.0f)+500);
        stateTime = 0.00f;




        stage.addActor(playerHealthBar);
        stage.addActor(opponentHealthBar);
        stage.addActor(roundTime);
        stage.addActor(playerRoundWins);
        stage.addActor(opponentRoundWins);
        stage.addActor(Countdown);
        stage.addActor(fightlabel);
        stage.addActor(specialButton);
        stage.addActor(punchButton);
        stage.addActor(kickButton);
        stage.addActor(roundLabel);
        stage.addActor(winOnePlayer);
        stage.addActor(winTwoPlayer);
        stage.addActor(winOneOpponent);
        stage.addActor(winTwoOpponent);
        stage.addActor(btnPause);
        stage.addActor(menuBackground);
        stage.addActor(btnRestartGame);
        stage.addActor(btnUnpause);
        stage.addActor(btnHowToPlay);
        stage.addActor(mainMenuButton);
        stage.addActor(psmMenuButton);
        stage.addActor(winOnePlayergrey);
        stage.addActor(winTwoPlayergrey);
        stage.addActor(winTwoOpponentgrey);
        stage.addActor(winOneOpponentgrey);



        Gdx.input.setInputProcessor(pauseMenuStage);
        Gdx.input.setInputProcessor(stage);

        newGame();


    }
    @Override
    public void show() {

        Gdx.app.log("Gamescreen: ","menuScreen show called");
        create();
    }


    private void updateplayer() {


        dt = Gdx.graphics.getDeltaTime();
        //Touch Input Info
        boolean checkTouch = Gdx.input.isTouched();
        int touchX = Gdx.input.getX();
        int touchY = Gdx.input.getY();
        //Update Game State based on input
        switch (gameState) {

            case PLAYING:

                //Poll user for input
                moveLeftButton.update(checkTouch, touchX, touchY);
                moveRightButton.update(checkTouch, touchX, touchY);
                int moveX = 0;


 //               if(hits >= 5)
//                    specialButton.setVisible(true);

                kickButton.addListener(new ClickListener()
                {
                    @Override
                    public void clicked (InputEvent event, float x, float y)
                    {
                        kickButton.setVisible(false);
                        player_CurrentState = State.Kicking;
                        hits++;
                        if(opponentSprite.getX() - playerSprite.getX() <= 600) {
                            opponent_CurrentState = State.Damage;

                        Timer.schedule(new Timer.Task() { @Override public void run() {
                            player_CurrentState = State.Idle;
                            opponent_CurrentState = State.Idle;
                            kickButton.setVisible(true);
                            check=1;
                            hits++;
                            thedamage = 10;
                        } },0.20f);}
                        else {
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    kickButton.setVisible(true);
                                    player_CurrentState = State.Idle;
                                }
                            }, 0.20f);
                        }

                    }});
                specialButton.addListener(new ClickListener()
                {
                    @Override
                    public void clicked (InputEvent event, float x, float y)
                    {
                        specialButton.setVisible(false);
                        player_CurrentState = State.Special;
                        if(opponentSprite.getX() - playerSprite.getX() <= 600) {
                            opponent_CurrentState = State.Damage;
                            Timer.schedule(new Timer.Task() { @Override public void run() {
                                player_CurrentState = State.Idle;
                                opponent_CurrentState = State.Idle;
                                specialButton.setVisible(true);
                                check=1;
                                thedamage = 40;
                        } },0.20f);}
                        else {
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    specialButton.setVisible(true);
                                    player_CurrentState = State.Idle;
                                }
                            }, 0.20f);
                        }
                }});

                punchButton.addListener(new ClickListener()
                {
                    @Override
                    public void clicked (InputEvent event, float x, float y)
                    {
                        punchButton.setVisible(false);
                        player_CurrentState = State.Punching;

                        if(opponentSprite.getX() - playerSprite.getX() <= 600) {
                            opponent_CurrentState = State.Damage;
                            Timer.schedule(new Timer.Task() { @Override public void run() {
                                opponent_CurrentState = State.Idle;
                                player_CurrentState = State.Idle;
                                punchButton.setVisible(true);
                                check=1;
                                hits++;
                                thedamage = 8;
                            } },0.20f);
                        }
                        else {
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    punchButton.setVisible(true);
                                    player_CurrentState = State.Idle;
                                }
                            }, 0.20f);
                        }
                    }});
               if (Gdx.input.isKeyPressed(Input.Keys.UP) || moveLeftButton.isDown ) {

                    player_CurrentState = State.Walking;
                    moveX -= 1;
                    playerDelta.x = moveX * MOVEMENT_SPEED;
                    playerSprite.translateX(playerDelta.x);

                }
                else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || moveRightButton.isDown && opponentSprite.getX()-playerSprite.getX()>550) {
                    moveX += 1;
                    playerDelta.x = moveX * MOVEMENT_SPEED;
                    player_CurrentState = State.Walking;
                    playerSprite.translateX(playerDelta.x);

                }
                if(check==1){
                    opponentHealthBar.setWidthInner((int) health(thedamage));
                    }
                if(playerWinCount==1){
                    winOnePlayergrey.setVisible(false);
                    winOnePlayer.setVisible(true);
                    }
                if(playerWinCount==2){
                    winOnePlayergrey.setVisible(false);
                    winOnePlayer.setVisible(true);
                    winTwoPlayergrey.setVisible(false);
                    winTwoPlayer.setVisible(true);
                }
                if(opponentWinCount==1){
                    winOneOpponentgrey.setVisible(false);
                    winOneOpponent.setVisible(true);
                }
                if(opponentWinCount==1){
                    winOneOpponentgrey.setVisible(false);
                    winOneOpponent.setVisible(true);
                    winTwoOpponentgrey.setVisible(false);
                    winTwoOpponent.setVisible(true);
                }

                if(playerHealthBar.getWidthInner() <= 0){

                    playerHealthBar.setWidthInner(0);
                    player_CurrentState = State.Dead;
                    opponent_CurrentState = State.Win;
                    opponentWinCount++;
                    Rounds++;
                    game.setScreen(MyGdxGame.gclass);
                }
                else if(opponentHealthBar.getWidthInner() <= 0){

                    opponentHealthBar.setWidthInner(0);
                    opponent_CurrentState = State.Dead;
                    player_CurrentState = State.Win;
                    playerWinCount++;
                    Rounds++;
                    game.setScreen(MyGdxGame.gclass);

                }
                break;

            case ROUND_FINISHED:
                    if(playerHealthBar.getWidthInner()> opponentHealthBar.getWidthInner()){
                        opponent_CurrentState = State.Loose;
                        player_CurrentState = State.Idle;
                        playerWinCount++;
                        Rounds++;
                        game.setScreen(MyGdxGame.gclass);
                    }
                    else if(playerHealthBar.getWidthInner()< opponentHealthBar.getWidthInner()){
                        opponent_CurrentState = State.Win;
                        player_CurrentState = State.Loose;
                        Rounds++;
                        opponentWinCount++;
                        game.setScreen(MyGdxGame.gclass);

                    }

                break;
            case COMPLETE:
                    roundTime.setVisible(false);
                    btnSound.play(1.0f);
                    menuBackground.setVisible(true);
                    btnRestartGame.setVisible(true);
                    psmMenuButton.setVisible(true);
                    mainMenuButton.setVisible(true);
                 break;

            case PAUSE:
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + gameState);
        }

        camera.update();
        tiledMapRenderer.setView(camera);
    }

    private void updateOpponent() {
        opponentDelta.x = 2;

        switch (gameState) {
            case PLAYING:
                if(playerSprite.getX()+700 <  opponentSprite.getX()){
                    opponent_CurrentState = State.Walking;
                    opponentSprite.translateX(-opponentDelta.x);

                }
                else if(opponentSprite.getX()- playerSprite.getX() <= 300 && !player_CurrentState.equals(State.Kicking) &&!player_CurrentState.equals(State.Punching) && !player_CurrentState.equals(State.Special)){
                    opponent_CurrentState = State.Idle;
              }
            case PAUSE:
                break;
        }
    }

    private double health(int damage){
        opponentHealth = opponentHealth-(0.7*damage);
        check=0;
        return opponentHealth;

    }
        @Override
    public void render(float delta) {

        updateplayer();
        updateOpponent();
        dt = Gdx.graphics.getDeltaTime();

        stateTime += Gdx.graphics.getDeltaTime();

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {

                //Round Timer
                if(gameState != GameState.PAUSE){
                    if(TimeUtils.timeSinceMillis(roundTim)/1000 < 11 && TimeUtils.timeSinceMillis(roundTim)/1000 >= 0 ){
                        roundTime.setText(String.valueOf(TimeUtils.timeSinceMillis(roundTim)/1000));
                    }
                    else if(TimeUtils.timeSinceMillis(roundTim)/1000 > 11) {

                        //roundTim = TimeUtils.millis();
                        gameState = GameState.ROUND_FINISHED;
                    }
                    else if(Rounds == 3){
                        gameState = GameState.COMPLETE;
                    }

                }
            }
        },5);


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Player_Frame =  get_Player_current_state();
        Opponent_Frame = get_Opponent_current_state();

        tiledMapRenderer.render();
        batch.begin();
        stage.draw();
        batch.draw(Player_Frame,playerSprite.getX(),50,350,500);
        batch.draw(Opponent_Frame,opponentSprite.getX(),50,-350,500);
        moveLeftButton.draw(batch);
        moveRightButton.draw(batch);
        pauseMenuStage.draw();
        batch.end();
        }
    private void newGame() {

        kickButton.setVisible(false);
        punchButton.setVisible(false);
        specialButton.setVisible(false);
        gameState = GameState.PLAYING;
        roundTime.setVisible(false);
        Countdown.setVisible(false);
        fightlabel.setVisible(false);
        fightlabel.setText("FIGHT");
        opponentHealthBar.setWidthInner(700);
        playerHealthBar.setWidthInner(700);

        player_CurrentState = State.Idle;
        opponent_CurrentState = State.Idle;

        menuBackground.setVisible(false);
        btnRestartGame.setVisible(false);
        btnHowToPlay.setVisible(false);
        btnUnpause.setVisible(false);

        if(Rounds == 0)
        {
            roundLabel.setText("ROUND 1");
            tiledMap = new TmxMapLoader().load("chinatown.tmx");
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

            camera = new OrthographicCamera();
            camera.setToOrtho(false, 730 , 330  );
        }
        if (Rounds == 1){
            tiledMap = new TmxMapLoader().load("fightmap.tmx");
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
            roundLabel.setText("ROUND 2");

            camera = new OrthographicCamera();
            camera.setToOrtho(false, 490 , 160  );
        }
        else if(Rounds == 2){
            tiledMap = new TmxMapLoader().load("downtown.tmx");
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

            roundLabel.setText("ROUND 3");

            camera = new OrthographicCamera();
            camera.setToOrtho(false, 720 , 330  );
        }


//        Music music2 = Gdx.audio.newMusic(Gdx.files.internal("round1.wav"));
//
//
//        music2.setVolume(1.0f);
//        music2.setLooping(false);
//
//        music2.play();
//        music2.setOnCompletionListener(new Music.OnCompletionListener()
//        {
//            @Override
//            public void onCompletion(Music music) {
//                music= Gdx.audio.newMusic(Gdx.files.internal("321fight.wav"));
//                music.setVolume(1.0f);
//                music.play();
//            }
//        });

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                roundLabel.setVisible(false);
                Countdown.setText(3);
                Countdown.setVisible(true);

            }
        },2);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Countdown.setText(2);
                Countdown.setVisible(true);

            }
        },3);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Countdown.setText(1);
                Countdown.setVisible(true);

            }
        },4);

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Countdown.setVisible(false);
                fightlabel.setVisible(true);

            }
        },5);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                fightlabel.setVisible(false);
                roundTim = TimeUtils.millis();
                roundTime.setVisible(true);
                gameState = GameState.PLAYING;
                kickButton.setVisible(true);
                punchButton.setVisible(true);
                specialButton.setVisible(true);
            }
        },6);

        playerSprite.setPosition(600,0);

        dt = 0.0f;
    }

    public TextureRegion get_Player_current_state(){

        if(player_CurrentState == State.Idle){
            Player_Frame = PlayerClass.getPlayers().getIdle().getKeyFrame(stateTime, true);
        }
        if(player_CurrentState == State.Walking){
            Player_Frame = PlayerClass.getPlayers().getWalk().getKeyFrame(stateTime,true);
        }
        if(player_CurrentState == State.Punching){
            Player_Frame =  PlayerClass.getPlayers().getPunch().getKeyFrame(0.33f, false);
        }
        if(player_CurrentState == State.Kicking){
            Player_Frame = PlayerClass.getPlayers().getKick().getKeyFrame(stateTime, false);

        }
        if(player_CurrentState == State.Special){
            Player_Frame =  PlayerClass.getPlayers().getSpecial().getKeyFrame(stateTime, false);
        }
        if(player_CurrentState == State.Win){
            Player_Frame = PlayerClass.getPlayers().getWin().getKeyFrame(stateTime, false);
        }
        if(player_CurrentState == State.Dead){
            Player_Frame = PlayerClass.getPlayers().getDead().getKeyFrame(stateTime, false);
        }
        if(player_CurrentState == State.Loose){
            Player_Frame = PlayerClass.getPlayers().getLoose().getKeyFrame(stateTime, false);
        }
        if(player_CurrentState == State.Damage){
            Player_Frame = PlayerClass.getPlayers().getDamage().getKeyFrame(stateTime, false);
        }

        return Player_Frame;
    }

    public TextureRegion get_Opponent_current_state(){

        if(opponent_CurrentState == State.Idle){
            Opponent_Frame = OpponentClass.getOpponent().getIdle().getKeyFrame(stateTime, true);
        }
        if(opponent_CurrentState == State.Walking){
           Opponent_Frame = OpponentClass.getOpponent().getWalk().getKeyFrame(stateTime, true);
        }
        if(opponent_CurrentState == State.Punching){
            Opponent_Frame =  OpponentClass.getOpponent().getPunch().getKeyFrame(stateTime, false);
        }
        if(opponent_CurrentState == State.Kicking){
            Opponent_Frame = OpponentClass.getOpponent().getKick().getKeyFrame(stateTime, false);
        }
        if(opponent_CurrentState == State.Special){
            Opponent_Frame =  OpponentClass.getOpponent().getSpecial().getKeyFrame(stateTime, false);
        }
        if(opponent_CurrentState == State.Win){
            Opponent_Frame = OpponentClass.getOpponent().getWin().getKeyFrame(stateTime, false);
        }
        if(opponent_CurrentState == State.Dead){
            Opponent_Frame = OpponentClass.getOpponent().getDead().getKeyFrame(stateTime, false);
        }
        if(opponent_CurrentState == State.Loose){
            Opponent_Frame = OpponentClass.getOpponent().getLoose().getKeyFrame(stateTime, false);
        }
        if(opponent_CurrentState == State.Damage){
            Opponent_Frame = OpponentClass.getOpponent().getDamage().getKeyFrame(stateTime, false);
        }
        return Opponent_Frame;

    }

    public Image makeImage(Texture txt, int width, int height, int posx, int posy){
        Image img = new Image(txt);
        img.setSize(width,height);
        img.setX(posx);
        img.setY(posy);
        img.setZIndex(2);
        img.setVisible(false);

        return img;
    }
    @Override
    public void resize(int width, int height) {
//
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
        skin.dispose();
    }
}
