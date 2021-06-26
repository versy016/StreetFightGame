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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

public class GameClass implements Screen {

    MyGdxGame game;
    public enum GameState { PLAYING, COMPLETE };
    public static final float MOVEMENT_SPEED = 200.0f;
    GameState gameState = GameState.PLAYING;

    public enum State {Idle,Walking, Kicking, Punching, Special, dead, Loose, Win;}

    State player_CurrentState;
    State OpponentcurrentState;

    private Animation walkAnimation;
    private Animation walkAnimation2;
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
    private Image healthbar1;
    private Image healthbar2;

    //UI textures
    private Texture buttonSquareTexture;
    private Texture buttonSquareDownTexture;

    Vector2 playerDelta;
    //Player Coordinates
    int characterX;
    int characterY;

    private Texture playerHealthBar;
    private Texture opponentHealthBar;

    //UI Buttons
    private Button moveLeftButton;
    private Button moveRightButton;

    private Texture comMenuBG;
    private Skin skin;

    Image menuBackground;
    TextButton btnRestartGame;
    Sound btnSound;
    TextButton btnBackToPlayerSelectMenu;
    TextButton btnExit;
    private Stage pauseMenuStage;

    public Body b2bodyplayer;
    public World world;
    private Box2DDebugRenderer debugRenderer;

    private Stage stage;

    public GameClass(MyGdxGame game) {
        this.game = game;
    }

    public void create() {

        //***********************************************************************CompleteMenuStage**************************************************************************************
        comMenuBG = new Texture("Starting Assets/assets/finishedbg.png");

        skin = new Skin(Gdx.files.internal("Starting Assets/assets/uiskin.json"));
        btnRestartGame = new TextButton("Restart", skin, "default");
        btnBackToPlayerSelectMenu = new TextButton("Back To Menu",skin,"default");
        btnExit = new TextButton("Exit", skin, "default");
        btnSound = Gdx.audio.newSound(Gdx.files.internal("Starting Assets/assets/buttonsound.wav"));

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

        btnBackToPlayerSelectMenu.setWidth(600f);
        btnBackToPlayerSelectMenu.setHeight(100f);
        btnBackToPlayerSelectMenu.getLabel().setFontScale(5);
        btnBackToPlayerSelectMenu.setColor(Color.GOLD);
        btnBackToPlayerSelectMenu.setPosition(750, 600);
        btnBackToPlayerSelectMenu.setVisible(false);

        btnExit.setWidth(600f);
        btnExit.setHeight(100f);
        btnExit.getLabel().setFontScale(5);
        btnExit.setColor(Color.GOLD);
        btnExit.setPosition(750, 400);
        btnExit.setVisible(false);


        btnRestartGame.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                btnSound.play(1.0f);
                // TODO when restart the game

            }
        });

        btnExit.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                btnSound.play(1.0f);
                Gdx.app.exit();
            }
        });

        btnBackToPlayerSelectMenu.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                btnSound.play(1.0f);
            }
        });

        //***********************************************************************CompleteMenuStage**************************************************************************

        stage = new Stage();
        pauseMenuStage = new Stage();
        debugRenderer = new Box2DDebugRenderer();

        buttonSquareTexture = new Texture("buttonSquare_blue.png");
        buttonSquareDownTexture = new Texture("buttonSquare_beige_pressed.png");


        playerHealthBar = new Texture(Gdx.files.internal("healthbar.png"));
        opponentHealthBar = new Texture(Gdx.files.internal("healthbar2.png"));

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        //Buttons
        float buttonSize = h * 0.1f;
        moveLeftButton = new Button(20, buttonSize, buttonSize, buttonSize, buttonSquareTexture, buttonSquareDownTexture);
        moveRightButton = new Button(20+buttonSize*2, buttonSize, buttonSize, buttonSize, buttonSquareTexture, buttonSquareDownTexture);

        world = new World(new Vector2(0,10),true);

        PolygonShape playershape = new PolygonShape();
        playershape.setAsBox(15,25);

        BodyDef playerDef = new BodyDef();
        playerDef.position.set(new Vector2(165,35));

        playerDef.type = BodyDef.BodyType.DynamicBody;

        b2bodyplayer = world.createBody(playerDef);
        b2bodyplayer.createFixture(playershape, 0.0f);


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


        stage.addActor(menuBackground);
        stage.addActor(btnRestartGame);
        stage.addActor(btnExit);
        stage.addActor(btnBackToPlayerSelectMenu);
        stage.addActor(playerHealthBar);
        stage.addActor(opponentHealthBar);

        Gdx.input.setInputProcessor(stage);

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
                moveLeftButton.update(checkTouch, touchX, touchY);
                moveRightButton.update(checkTouch, touchX, touchY);

                int moveX = 0;
                int moveY = 0;
                if (Gdx.input.isKeyPressed(Input.Keys.UP) || moveLeftButton.isDown) {
                    moveLeftButton.isDown = true;
                    player_CurrentState = State.Walking;
                    moveX -= 1;
                    playerDelta.x = moveX * MOVEMENT_SPEED * dt;
                    playerSprite.translateX(playerDelta.x);

                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || moveRightButton.isDown) {
                    moveRightButton.isDown = true;
                    moveX += 1;
                    playerDelta.x = moveX * MOVEMENT_SPEED * dt;
                    playerSprite.translateX(playerDelta.x);
                    player_CurrentState = State.Walking;

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

        }

    }
        @Override
    public void render(float delta) {
        update();
        dt = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        Player_Frame =  get_Player_current_state();
        Opponent_Frame = OpponentClass.getOpponent().getIdle().getKeyFrame(stateTime, true);

//		frameIndex = walkAnimation.getKeyFrameIndex(stateTime);
//		Gdx.app.log("current time",Float.toString(stateTime));
//		Gdx.app.log("current frame index",Integer.toString(frameIndex));
        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.begin();
        stage.draw();
        batch.draw(Player_Frame,playerSprite.getX(),50,220,400);
        batch.draw(Opponent_Frame,1000+220,50,-220,400);
        moveLeftButton.draw(batch);
        moveRightButton.draw(batch);
//        pauseMenuStage.draw();
        batch.end();

    }
    private void newGame() {
//        camera.position.x = 1000;
//        camera.position.y = 100;


        playerSprite.setPosition(600,0);

        dt = 0.0f;
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
