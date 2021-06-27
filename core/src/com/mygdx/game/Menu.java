package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

public class Menu implements Screen
{
    MyGdxGame game;
    private Texture background;
    private Texture menuBG;
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private Music music;
    Image imgBackground;
    Image menuBackground;
    TextButton btnPlay;
    Sound btnSound;
    TextButton btnOption;
    TextButton btnExit;

    public Menu(MyGdxGame game)
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
        btnPlay = new TextButton("Play", skin, "default");
        btnOption = new TextButton("Option",skin,"default");
        btnExit = new TextButton("Exit", skin, "default");
        btnSound = Gdx.audio.newSound(Gdx.files.internal("Starting Assets/assets/buttonsound.wav"));
        //animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Starting Assets/assets/menuBackGround.gif").read());

        imgBackground = new Image(background);
        imgBackground.setSize(2300,1100);
        imgBackground.setZIndex(1);


        menuBackground = new Image(menuBG);
        menuBackground.setSize(1800,900);
        menuBackground.setX(200);
        menuBackground.setY(200);
        menuBackground.setZIndex(2);

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

        btnOption.setWidth(600f);
        btnOption.setHeight(100f);
        btnOption.getLabel().setFontScale(5);
        btnOption.setColor(Color.GOLD);
        btnOption.setPosition(750, 600);

        btnExit.setWidth(600f);
        btnExit.setHeight(100f);
        btnExit.getLabel().setFontScale(5);
        btnExit.setColor(Color.GOLD);
        btnExit.setPosition(750, 400);


        btnPlay.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                btnSound.play(1.0f);
                game.setScreen(MyGdxGame.psmclass);
                music.stop();

            }
        });

        btnExit.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                Gdx.app.exit();
//                btnSound.play(1.0f);

            }
        });

        btnOption.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                btnSound.play(1.0f);

            }
        });
        //stage.addActor(animActor);
        stage.addActor(imgBackground);
        stage.addActor(menuBackground);
        stage.addActor(btnPlay);
        stage.addActor(btnExit);
        stage.addActor(btnOption);

        Gdx.input.setInputProcessor(stage);
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
