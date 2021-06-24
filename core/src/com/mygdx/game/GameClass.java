package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameClass implements Screen {

    MyGdxGame game;

    private Texture player1;
    private Texture player2;
    private Texture player3;
    private Texture player4;
    private Texture player1healthbar;
    private Texture player2healthbar;

    private TextureRegion[][] temp;
    private TextureRegion[] player1walkFrames;
    private TextureRegion[] player2walkFrames;

    private Animation walkAnimation;
    private Animation walkAnimation2;

    private float stateTime;
    private Sprite playerSprite;
    float dt;               //game delata time variable
    private SpriteBatch batch;                   // Spritebatch for rendering

    TextureRegion currentFrame;
    TextureRegion currentFrame2;

    TiledMap tiledMap;                  //tiled map
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer tiledMapRenderer; //tiled map renderer
    private Image healthbar1;
    private Image healthbar2;

    public Body b2bodyplayer;
    public World world;
    private Box2DDebugRenderer debugRenderer;

    private Stage stage;

    public GameClass(MyGdxGame game) {
        this.game = game;
    }

    public void create() {

        stage = new Stage();
        debugRenderer = new Box2DDebugRenderer();

        player1healthbar = new Texture(Gdx.files.internal("healthbar.png"));
        player2healthbar = new Texture(Gdx.files.internal("healthbar2.png"));

        healthbar1 = new Image(player1healthbar);
        healthbar1.setSize(700,120);
        healthbar1.setX(100);
        healthbar1.setY(950);

        healthbar2 = new Image(player2healthbar);
        healthbar2.setSize(700,120);
        healthbar2.setX(1300);
        healthbar2.setY(950);

        //creating box2d body for player and opponents
        world = new World(new Vector2(0,10),true);

        PolygonShape playershape = new PolygonShape();
        playershape.setAsBox(15,25);

        BodyDef playerDef = new BodyDef();
        playerDef.position.set(new Vector2(165,35));

        playerDef.type = BodyDef.BodyType.DynamicBody;

        b2bodyplayer = world.createBody(playerDef);
       b2bodyplayer.createFixture(playershape, 0.0f);

        batch = new SpriteBatch();                // #12

        stateTime = 0.0f;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 490 , 160  );
        tiledMap = new TmxMapLoader().load("fightmap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        stage.addActor(healthbar1);
        stage.addActor(healthbar2);
        Gdx.input.setInputProcessor(stage);

    }
    @Override
    public void show() {
        Gdx.app.log("Gamescreen: ","menuScreen show called");
        create();
    }

    private void update() {


    }
        @Override
    public void render(float delta) {
        dt = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame =  PlayerClass.getPlayers().getIdle().getKeyFrame(stateTime, true);
        currentFrame2 = OpponentClass.getOpponent().getIdle().getKeyFrame(stateTime, true);

        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        debugRenderer.render(world, camera.combined);
        batch.begin();
        stage.draw();
        batch.draw(currentFrame,600,50,200,400);
        batch.draw(currentFrame2,1000+220,50,-220,400);
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

    }
}
