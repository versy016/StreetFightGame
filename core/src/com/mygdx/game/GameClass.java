package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameClass implements Screen {

    MyGdxGame game;
    public enum GameState { PLAYING, COMPLETE };
    public static final float MOVEMENT_SPEED = 200.0f;
    GameState gameState = GameState.PLAYING;


    private TextureRegion[][] temp;
    private TextureRegion[] player1walkFrames;
    private TextureRegion[] player2walkFrames;

    private Animation walkAnimation;
    private Animation walkAnimation2;

    private float stateTime;
    private Sprite playerSprite;
    float dt;                                      //game delata time variable
    private SpriteBatch batch;                   // Spritebatch for rendering

    TextureRegion currentFrame;
    TextureRegion currentFrame2;

    TiledMap tiledMap;                  //tiled map
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer tiledMapRenderer; //tiled map renderer

    //UI textures
    private Texture buttonSquareTexture;
    private Texture buttonSquareDownTexture;

    Vector2 playerDelta;
    //Player Coordinates
    int characterX;
    int characterY;


    //UI Buttons
    private Button moveLeftButton;
    private Button moveRightButton;


    private Stage stage;

    public GameClass(MyGdxGame game) {
        this.game = game;
    }

    public void create() {

        stage = new Stage();

        //Textures
        buttonSquareTexture = new Texture("buttonSquare_blue.png");
        buttonSquareDownTexture = new Texture("buttonSquare_beige_pressed.png");

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        //Buttons
        float buttonSize = h * 0.1f;
        moveLeftButton = new Button(20, buttonSize, buttonSize, buttonSize, buttonSquareTexture, buttonSquareDownTexture);
        moveRightButton = new Button(20+buttonSize*2, buttonSize, buttonSize, buttonSize, buttonSquareTexture, buttonSquareDownTexture);


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
        characterX = 600;
        characterY = 50;

        playerSprite = new Sprite(PlayerClass.getPlayers().getWalk().getKeyFrames()[0]);
        stateTime = 0.0f;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 490 , 160  );
        tiledMap = new TmxMapLoader().load("fightmap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        stage.addActor(playerHealthBar);
        stage.addActor(opponentHealthBar);
        Gdx.input.setInputProcessor(stage);


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
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || moveLeftButton.isDown) {
                    moveLeftButton.isDown = true;
                    moveX -= 1;
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || moveRightButton.isDown) {
                    moveRightButton.isDown = true;
                    moveX += 1;
                }
                //TODO Determine Character Movement Distance
                playerDelta.x = moveX * MOVEMENT_SPEED * dt;
                playerDelta.y = moveY * MOVEMENT_SPEED * dt;
                //Movement update
                if (playerDelta.len2()>0) { //Don't move every frame

                    //TODO Retrieve Collision layer

                    //Don't do anything if we're not moving
                    if ((moveX != 0 || moveY != 0)
                        //TODO Also check map bounds to prevent exceptions when accessing map cells
                    ) {

                        //TODO Retrieve Target Tile

                        //TODO Move only if the target cell is empty
                        PlayerClass.getPlayers().translate(playerDelta.x, playerDelta.y);
                        camera.translate(playerDelta);

                    }
                }
                break;
            case COMPLETE:


        }

    }
        @Override
    public void render(float delta) {

        //Update the Game State
        update();

        dt = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame =  PlayerClass.getPlayers().getWalk().getKeyFrame(stateTime, true);
        currentFrame2 = OpponentClass.getOpponent().getWalk().getKeyFrame(stateTime, true);
//		frameIndex = walkAnimation.getKeyFrameIndex(stateTime);
//		Gdx.app.log("current time",Float.toString(stateTime));
//		Gdx.app.log("current frame index",Integer.toString(frameIndex));
        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.begin();
        stage.draw();
        batch.draw(currentFrame,600,50,200,400);
        batch.draw(currentFrame2,1000,50,200,400);
        moveLeftButton.draw(batch);
        moveRightButton.draw(batch);
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
        buttonSquareTexture.dispose();
        buttonSquareDownTexture.dispose();
    }
}
