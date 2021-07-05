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

    public enum State {Idle,Walking, Kicking, Punching, Special, Dead, Loose, Damage, Win, Defend}

    State player_CurrentState;
    State opponent_CurrentState;

    Image messageBoxBackGround;

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
    private TextButton defendButton;

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
    Label fightLabel;
    Label roundLabel;
    Label winningLabel;

    private Stage pauseMenuStage;
    static long roundTim;
    static long opponentMovesTime;
    static Timer timer;

    boolean roundWinStatus = false;
    int playerWinCount=0;
    int opponentWinCount=0;

    private Stage stage;

    HealthBar playerHealthBar;
    HealthBar opponentHealthBar;
    int opponentHits;
    int playerHits;

    int checkplayer =0;
    int thedamagePlayer =0;
    int checkopponent=0;
    int thedamageOpponent=0;
    int Rounds =0;

    boolean checkRoundFinishStatus = false;
    boolean go = false;
    Texture winRadioBtn;
    Texture looseRadioBtn;

    Skin skin;

    TextButton btnMessageBox;
    Label message;

    double opponentHealth;
    double playerHealth;
    Music punchMusic;
    Music kickMusic;
    Music specialMusic;
    Music damageMusic;
    Music deadMusic;

    Music backGroundMusic;

    public GameClass(MyGdxGame game) {
        this.game = game;
    }


    public void create() {

        //***********************************************************************MessageBox*********************************************************************************************
        Texture menuBG = new Texture("Starting Assets/assets/finishedbg.png");
        skin = new Skin(Gdx.files.internal("Starting Assets/assets/uiskin.json"));
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
            }
        });
        //***********************************************************************MessageBox*********************************************************************************************
        punchMusic = Gdx.audio.newMusic(Gdx.files.internal("punch.wav"));
        deadMusic = Gdx.audio.newMusic(Gdx.files.internal("death.wav"));
        damageMusic = Gdx.audio.newMusic(Gdx.files.internal("Damage.wav"));
        kickMusic = Gdx.audio.newMusic(Gdx.files.internal("kick.wav"));
        specialMusic = Gdx.audio.newMusic(Gdx.files.internal("special.wav"));

        punchMusic.setVolume(1.0f);
        punchMusic.setLooping(false);

        deadMusic.setVolume(1.0f);
        deadMusic.setLooping(false);

        damageMusic.setVolume(1.0f);
        damageMusic.setLooping(false);

        kickMusic.setVolume(1.0f);
        kickMusic.setLooping(false);

        specialMusic.setVolume(1.0f);
        specialMusic.setLooping(false);

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
        specialButton = new TextButton("S", skin, "default");
        defendButton = new TextButton("D", skin, "default");

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
        btnRestartGame.setPosition(750, 750);
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
        btnUnpause.setPosition(750, 450);
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

        defendButton.setWidth(80f);
        defendButton.setHeight(80f);
        defendButton.getLabel().setFontScale(3);
        defendButton.setColor(Color.BLACK);
        defendButton.setPosition(350, 300);
        defendButton.setVisible(false);

        specialButton.setWidth(80f);
        specialButton.setHeight(80f);
        specialButton.getLabel().setFontScale(3);
        specialButton.setColor(Color.BLACK);
        specialButton.setPosition(100, 400);
        specialButton.setVisible(false);

        psmMenuButton.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                mainMenuButton.setVisible(false);
                psmMenuButton.setVisible(false);
                menuBackground.setVisible(false);
                btnRestartGame.setVisible(false);
                gameState = GameState.PLAYING;
                Rounds = 0;
                opponentWinCount = 0;
                playerWinCount = 0;
                opponentRoundWins.setText("Wins "+opponentWinCount);
                playerRoundWins.setText("Wins "+playerWinCount);

                MyGdxGame.psmclass.dispose();
                game.setScreen(MyGdxGame.psmclass);
            }
        });

        mainMenuButton.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                mainMenuButton.setVisible(false);
                psmMenuButton.setVisible(false);
                menuBackground.setVisible(false);
                btnRestartGame.setVisible(false);
                gameState = GameState.PLAYING;
                Rounds = 0;
                opponentWinCount = 0;
                playerWinCount = 0;
                opponentRoundWins.setText("Wins "+opponentWinCount);
                playerRoundWins.setText("Wins "+playerWinCount);

                MyGdxGame.psmclass.dispose();
                game.setScreen(MyGdxGame.mclass);
            }
        });

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
                Gdx.input.setInputProcessor(pauseMenuStage);
            }
        });

        btnRestartGame.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                btnSound.play(1.0f);
                opponentWinCount = 0;
                playerWinCount = 0;
                Rounds = 0;
                newGame();
                Gdx.input.setInputProcessor(stage);
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

                Gdx.input.setInputProcessor(stage);
            }
        });

        btnHowToPlay.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                btnSound.play(1.0f);
                messageBoxBackGround.setVisible(true);
                message.setVisible(true);
                btnMessageBox.setVisible(true);

                Gdx.input.setInputProcessor(pauseMenuStage);
            }
        });

        //***********************************************************************CompleteMenuStage**************************************************************************************


        timer = new Timer();
        stage = new Stage();
        pauseMenuStage = new Stage();
        //UI textures
        Texture buttonSquareTextureForForward = new Texture("rightArrow.png");
        Texture buttonSquareTextureForBackward = new Texture("leftArrow.png");
        playerRoundWins = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        opponentRoundWins = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        roundTime = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Countdown = new Label("", new Label.LabelStyle(new BitmapFont(), Color.RED));
        fightLabel = new Label("FIGHT", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        roundLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        winningLabel = new Label("",new Label.LabelStyle(new BitmapFont(), Color.GOLD));

        playerRoundWins.setFontScale(3,2);
        playerRoundWins.setPosition(100,1050);
        playerRoundWins.setVisible(true);

        roundLabel.setFontScale(12,6);
        roundLabel.setPosition((float)Gdx.graphics.getWidth()/3,(float)Gdx.graphics.getHeight()/1.5f);
        roundLabel.setVisible(true);

        winningLabel.setFontScale(6,6);
        winningLabel.setPosition((float)Gdx.graphics.getWidth()/3,(float)Gdx.graphics.getHeight()/1.5f);
        winningLabel.setVisible(true);

        opponentRoundWins.setFontScale(3,2);
        opponentRoundWins.setPosition(1300,1050);
        opponentRoundWins.setVisible(true);

        roundTime.setFontScale(8,6);
        roundTime.setPosition(1025,950);

        Countdown.setFontScale(20,16);
        Countdown.setPosition(975,800);

        fightLabel.setFontScale(12,6);
        fightLabel.setPosition((float)Gdx.graphics.getWidth()/2.75f,(float)Gdx.graphics.getHeight()/1.5f);


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
        playerHealth = 700;
        opponentHits =0;
        playerHits = 0;
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
        stage.addActor(fightLabel);
        stage.addActor(specialButton);
        stage.addActor(punchButton);
        stage.addActor(kickButton);
        stage.addActor(defendButton);
        stage.addActor(roundLabel);
        stage.addActor(winOnePlayer);
        stage.addActor(winTwoPlayer);
        stage.addActor(winOneOpponent);
        stage.addActor(winTwoOpponent);
        stage.addActor(btnPause);

        stage.addActor(winOnePlayergrey);
        stage.addActor(winTwoPlayergrey);
        stage.addActor(winTwoOpponentgrey);
        stage.addActor(winOneOpponentgrey);

        pauseMenuStage.addActor(menuBackground);
        pauseMenuStage.addActor(btnUnpause);
        pauseMenuStage.addActor(btnHowToPlay);
        pauseMenuStage.addActor(btnRestartGame);
        pauseMenuStage.addActor(mainMenuButton);
        pauseMenuStage.addActor(psmMenuButton);
        pauseMenuStage.addActor(winningLabel);
        pauseMenuStage.addActor(messageBoxBackGround);
        pauseMenuStage.addActor(message);
        pauseMenuStage.addActor(btnMessageBox);


        Gdx.input.setInputProcessor(stage);

        newGame();


    }
    @Override
    public void show() {

        Gdx.app.log("Gamescreen: ","menuScreen show called");
        create();
    }


    private void updatePlayer() {


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

                kickButton.addListener(new ClickListener()
                {
                    @Override
                    public void clicked (InputEvent event, float x, float y)
                    {
                        kickButton.setVisible(false);
                        player_CurrentState = State.Kicking;
                        if(opponentSprite.getX() - playerSprite.getX() <= 600 && opponent_CurrentState != State.Defend )
                        {
                            opponent_CurrentState = State.Damage;

                            Timer.schedule(new Timer.Task() { @Override public void run() {
                                player_CurrentState = State.Idle;
                                opponent_CurrentState = State.Idle;
                                kickButton.setVisible(true);
                                checkplayer =1;
                                playerHits++;
                                thedamagePlayer = 10;
                            } },0.20f);
                        }
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
                        if(opponentSprite.getX() - playerSprite.getX() <= 600 && opponent_CurrentState != State.Defend) {
                            opponent_CurrentState = State.Damage;
                            Timer.schedule(new Timer.Task() { @Override public void run() {
                                player_CurrentState = State.Idle;
                                opponent_CurrentState = State.Idle;
                                specialButton.setVisible(true);
                                checkplayer =1;
                                thedamagePlayer = 40;
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

                        if(opponentSprite.getX() - playerSprite.getX() <= 600  && opponent_CurrentState != State.Defend) {
                            opponent_CurrentState = State.Damage;
                            Timer.schedule(new Timer.Task() { @Override public void run() {
                                opponent_CurrentState = State.Idle;
                                player_CurrentState = State.Idle;
                                punchButton.setVisible(true);
                                checkplayer =1;
                                playerHits++;
                                thedamagePlayer = 8;
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
                defendButton.addListener(new ClickListener()
                {
                    @Override
                    public void clicked (InputEvent event, float x, float y)
                    {
                        defendButton.setVisible(false);

                        player_CurrentState = State.Defend;
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                defendButton.setVisible(true);
                                player_CurrentState = State.Idle;
                            }
                        }, 0.5f);

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
                if(checkplayer ==1){
                    opponentHealthBar.setWidthInner((int) decreaseOpponentHealth(thedamagePlayer));
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
                    player_CurrentState = State.Dead;
                    opponent_CurrentState = State.Win;
                    roundWinStatus = true;
                    if(!checkRoundFinishStatus){
                        checkRoundFinishStatus = true;
                        playerHealthBar.setWidthInner(0);
                        winningLabel.setText("Opponent Won Round 0"+Rounds);
                        winningLabel.setVisible(true);
                        opponentWinCount++;
                        Rounds++;
                        timer.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {
                                backGroundMusic.stop();
                                game.setScreen(MyGdxGame.gclass);
                            }
                        },2);
                    }

                }
                else if(opponentHealthBar.getWidthInner() <= 0){
                    opponent_CurrentState = State.Dead;
                    player_CurrentState = State.Win;
                    roundWinStatus = true;
                    if(!checkRoundFinishStatus){
                        checkRoundFinishStatus = true;
                        opponentHealthBar.setWidthInner(0);
                        playerWinCount++;
                        Rounds++;
                        winningLabel.setText("You Won Round 0"+Rounds);
                        winningLabel.setVisible(true);
                        timer.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {
                                backGroundMusic.stop();
                                game.setScreen(MyGdxGame.gclass);
                            }
                        },2);
                    }

                }
                break;

            case ROUND_FINISHED:
                    if(playerHealthBar.getWidthInner()> opponentHealthBar.getWidthInner()){
                        opponent_CurrentState = State.Loose;
                        player_CurrentState = State.Idle;
                        roundWinStatus = true;
                        if(!checkRoundFinishStatus){
                            checkRoundFinishStatus = true;
                            playerWinCount++;
                            Rounds++;
                            winningLabel.setText("You Won Round 0"+Rounds);
                            winningLabel.setVisible(true);
                            timer.scheduleTask(new Timer.Task() {
                                @Override
                                public void run() {
                                    backGroundMusic.stop();
                                    game.setScreen(MyGdxGame.gclass);
                                }
                            },3);
                        }

                    }
                    else if(playerHealthBar.getWidthInner()< opponentHealthBar.getWidthInner()){
                        opponent_CurrentState = State.Win;
                        player_CurrentState = State.Loose;
                        roundWinStatus = true;
                        if(!checkRoundFinishStatus){
                            checkRoundFinishStatus = true;
                            Rounds++;
                            opponentWinCount++;
                            winningLabel.setText("Opponent Won Round 0"+Rounds);
                            winningLabel.setVisible(true);

                            timer.scheduleTask(new Timer.Task() {
                                @Override
                                public void run() {
                                    backGroundMusic.stop();
                                    game.setScreen(MyGdxGame.gclass);
                                }
                            },3);
                        }
                        game.setScreen(MyGdxGame.gclass);
                    }
                break;
            case COMPLETE:
                    roundTime.setVisible(false);
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

                if(opponentSprite.getX() - playerSprite.getX() <= 650 && !roundWinStatus ){

                    if((TimeUtils.timeSinceMillis(roundTim)/1000)%3 == 0 && TimeUtils.timeSinceMillis(opponentMovesTime)/1000 >= 2 ){
                        opponent_CurrentState = State.Punching;

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                opponent_CurrentState = State.Idle;

                                if (opponentSprite.getX() - playerSprite.getX() <= 630) {
                                    player_CurrentState = State.Damage;
                                    opponent_CurrentState = State.Idle;
                                    Timer.schedule(new Timer.Task() { @Override public void run() { player_CurrentState = State.Idle;}},0.25f);
                                    checkopponent = 1;
                                    thedamageOpponent = 8;
                                    opponentHits++;
                                }
                            }
                        }, 0.25f);
                        opponentMovesTime = opponentMovesTime+1000;

                    }
                    if((TimeUtils.timeSinceMillis(roundTim)/1000)%11 == 0 && TimeUtils.timeSinceMillis(opponentMovesTime)/1000 >= 2 ){
                        opponent_CurrentState = State.Special;

                        Timer.schedule(new Timer.Task() { @Override public void run() {
                            opponent_CurrentState = State.Idle;

                            if (opponentSprite.getX() - playerSprite.getX() <= 630) {
                                player_CurrentState = State.Damage;
                                opponent_CurrentState = State.Idle;
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        player_CurrentState = State.Idle;
                                    }
                                }, 0.25f);
                                checkopponent = 1;
                                thedamageOpponent = 10;
                                opponentHits++;
                            }
                            opponentMovesTime = opponentMovesTime + 1000;
                        }},0.25f);
                    }
                    else if((TimeUtils.timeSinceMillis(roundTim)/1000)%5 == 0 && TimeUtils.timeSinceMillis(opponentMovesTime)/1000 >= 2 ){

                        opponent_CurrentState = State.Kicking;

                        Timer.schedule(new Timer.Task() { @Override public void run() {
                            opponent_CurrentState = State.Idle;

                            if(opponentSprite.getX() - playerSprite.getX() <= 630){
                                player_CurrentState = State.Damage;
                                opponent_CurrentState = State.Idle;
                                Timer.schedule(new Timer.Task() { @Override public void run() { player_CurrentState = State.Idle;}},0.25f);
                                checkopponent =1;
                                thedamageOpponent = 10;
                                opponentHits++;
                            }
                        } },0.25f);
                        opponentMovesTime = opponentMovesTime+1000;

                    }
                    else if ((TimeUtils.timeSinceMillis(roundTim)/1000)%7 == 0 && TimeUtils.timeSinceMillis(opponentMovesTime)/1000 >= 3 && opponent_CurrentState != State.Kicking && opponent_CurrentState != State.Punching && opponent_CurrentState != State.Special ) {
                        opponent_CurrentState = State.Defend;
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                opponent_CurrentState = State.Idle;
                            }
                        }, 0.5f);
                    }
                }
                if(playerSprite.getX()+700 <  opponentSprite.getX() && go){
                    opponent_CurrentState = State.Walking;
                    opponentSprite.translateX(-opponentDelta.x);

                }
                else if(opponentSprite.getX()- playerSprite.getX() <= 300 ){
                    opponent_CurrentState = State.Idle;
                }
                if(checkopponent ==1){
                    playerHealthBar.setWidthInner((int) decreasePlayerHealth(thedamageOpponent));
                }
            case PAUSE:
                break;
        }
    }

    private double decreaseOpponentHealth(int damage){
        opponentHealth = opponentHealth-(0.7*damage);
        checkplayer =0;
        return opponentHealth;

    }
    private double decreasePlayerHealth(int damage){
        playerHealth = playerHealth-(0.7*damage);
        checkopponent =0;
        return playerHealth;

    }

        @Override
    public void render(float delta) {

        updatePlayer();
        updateOpponent();
        dt = Gdx.graphics.getDeltaTime();

        stateTime += Gdx.graphics.getDeltaTime();

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {

                //Round Timer
                if(gameState == GameState.PLAYING){
                    if(TimeUtils.timeSinceMillis(roundTim)/1000 < 91 && TimeUtils.timeSinceMillis(roundTim)/1000 >= 0 ){
                        roundTime.setText(String.valueOf(TimeUtils.timeSinceMillis(roundTim)/1000));
                    }
                    if(TimeUtils.timeSinceMillis(roundTim)/1000 > 90) {
                        gameState = GameState.ROUND_FINISHED;
                        Gdx.app.log("Round Finish","Finished");
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
        winningLabel.setVisible(false);
        roundWinStatus = false;
        checkRoundFinishStatus = false;
        roundTim = TimeUtils.millis();
        opponentMovesTime = TimeUtils.millis();
        Gdx.input.setInputProcessor(stage);
        opponentRoundWins.setText("Wins "+opponentWinCount);
        playerRoundWins.setText("Wins "+playerWinCount);
        kickButton.setVisible(false);
        punchButton.setVisible(false);
        specialButton.setVisible(false);
        gameState = GameState.PLAYING;
        roundTime.setVisible(false);
        Countdown.setVisible(false);
        fightLabel.setVisible(false);
        fightLabel.setText("FIGHT");
        mainMenuButton.setVisible(false);
        psmMenuButton.setVisible(false);
        opponentHealthBar.setWidthInner(700);
        playerHealthBar.setWidthInner(700);

        player_CurrentState = State.Idle;
        opponent_CurrentState = State.Idle;

        menuBackground.setVisible(false);
        btnRestartGame.setVisible(false);
        btnHowToPlay.setVisible(false);
        btnUnpause.setVisible(false);
        go = false;

        if(Rounds == 0)
        {
            roundLabel.setText("ROUND 1");
            tiledMap = new TmxMapLoader().load("chinatown.tmx");
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

            camera = new OrthographicCamera();
            camera.setToOrtho(false, 730 , 330  );
            Music music2 = Gdx.audio.newMusic(Gdx.files.internal("round1.wav"));


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

        }
        else if (Rounds == 1){
            tiledMap = new TmxMapLoader().load("fightmap.tmx");
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
            roundLabel.setText("ROUND 2");

            camera = new OrthographicCamera();
            camera.setToOrtho(false, 490 , 160  );
            Music music2 = Gdx.audio.newMusic(Gdx.files.internal("round2.wav"));


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

        }
        else if(Rounds == 2){
            tiledMap = new TmxMapLoader().load("downtown.tmx");
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

            roundLabel.setText("ROUND 3");

            camera = new OrthographicCamera();
            camera.setToOrtho(false, 720 , 330  );

            Music music2 = Gdx.audio.newMusic(Gdx.files.internal("round3.wav"));

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

        }
        else if(Rounds > 2){
            gameState = GameState.COMPLETE;
            Gdx.input.setInputProcessor(pauseMenuStage);
            Music music2 = Gdx.audio.newMusic(Gdx.files.internal("Win.wav"));


            music2.setVolume(1.0f);
            music2.setLooping(false);

            music2.play();


        }

        if(gameState == GameState.PLAYING) {

            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    roundLabel.setVisible(false);
                    Countdown.setText(3);
                    Countdown.setVisible(true);

                }
            }, 2);
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    Countdown.setText(2);
                    Countdown.setVisible(true);

                }
            }, 3);
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    Countdown.setText(1);
                    Countdown.setVisible(true);

                }
            }, 4);

            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    Countdown.setVisible(false);
                    fightLabel.setVisible(true);

                }
            }, 5);
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    fightLabel.setVisible(false);
                    roundTim = TimeUtils.millis();
                    roundTime.setVisible(true);
                    gameState = GameState.PLAYING;
                    kickButton.setVisible(true);
                    punchButton.setVisible(true);
                    specialButton.setVisible(true);
                    defendButton.setVisible(true);
                    backGroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundloopmusic.wav"));
                    backGroundMusic.setVolume(0.3f);
                    backGroundMusic.setLooping(false);
                    backGroundMusic.play();
                    go = true;
                }
            }, 6);


        }
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
            punchMusic.play();

        }
        if(player_CurrentState == State.Kicking){
            Player_Frame = PlayerClass.getPlayers().getKick().getKeyFrame(stateTime, false);
            kickMusic.play();

        }
        if(player_CurrentState == State.Special){
            Player_Frame =  PlayerClass.getPlayers().getSpecial().getKeyFrame(stateTime, false);
            specialMusic.play();
        }
        if(player_CurrentState == State.Win){
            Player_Frame = PlayerClass.getPlayers().getWin().getKeyFrame(stateTime, false);
        }
        if(player_CurrentState == State.Dead){
            Player_Frame = PlayerClass.getPlayers().getDead().getKeyFrame(stateTime, false);
            damageMusic.play();
        }
        if(player_CurrentState == State.Loose){
            Player_Frame = PlayerClass.getPlayers().getLoose().getKeyFrame(stateTime, false);
        }
        if(player_CurrentState == State.Damage){
            Player_Frame = PlayerClass.getPlayers().getDamage().getKeyFrame(stateTime, false);
            damageMusic.play();
        }
        if(player_CurrentState == State.Defend){
            Player_Frame = PlayerClass.getPlayers().getDefend().getKeyFrame(stateTime, false);
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
            Opponent_Frame =  OpponentClass.getOpponent().getPunch().getKeyFrame(0.33f, false);
            punchMusic.play();
        }
        if(opponent_CurrentState == State.Kicking){
            Opponent_Frame = OpponentClass.getOpponent().getKick().getKeyFrame(stateTime, false);
            kickMusic.play();
        }
        if(opponent_CurrentState == State.Special){
            Opponent_Frame =  OpponentClass.getOpponent().getSpecial().getKeyFrame(stateTime, false);
            specialMusic.play();
        }
        if(opponent_CurrentState == State.Win){
            Opponent_Frame = OpponentClass.getOpponent().getWin().getKeyFrame(stateTime, false);
        }
        if(opponent_CurrentState == State.Dead){
            Opponent_Frame = OpponentClass.getOpponent().getDead().getKeyFrame(stateTime, false);
            deadMusic.play();
        }
        if(opponent_CurrentState == State.Loose){
            Opponent_Frame = OpponentClass.getOpponent().getLoose().getKeyFrame(stateTime, false);
        }
        if(opponent_CurrentState == State.Damage){
            Opponent_Frame = OpponentClass.getOpponent().getDamage().getKeyFrame(stateTime, false);
            damageMusic.play();
        }
        if(opponent_CurrentState == State.Defend){
            Opponent_Frame = OpponentClass.getOpponent().getDefend().getKeyFrame(stateTime, false);
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
        this.game.dispose();
        skin.dispose();
        stage.dispose();
        pauseMenuStage.dispose();
        batch.dispose();
    }
    public void styleButton(TextButton btn){
        btn.setWidth(300f);
        btn.setHeight(60f);
        btn.getLabel().setFontScale(3);
        btn.setColor(Color.GOLD);
    }
}
