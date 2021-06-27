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
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

public class GameClass implements Screen {

    MyGdxGame game;
    public enum GameState { PLAYING, COMPLETE ,PAUSE};
    public static final float MOVEMENT_SPEED = 4.0f;
    GameState gameState = GameState.PLAYING;

    public enum State {Idle,Walking, Kicking, Punching, Special, dead, Loose, Win;}

    State player_CurrentState;
    State OpponentcurrentState;

    private Music music2;
    private float stateTime;
    private Sprite playerSprite;
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

    Vector2 playerDelta;


    //UI Buttons
    private Button moveBackwardButton;
    private Button moveForwardButton;

    private Texture comMenuBG;
    private Skin skin;
    private Skin glassyForKick;
    private Skin glassyForPunch;
    private Skin glassyForSuperPower;


    Image menuBackground;
    TextButton btnRestartGame;
    Sound btnSound;
    TextButton btnHowToPlay;
    TextButton btnUnpause;
    TextButton btnPause;

    ImageButton btnKick;
    ImageButton btnPunch;
    ImageButton btnSuperPower;

    Label playerRoundWins;
    Label opponentRoundWins;
    Label roundTime;
    private Stage pauseMenuStage;
    static long roundTim;
    static Timer timer;

    boolean checkFirstTimeRoundTime;
    //Variable for save currentRoundTime to build pause functionality
    long currentRoundTime = 0;

    int playerWinCount=0;
    int opponentWinCount=0;

    private Body b2bodyplayer;
    private BodyDef playerDef;
    private World world;
    private PolygonShape playershape;
    private Box2DDebugRenderer debugRenderer;

    private Stage stage;

    public GameClass(MyGdxGame game) {
        this.game = game;
    }

    public void create() {


        glassyForKick = new Skin(Gdx.files.internal("Starting Assets/assets/glassy/skin/glassy-ui.json"));
        glassyForPunch = new Skin(Gdx.files.internal("Starting Assets/assets/glassy/skin/glassy-ui.json"));
        glassyForSuperPower = new Skin(Gdx.files.internal("Starting Assets/assets/glassy/skin/glassy-ui.json"));

        checkFirstTimeRoundTime = true;

        btnKick = new ImageButton(glassyForKick);
        btnKick.setSize(100,100);
        btnKick.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Kick.png"))));
        btnKick.setPosition(20,300);



        btnPunch = new ImageButton(glassyForPunch);
        btnPunch.setSize(100,100);
        btnPunch.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Punch.png"))));
        btnPunch.setPosition(130,300);


        btnSuperPower = new ImageButton(glassyForSuperPower);
        btnSuperPower.setSize(100,100);
        btnSuperPower.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("SuperPower.png"))));
        btnSuperPower.setPosition(240,300);






        //***********************************************************************CompleteMenuStage**************************************************************************************
        comMenuBG = new Texture("Starting Assets/assets/finishedbg.png");
        skin = new Skin(Gdx.files.internal("Starting Assets/assets/uiskin.json"));
        btnRestartGame = new TextButton("Restart", skin, "default");
        btnHowToPlay = new TextButton("HowToPlay",skin,"default");
        btnUnpause = new TextButton("Unpause", skin, "default");
        btnSound = Gdx.audio.newSound(Gdx.files.internal("Starting Assets/assets/buttonsound.wav"));
        btnPause = new TextButton("Pause",skin,"default");

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

        btnPause.setWidth(100f);
        btnPause.setHeight(100f);
        btnPause.getLabel().setFontScale(2);
        btnPause.setColor(Color.GOLD);
        btnPause.setPosition(2100, 950);
        btnPause.setVisible(true);

        btnPause.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                currentRoundTime = TimeUtils.millis();
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
                menuBackground.setVisible(false);
                btnRestartGame.setVisible(false);
                btnHowToPlay.setVisible(false);
                btnUnpause.setVisible(false);
                long pauseTime = roundTim - currentRoundTime;
                roundTim = roundTim-pauseTime;
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

        playerRoundWins.setFontScale(3,2);
        playerRoundWins.setPosition(100,1000);
        playerRoundWins.setVisible(true);

        opponentRoundWins.setFontScale(3,2);
        opponentRoundWins.setPosition(1300,1000);
        opponentRoundWins.setVisible(true);

        roundTime.setFontScale(8,6);
        roundTime.setPosition(1050,950);
        roundTime.setVisible(true);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        //Buttons
        float buttonSize = h * 0.1f;
        moveBackwardButton = new Button(80, 150, buttonSize, buttonSize, buttonSquareTextureForBackward, buttonSquareTextureForBackward);
        moveForwardButton = new Button(90+buttonSize, 150, buttonSize, buttonSize, buttonSquareTextureForForward, buttonSquareTextureForForward);


        world = new World(new Vector2(0,10),true);
        debugRenderer = new Box2DDebugRenderer();

        playershape = new PolygonShape();
        playershape.setAsBox(15,25);

        playerDef = new BodyDef();
        playerDef.position.set(new Vector2(165,35));
        playerDef.type = BodyDef.BodyType.DynamicBody;

        b2bodyplayer = world.createBody(playerDef);
        b2bodyplayer.createFixture(playershape, 0.5f);



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




        batch = new SpriteBatch();                // #12
        playerDelta = new Vector2();
        playerSprite = new Sprite(PlayerClass.getPlayers().getIdle().getKeyFrames()[0]);
        stateTime = 0.33f;

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
        stage.addActor(btnKick);
        stage.addActor(btnPunch);
        stage.addActor(btnSuperPower);
//        stage.addActor(btnBackward);
//        stage.addActor(btnForward);
        pauseMenuStage.addActor(btnPause);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setInputProcessor(pauseMenuStage);
        newGame();

        player_CurrentState = State.Idle;

    }
    @Override
    public void show() {
        Gdx.app.log("Gamescreen: ","menuScreen show called");
        create();
    }


    private void update() {


        dt = Gdx.graphics.getDeltaTime();
        //Touch Input Info
        boolean checkTouch = Gdx.input.isTouched();
        int touchX = Gdx.input.getX();
        int touchY = Gdx.input.getY();

        //Update Game State based on input
        switch (gameState) {


            case PLAYING:

                //Poll user for input
                moveBackwardButton.update(checkTouch, touchX, touchY);
                moveForwardButton.update(checkTouch, touchX, touchY);

                int moveX = 0;
                int moveY = 0;
                if (Gdx.input.isKeyPressed(Input.Keys.UP) || moveBackwardButton.isDown) {

                    moveBackwardButton.isDown = true;
                    player_CurrentState = State.Walking;
                    moveX -= 1;
                    playerDelta.x = moveX * MOVEMENT_SPEED;
                    playerSprite.translateX(playerDelta.x);
                   b2bodyplayer.applyLinearImpulse(new Vector2(moveX * MOVEMENT_SPEED,0), b2bodyplayer.getWorldCenter(),true);
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || moveForwardButton.isDown) {
                    moveForwardButton.isDown = true;
                    moveX += 1;
                    playerDelta.x = moveX * MOVEMENT_SPEED;
                    playerSprite.translateX(playerDelta.x);
                    player_CurrentState = State.Walking;
                    b2bodyplayer.applyLinearImpulse(new Vector2(moveX * MOVEMENT_SPEED,0), b2bodyplayer.getWorldCenter(),true);

                }
                //TODO Determine Character Movement Distance
               // playerDelta.x = moveX * MOVEMENT_SPEED * dt;
                //Movement update
                //playerSprite.translateX(playerDelta.x);
               // camera.position.x += MOVEMENT_SPEED*dt;

                if(Gdx.input.justTouched()){

                    //condition to check if the player has touched the upper half of screen then make the player jump
                    if (Gdx.input.getY() < Gdx.graphics.getHeight() / 2){
                        player_CurrentState = State.Punching;
                        Timer.schedule(new Timer.Task() { @Override public void run() {  player_CurrentState = State.Idle; }}, 0.5f);

                    }
                    //condition to check if the player has touched the bottom half of screen then make the player slide
                    else {
                        player_CurrentState = State.Kicking;
                        Timer.schedule(new Timer.Task() { @Override public void run() {  player_CurrentState = State.Idle; }},0.5f);

                    }
                }


                //TODO Move only if the target cell is empty
//                PlayerClass.getPlayers().translate(playerDelta.x, playerDelta.y);
//                camera.translate(playerDelta);



                break;
            case COMPLETE:
            {

                break;
            }
            case PAUSE:{
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + gameState);
        }
        world.step(1/60f,6,2);
        camera.update();
        tiledMapRenderer.setView(camera);
    }
        @Override
    public void render(float delta) {
        update();
        dt = Gdx.graphics.getDeltaTime();

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                //Round Timer
                if(gameState != GameState.PAUSE && music2.isPlaying() == false){
                    if(checkFirstTimeRoundTime == true){
                        roundTim = TimeUtils.millis();
                        checkFirstTimeRoundTime = false;
                    }
                    if(TimeUtils.timeSinceMillis(roundTim)/1000 < 91 && TimeUtils.timeSinceMillis(roundTim)/1000 >= 0 ){
                        roundTime.setText(String.valueOf(TimeUtils.timeSinceMillis(roundTim)/1000));
                    }
                }
            }
        },6);


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        Player_Frame =  get_Player_current_state();
        Opponent_Frame = OpponentClass.getOpponent().getIdle().getKeyFrame(stateTime, true);


//		frameIndex = walkAnimation.getKeyFrameIndex(stateTime);
//		Gdx.app.log("current time",Float.toString(stateTime));
//		Gdx.app.log("current frame index",Integer.toString(frameIndex));


        tiledMapRenderer.render();
        debugRenderer.render(world,camera.combined);

        batch.begin();
        stage.draw();
        batch.draw(Player_Frame,playerSprite.getX(),50,220,400);
        batch.draw(Opponent_Frame,1000+220,50,-220,400);
        moveBackwardButton.draw(batch);
        moveForwardButton.draw(batch);

        pauseMenuStage.draw();
        batch.end();


    }
    private void newGame() {

        menuBackground.setVisible(false);
        btnRestartGame.setVisible(false);
        btnHowToPlay.setVisible(false);
        btnUnpause.setVisible(false);
        playerWinCount = 0;

        playerSprite.setPosition(600,0);
        roundTim = TimeUtils.millis();
        dt = 0.0f;
        gameState = GameState.PLAYING;
    }

    public TextureRegion get_Player_current_state(){

        if(player_CurrentState == State.Idle){
            Player_Frame = PlayerClass.getPlayers().getIdle().getKeyFrame(stateTime, true);
        }
        if(player_CurrentState == State.Walking){
            Player_Frame = PlayerClass.getPlayers().getWalk().getKeyFrame(stateTime, true);
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
        if(player_CurrentState == State.dead){
            Player_Frame = PlayerClass.getPlayers().getDead().getKeyFrame(stateTime, false);
        }
        if(player_CurrentState == State.Loose){
            Player_Frame = PlayerClass.getPlayers().getLoose().getKeyFrame(stateTime, false);
        }

        return Player_Frame;

    }

    public TextureRegion get_Opponent_current_state(){

        if(OpponentcurrentState == State.Walking){
           Opponent_Frame = OpponentClass.getOpponent().getWalk().getKeyFrame(stateTime, true);
        }
        if(OpponentcurrentState == State.Punching){
            Opponent_Frame =  OpponentClass.getOpponent().getPunch().getKeyFrame(stateTime, false);
        }
        if(OpponentcurrentState == State.Kicking){
            Opponent_Frame = OpponentClass.getOpponent().getKick().getKeyFrame(stateTime, false);
        }
        if(OpponentcurrentState == State.Special){
            Opponent_Frame =  OpponentClass.getOpponent().getSpecial().getKeyFrame(stateTime, false);
        }
        if(OpponentcurrentState == State.Win){
            Opponent_Frame = OpponentClass.getOpponent().getWin().getKeyFrame(stateTime, false);
        }
        if(OpponentcurrentState == State.dead){
            Opponent_Frame = OpponentClass.getOpponent().getDead().getKeyFrame(stateTime, false);
        }
        if(OpponentcurrentState == State.Loose){
            Opponent_Frame = OpponentClass.getOpponent().getLoose().getKeyFrame(stateTime, false);
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
