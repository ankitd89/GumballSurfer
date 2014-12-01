package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.renderer.Camera;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.Timer;
import com.jme3.util.SkyFactory;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 */
public class TimeExpiredState extends AbstractAppState {

    private Spatial sceaneModel;
    private Nifty nifty;
    private SimpleApplication app;
    private Screen screen;
    private ViewPort viewPort;
    private Node rootNode;
    private Node guiNode;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private Renderer renderer;
    private AssetManager assetManager;
    private Timer timer;
    private final ColorRGBA backgroundColor = ColorRGBA.Blue;
    private Node localRootNode = new Node("Gumball field Screen RootNode");
    private Node localGuiNode = new Node("Gumball field Screen GuiNode");
    private Camera cam;
    private BitmapFont defaultFont;
    private static BitmapText fpsScoreText;
    private int score;
    private int totalGumballs = 0;
    Spatial gumBallMachine1;
    Spatial gumBallMachine2;
    Spatial gumBallMachine3;
    /**
     * custom methods
     */
    public TimeExpiredState() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        //System.out.println("Start initialize");
        super.initialize(stateManager, app);

        this.app = (SimpleApplication) app; // can cast Application to something more specific
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        this.renderer = this.app.getRenderer();
        this.timer = this.app.getTimer();
        this.guiNode = this.app.getGuiNode();
        this.cam = this.app.getCamera();
        
        
        defaultFont = assetManager.loadFont("Interface/Fonts/AppleChancery1.fnt");
        rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Skybox/space.jpg", true));

         setScore();
        
        initGumballMachine();
        initGumballs();
        initLight();
    }

    public void startGame(String nextScreen) {
        nifty.gotoScreen(nextScreen);  // switch to another screen
    }

    public void quitGame() {
        nifty.exit();
        app.stop();
    }

    /**
     * Nifty GUI ScreenControl methods
     */
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
    }

    @Override
    public void cleanup() {
        rootNode.detachChild(localRootNode);
        guiNode.detachChild(localGuiNode);
    }

    private void initLight() {
        /**
         * A white ambient light source.
         */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);
    }

    private void setScore() {
        score = guiNode.getChild("score").getUserData("score");
        totalGumballs = (int) score / 2;

        String gumballText = "";

        if (totalGumballs > 0) {
            gumballText = "Congratulations !! You got " + totalGumballs + " gumballs. Enjoy the gumballs!!";
        } else {
            gumballText = "Oops !! You are out of coins ! Please try again to get the gumballs ";
        }
        //defaultFont = assetManager.loadFont("Interface/Fonts/AppleChancery.fnt");
        fpsScoreText = new BitmapText(defaultFont, false);
        loadText(fpsScoreText, gumballText, defaultFont, 0, 8, 0);
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
        txt.setLocalTranslation(txt.getLineWidth() * x+440, txt.getLineHeight() * y+140, z);
        txt.setColor(ColorRGBA.White);
        txt.setText(text);
        guiNode.attachChild(txt);
    }

    private void initGumballMachine() {
        gumBallMachine1 = assetManager.loadModel("Models/GumballMachine1.j3o");
        gumBallMachine2 = assetManager.loadModel("Models/GumballMachine2.j3o");
        gumBallMachine3 = assetManager.loadModel("Models/GumballMachine3.j3o");
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", new ColorRGBA(0,0,0,0.5f));
        mat1.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        gumBallMachine1.setQueueBucket(Bucket.Transparent); 
        gumBallMachine1.setMaterial(mat1);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Red);
        gumBallMachine2.setMaterial(mat2);
        Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.randomColor());
        gumBallMachine3.setMaterial(mat3);
        
        cam.setLocation(gumBallMachine2.getLocalTranslation().add(0, -1.6f, 10));
        Quaternion rot = new Quaternion(2.7963115E-4f, 0.998722f, -0.05023247f, 0.00556096f);
        cam.setRotation(rot);
    }

    private void initGumballs() {
        Node gumNode = new Node();
        
        Spatial gumBalls = assetManager.loadModel("Models/gumball.j3o");
                
        int noOfGumballsToShow = (totalGumballs>13) ? 13:totalGumballs;
        for(int i=0; i < noOfGumballsToShow;i++) {
            Spatial gmClone = gumBalls.clone();
            gmClone.setLocalTranslation(-3.0f + (i + 0.5f), -5.0f, 0f);
            gumNode.attachChild(gmClone);
        }
        
        gumNode.attachChild(gumBallMachine1);
        gumNode.attachChild(gumBallMachine2);
        gumNode.attachChild(gumBallMachine3);
        rootNode.attachChild(gumNode);
    }
}
