package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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

    //adding Sound
    //private Sound sound;
    private Music music;



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

    private Stage stage;

    public GameClass(MyGdxGame game) {
        this.game = game;
    }

    public void create() {

        stage = new Stage();

        player1 = new Texture(Gdx.files.internal("player1walk.png")); // #9
        player2 = new Texture(Gdx.files.internal("player3walk.png")); // #9
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
// sound implemented
          //sound = Gdx.audio.newSound(Gdx.files.internal("kick.wav"));
          music= Gdx.audio.newMusic(Gdx.files.internal("321fight.wav"));


       // sound.play(1.0f,0.0f,0.8f);


        music.setVolume(1.0f);
        music.setLooping(false);
        music.play();





        TextureRegion[][] temp = TextureRegion.split(player1,
                player1.getWidth() / 4,
                player1.getHeight() / 1);

        player1walkFrames = new TextureRegion[4];

        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                player1walkFrames[index++] = temp[i][j];
            }
        }
        walkAnimation = new Animation(0.33f, player1walkFrames);

        temp = TextureRegion.split(player2,
                player2.getWidth() / 4,
                player2.getHeight() / 1);

        player2walkFrames = new TextureRegion[4];
       index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                player2walkFrames[index++] = temp[i][j];
            }
        }
        walkAnimation2 = new Animation(0.33f, player2walkFrames);

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
        currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
        currentFrame2 = (TextureRegion) walkAnimation2.getKeyFrame(stateTime, true);

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
    //sound.dispose();
    music.dispose();
    }
}
