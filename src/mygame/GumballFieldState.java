package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.Timer;
import com.jme3.util.SkyFactory;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mygame.GameController.GameStates;

/**
 *
 */
public class GumballFieldState extends AbstractAppState implements AnalogListener {

    private Nifty nifty;
    private GameController app;
    private Screen screen;
    private ViewPort viewPort;
    private Node rootNode;
    private Node guiNode;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Renderer renderer;
    private AssetManager assetManager;
    private Timer timer;
    
    private BitmapFont defaultFont;
    private boolean START;
    private int difficulty, score, colorInt, highCap, lowCap, diffHelp;
    private Node player;
    private Spatial fcoin;
    private ArrayList<Spatial> cubeField;
    private ArrayList<ColorRGBA> obstacleColors;
    private float speed, coreTime, coreTime2, playTime = 10.0f;
    private float camAngle = 0;
    private static BitmapText fpsScoreText;
    private BitmapText pressStart;
    private float fpsRate = 1000f / 1f;
    private BitmapText timeText;

    /**
     * custom methods
     */
    public GumballFieldState() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (GameController) app; // can cast Application to something more specific
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        this.renderer = this.app.getRenderer();
        this.timer = this.app.getTimer();
        this.timer.reset();
        this.guiNode = this.app.getGuiNode();
        System.out.println(this.app.getCamera().getLocation());
        Keys();
        rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Skybox/space.jpg", true));
        defaultFont = assetManager.loadFont("Interface/Fonts/AppleChancery1.fnt");
        pressStart = new BitmapText(defaultFont, false);
        fpsScoreText = new BitmapText(defaultFont, false);
        timeText = new BitmapText(defaultFont, false);

        loadText(fpsScoreText, "Current Score:", defaultFont, 0, 3, 0);
        loadText(pressStart, "PRESS ENTER", defaultFont, 0, 4, 0);
        loadText(timeText, "Time Remaining(s): 20", defaultFont, 0, 2, 0);

        player = Player.createPlayer(app,getPlayerModel());

        rootNode.attachChild(player);
        cubeField = new ArrayList<Spatial>();
        obstacleColors = new ArrayList<ColorRGBA>();

        CoinPrototype.initialize(app, player, difficulty, fcoin);        
        gameReset();
    }

    /**
     * Used to reset cubeField
     */
    private void gameReset() {

        lowCap = 10;
        colorInt = 0;
        highCap = 40;
        difficulty = highCap;

        for (Spatial cube : cubeField) {
            cube.removeFromParent();    
        }
        cubeField.clear();

        if (fcoin != null) {
            fcoin.removeFromParent();
        }
        fcoin = CoinPrototype.createFirstCoin();

        obstacleColors.clear();
        obstacleColors.add(ColorRGBA.Orange);
        obstacleColors.add(ColorRGBA.Red);
        obstacleColors.add(ColorRGBA.Yellow);
        renderer.setBackgroundColor(ColorRGBA.White);
        speed = lowCap / 400f;
        coreTime = 20.0f;
        coreTime2 = 10.0f;
        diffHelp = lowCap;
        player.setLocalTranslation(0, 0, 0);
    }

    /**
     * Nifty GUI ScreenControl methods
     */
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    public void setScoreText(int score) {
        this.score = score;
        fpsScoreText.setText("Current Score: " + score);
    }

//    @Override
//    public void requestClose(boolean esc) {
//        if (!esc){
//            System.out.println("The game was quit.");
//        }else{
//            System.out.println("Player has Collided. Final Score is " + score);
//            fpsScoreText.setText("Player has Collided. Final Score is " + score);
//        }
//        context.destroy(false);
//    }
    /**
     * If Game is Lost display Score and Reset the Game
     */
    private void gameLost() {
        START = false;
        loadText(pressStart, "You lost! Press enter to try again.", defaultFont, 0, 5, 0);
        gameReset();
    }

    /**
     * Core Game Logic
     */
    private void gameLogic(float tpf) {

        if (speed < .1f) {
            speed += .000001f * tpf * fpsRate;
        }

        player.move(speed * tpf * fpsRate, 0, 0);

        try {
            Spatial coin = CoinFactory.getCoinInstance(tpf);
            cubeField.add(coin);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (cubeField.isEmpty()) {
            //requestClose(false);
        } else {
            CollisionDetectionSubject subject = new CollisionDetectionSubject(this);
            subject.checkCollision(cubeField, player);
            Coin.removePastCoins(cubeField, player);
        }

    }

    /**
     * Sets up the keyboard bindings
     */
    private void Keys() {
        inputManager.addMapping("START", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addListener(this, "START", "Left", "Right");
    }

    public void onAnalog(String binding, float value, float tpf) {
        if (binding.equals("START") && !START) {
            START = true;
            guiNode.detachChild(pressStart);
        } else if (START == true && binding.equals("Left")) {
            player.move(0, 0, -(speed / 2f) * value * fpsRate);

            camAngle -= value*tpf;
        } else if (START == true && binding.equals("Right")) {
            player.move(0, 0, (speed / 2f) * value * fpsRate);
            camAngle += value*tpf;
        }
    }

    /**
     * Sets up a BitmapText to be displayed
     *
     * @param txt the Bitmap Text
     * @param text the
     * @param font the font of the text
     * @param x
     * @param y
     * @param z
     */
    private void loadText(BitmapText txt, String text, BitmapFont font, float x, float y, float z) {
        txt.setSize(font.getCharSet().getRenderedSize());
        txt.setLocalTranslation(txt.getLineWidth() * x, txt.getLineHeight() * y, z);
        txt.setColor(ColorRGBA.White);
        txt.setText(text);
        guiNode.attachChild(txt);
    }

    private void setScoreNode(){
        Node scoreNode = new Node("score");
        scoreNode.setUserData("score", this.score);
        guiNode.attachChild(scoreNode);
    }
    
    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    @Override
    public void update(float tpf) {
        camAngle = Player.camTakeOver(tpf, app, player, camAngle);
        
        if (timer.getTimeInSeconds() < playTime) {
            if (START) {
                timeText.setText("Time Remaining(s): " + (int) (playTime - timer.getTimeInSeconds()));
                gameLogic(tpf);
            }
        } else {   
            app.changeGameStateTo(GameStates.TIME_EXP,this);
        }
    }

    private String getPlayerModel() {
        return guiNode.getChild("SelectedPlayer").getUserData("SelectedPlayer");
    }
    
  
    @Override
    public void cleanup() {
        setScoreNode();
        rootNode.detachAllChildren();
        inputManager.removeListener(this);
    }

}
