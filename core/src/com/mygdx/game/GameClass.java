package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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
    public enum GameState { PLAYING, COMPLETE ,PAUSE};
    public static final float MOVEMENT_SPEED = 4.0f;
    GameState gameState = GameState.PLAYING;

    public enum State {Idle,Walking, Kicking, Punching, Special, Dead, Loose, Damage, Win;}

    State player_CurrentState;
    State opponent_CurrentState;


    private Music music2;
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

    //UI textures
    private Texture buttonSquareTextureForForward;
    private Texture buttonSquareTextureForBackward;
    private Texture buttonSquareTextureForKick;
    private Texture buttonSquareTextureForPunch;
    private Texture buttonSquareTextureForSuperPower;

    Vector2 playerDelta;
    Vector2 opponentDelta;

    //Player Coordinates
    int characterX;
    int characterY;


    //UI Buttons
    private Button moveLeftButton;
    private Button moveRightButton;
    private Button kickButton;
    private Button punchButton;
    private Button specialButton;


    private Texture comMenuBG;
    private Skin skin;


    Image menuBackground;
    TextButton btnRestartGame;
    Sound btnSound;
    TextButton btnHowToPlay;
    TextButton btnUnpause;
    TextButton btnPause;


    Label playerRoundWins;
    Label opponentRoundWins;
    Label roundTime;
    Label Countdown;
    Label fightlabel;

    private Stage pauseMenuStage;
    static long roundTim;
    static Timer timer;


    int playerWinCount=0;
    int opponentWinCount=0;

    private Stage stage;

    public GameClass(MyGdxGame game) {
        this.game = game;
    }

    public void create() {


        buttonSquareTextureForKick = new Texture("Kick.png");
        buttonSquareTextureForPunch = new Texture("Punch.png");
        buttonSquareTextureForSuperPower = new Texture("SuperPower.png");

        kickButton = new Button(20, 300, 100, 100, buttonSquareTextureForKick, buttonSquareTextureForKick);
        punchButton = new Button(130, 300, 100, 100, buttonSquareTextureForPunch, buttonSquareTextureForPunch);
        specialButton = new Button(240, 300, 100, 100, buttonSquareTextureForSuperPower, buttonSquareTextureForSuperPower);



        //***********************************************************************CompleteMenuStage**************************************************************************************
        comMenuBG = new Texture("Starting Assets/assets/finishedbg.png");
        skin = new Skin(Gdx.files.internal("Starting Assets/assets/uiskin.json"));
        btnRestartGame = new TextButton("Restart", skin, "default");
        btnHowToPlay = new TextButton("HowToPlay",skin,"default");
        btnUnpause = new TextButton("Unpause", skin, "default");
        btnSound = Gdx.audio.newSound(Gdx.files.internal("Starting Assets/assets/buttonsound.wav"));
        btnPause = new TextButton("||",skin,"default");

        menuBackground = new Image(comMenuBG);
        menuBackground.setSize(1800,900);
        menuBackground.setX(200);
        menuBackground.setY(200);
        menuBackground.setZIndex(2);
        menuBackground.setVisible(false);

        btnRestartGame.setWidth(600f);
        btnRestartGame.setHeight(100f);
        btnRestartGame.getLabel().setFontScale(5);
        btnRestartGame.setColor(Color.GOLD);
        btnRestartGame.setPosition(750, 800);
        btnRestartGame.setVisible(false);

        btnHowToPlay.setWidth(600f);
        btnHowToPlay.setHeight(100f);
        btnHowToPlay.getLabel().setFontScale(5);
        btnHowToPlay.setColor(Color.GOLD);
        btnHowToPlay.setPosition(750, 600);
        btnHowToPlay.setVisible(false);

        btnUnpause.setWidth(600f);
        btnUnpause.setHeight(100f);
        btnUnpause.getLabel().setFontScale(5);
        btnUnpause.setColor(Color.GOLD);
        btnUnpause.setPosition(750, 400);
        btnUnpause.setVisible(false);

        btnPause.setWidth(60f);
        btnPause.setHeight(60f);
        btnPause.getLabel().setFontScale(2);
        btnPause.setColor(Color.GOLD);
        btnPause.setPosition(2000, 1000);
        btnPause.setVisible(true);

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
        buttonSquareTextureForForward = new Texture("rightArrow.png");
        buttonSquareTextureForBackward = new Texture("leftArrow.png");
        playerRoundWins = new Label("Wins "+playerWinCount, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        opponentRoundWins = new Label("Wins "+opponentWinCount, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        roundTime = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Countdown = new Label("", new Label.LabelStyle(new BitmapFont(), Color.RED));
        fightlabel = new Label("FIGHT", new Label.LabelStyle(new BitmapFont(), Color.GOLD));

        playerRoundWins.setFontScale(3,2);
        playerRoundWins.setPosition(100,1000);
        playerRoundWins.setVisible(true);

        opponentRoundWins.setFontScale(3,2);
        opponentRoundWins.setPosition(1300,1000);
        opponentRoundWins.setVisible(true);

        roundTime.setFontScale(8,6);
        roundTime.setPosition(1050,950);
        roundTime.setVisible(false);

        Countdown.setFontScale(20,16);
        Countdown.setPosition(1000,800);
        Countdown.setVisible(false);

        fightlabel.setFontScale(15,8);
        fightlabel.setPosition(800,800);
        fightlabel.setVisible(false);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        //Buttons
        float buttonSize = h * 0.1f;
        moveLeftButton = new Button(80, 150, buttonSize, buttonSize, buttonSquareTextureForBackward, buttonSquareTextureForBackward);
        moveRightButton = new Button(90+buttonSize, 150, buttonSize, buttonSize, buttonSquareTextureForForward, buttonSquareTextureForForward);

        HealthBar  playerHealthBar = new HealthBar();
        playerHealthBar.setX(100);
        playerHealthBar.setY(950);
        playerHealthBar.setWidthInner(700);
        playerHealthBar.setHeightInner(40);
        playerHealthBar.setWidthOuter(710);
        playerHealthBar.setHeightOuter(50);

        HealthBar opponentHealthBar = new HealthBar();
        opponentHealthBar.setX(1300);
        opponentHealthBar.setY(950);
        opponentHealthBar.setWidthInner(700);
        opponentHealthBar.setHeightInner(40);
        opponentHealthBar.setWidthOuter(710);
        opponentHealthBar.setHeightOuter(50);




        batch = new SpriteBatch();                // #12
        playerDelta = new Vector2();
        opponentDelta = new Vector2();
        playerSprite = new Sprite(PlayerClass.getPlayers().getIdle().getKeyFrames()[0]);
        opponentSprite = new Sprite(OpponentClass.getOpponent().getIdle().getKeyFrames()[0]);
        playerSprite.setX((Gdx.graphics.getWidth()/2)-500);
        opponentSprite.setX((Gdx.graphics.getWidth()/2)+500);
        stateTime = 0.00f;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 490 , 160  );
        tiledMap = new TmxMapLoader().load("fightmap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);


        pauseMenuStage.addActor(menuBackground);
        pauseMenuStage.addActor(btnRestartGame);
        pauseMenuStage.addActor(btnUnpause);
        pauseMenuStage.addActor(btnHowToPlay);
        stage.addActor(playerHealthBar);
        stage.addActor(opponentHealthBar);
        stage.addActor(roundTime);
        stage.addActor(playerRoundWins);
        stage.addActor(opponentRoundWins);
        stage.addActor(Countdown);
        stage.addActor(fightlabel);
        pauseMenuStage.addActor(btnPause);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setInputProcessor(pauseMenuStage);
        newGame();

        player_CurrentState = State.Idle;
        opponent_CurrentState = State.Idle;

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
                kickButton.update(checkTouch,touchX,touchY);
                punchButton.update(checkTouch,touchX,touchY);
                specialButton.update(checkTouch,touchX,touchY);
                int moveX = 0;

                if( kickButton.isDown){
                    player_CurrentState = State.Kicking;
                    System.out.println(opponentSprite.getX()-playerSprite.getX());

                    if(opponentSprite.getX() - playerSprite.getX() <= 600) {
                        opponent_CurrentState = State.Damage;
                        OpponentClass.getOpponent().setHealth(-9);
                    }
                    Timer.schedule(new Timer.Task() { @Override public void run() {
                        player_CurrentState = State.Idle;
                        opponent_CurrentState = State.Idle;
                    } },0.20f);

                }
                else if(specialButton.isDown){
                    player_CurrentState = State.Special;
                    if(opponentSprite.getX() - playerSprite.getX() <= 600) {
                        System.out.println(playerSprite.getX()+"___"+opponentSprite.getX());
                        opponent_CurrentState = State.Damage;
                        OpponentClass.getOpponent().setHealth(-30);
                    }
                    Timer.schedule(new Timer.Task() { @Override public void run() {
                        player_CurrentState = State.Idle;
                        opponent_CurrentState = State.Idle;
                    } },0.25f);
                }
                else if(punchButton.isDown){
                    player_CurrentState = State.Punching;
                    if(opponentSprite.getX() - playerSprite.getX() <= 600) {
                        OpponentClass.getOpponent().setHealth(-7);
                        opponent_CurrentState = State.Damage;
                    }
                    Timer.schedule(new Timer.Task() { @Override public void run() {
                        opponent_CurrentState = State.Idle;
                        player_CurrentState = State.Idle;
                    } },0.25f);

                }
                else if (Gdx.input.isKeyPressed(Input.Keys.UP) || moveLeftButton.isDown ) {

                    player_CurrentState = State.Walking;
                    moveX -= 1;
                    playerDelta.x = moveX * MOVEMENT_SPEED;
                    playerSprite.translateX(playerDelta.x);

                }
                else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || moveRightButton.isDown ) {
                    //b2bodyplayer.applyLinearImpulse(new Vector2(4f,0), b2bodyplayer.getWorldCenter(),true);
                    moveX += 1;
                    playerDelta.x = moveX * MOVEMENT_SPEED;
                    player_CurrentState = State.Walking;
                    playerSprite.translateX(playerDelta.x);

                }

               // TODO Determine Character Movement Distance
               // playerDelta.x = moveX * MOVEMENT_SPEED * dt;
                //Movement update
                //playerSprite.translateX(playerDelta.x);
               // camera.position.x += MOVEMENT_SPEED*dt;

                //TODO Move only if the target cell is empty
//                PlayerClass.getPlayers().translate(playerDelta.x, playerDelta.y);
//                camera.translate(playerDelta);

                break;
            case COMPLETE:
            {

                break;
            }
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
        }
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
                    if(TimeUtils.timeSinceMillis(roundTim)/1000 < 91 && TimeUtils.timeSinceMillis(roundTim)/1000 >= 0 ){
                        roundTime.setText(String.valueOf(TimeUtils.timeSinceMillis(roundTim)/1000));
                    }
                }
            }
        },7);


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
        kickButton.draw(batch);
        punchButton.draw(batch);
        specialButton.draw(batch);
        pauseMenuStage.draw();
        batch.end();
        }
    private void newGame() {

        menuBackground.setVisible(false);
        btnRestartGame.setVisible(false);
        btnHowToPlay.setVisible(false);
        btnUnpause.setVisible(false);
        playerWinCount = 0;


        music2 = Gdx.audio.newMusic(Gdx.files.internal("round1.wav"));


        music2.setVolume(1.0f);
        music2.setLooping(false);

        music2.play();
        music2.setOnCompletionListener(new Music.OnCompletionListener()
        {
            @Override
            public void onCompletion(Music music) {
                music= Gdx.audio.newMusic(Gdx.files.internal("321fight.wav"));
                music.setVolume(1.0f);
                music.play();


            }

        });


        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
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
            }
        },6);

        playerSprite.setPosition(600,0);

        dt = 0.0f;
        gameState = GameState.PLAYING;
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

    @Override
    public void resize(int width, int height) {
// Camera is updated here to support changes in screen size
//        camera.viewportWidth = 640;
//        camera.viewportHeight = camera.viewportWidth * (float)height / (float)width;
//        camera.update();
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
