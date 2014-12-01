package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.Timer;
import com.jme3.util.SkyFactory;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import mygame.GameController.GameStates;

public class StartScreen extends AbstractAppState {

    private Spatial sceaneModel;
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
    private Camera cam;
    private Timer timer;
    private final ColorRGBA backgroundColor = ColorRGBA.Blue;
    private Node localRootNode = new Node("Gumball field Screen RootNode");
    private Node localGuiNode = new Node("Gumball field Screen GuiNode");
    private Spatial player_girl;
    private Spatial player_boy;
    private Spatial blue_coin, red_coin, green_coin;
    private BitmapText pressStart, red, blue, green, rules, avtar, neytiri, jake;
    private BitmapFont defaultFont, font2;
    private boolean START;
    private String model_boy = "/Models/boy/boy.j3o";
    private String model_girl = "/Models/girl.j3o";

    public StartScreen() {
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
        this.guiNode = this.app.getGuiNode();
        this.cam = this.app.getCamera();
        this.rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Skybox/space.jpg", true));
        Keys();

        defaultFont = assetManager.loadFont("/Interface/Fonts/AppleChancery.fnt");
        font2 = assetManager.loadFont("/Interface/Fonts/AppleChancery1.fnt");
        pressStart = new BitmapText(defaultFont, false);
        avtar = new BitmapText(defaultFont,false);
        neytiri = new BitmapText(defaultFont,false);
        jake = new BitmapText(defaultFont,false);
        
        loadText(pressStart, "GUMBALL SURFER", defaultFont, 0, 8, 0);
        pressStart.setLocalTranslation(pressStart.getLineWidth() * 0 + 500, pressStart.getLineHeight() * 8 + 320, 0);
        
        loadText(avtar, "SELECT YOUR AVATAR", defaultFont, 0, 8, 0);
        avtar.setLocalTranslation(avtar.getLineWidth() * 0 + 480, avtar.getLineHeight() * 12 , 0);
        player_girl = assetManager.loadModel(model_girl);
        player_girl.setLocalTranslation(-4.0f, 0.0f, 0.0f);
        //System.out.println(player_girl.getLocalScale() + " :: GIRL : " + player_girl.getName());
        player_girl.setLocalScale(0.5f, 0.8f, 0.7f);
        player_girl.setName("GIRL");
        rootNode.attachChild(player_girl);
        loadText(neytiri, "NEYTIRI", defaultFont, 0, 8, 0);
        neytiri.setLocalTranslation(neytiri.getLineWidth() * 0 + 155, neytiri.getLineHeight() * 6+24 , 0);

        player_boy = assetManager.loadModel(model_boy);
        player_boy.setLocalTranslation(4.0f, 0.0f, 0.0f);
        //System.out.println(player_boy.getLocalScale() + " :: BOY : " + player_boy.getName());
        player_boy.setLocalScale(0.5f, 0.8f, 0.7f);
        player_boy.setName("BOY");
        rootNode.attachChild(player_boy);
        loadText(jake, "JAKE", defaultFont, 0, 8, 0);
        jake.setLocalTranslation(jake.getLineWidth() * 7 + 250, jake.getLineHeight() * 6+24 , 0);

        green_coin = assetManager.loadModel("/Models/coin/coin.j3o");
        Material mat_green = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_green.setColor("Color", ColorRGBA.Green);
        green_coin.setMaterial(mat_green);
        green_coin.setLocalTranslation(-4.0f, -2.0f, 0.0f);
        green_coin.setLocalScale(0.5f, 0.5f, 0.5f);
        rootNode.attachChild(green_coin);

        red_coin = assetManager.loadModel("/Models/coin/coin.j3o");
        Material mat_red = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_red.setColor("Color", ColorRGBA.Red);
        red_coin.setMaterial(mat_red);
        red_coin.setLocalTranslation(0.0f, -2.0f, 0.0f);
        red_coin.setLocalScale(0.5f, 0.5f, 0.5f);
        rootNode.attachChild(red_coin);

        blue_coin = assetManager.loadModel("/Models/coin/coin.j3o");
        Material mat_blue = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_blue.setColor("Color", ColorRGBA.Blue);
        blue_coin.setMaterial(mat_blue);
        blue_coin.setLocalTranslation(4.0f, -2.0f, 0.0f);
        blue_coin.setLocalScale(0.5f, 0.5f, 0.5f);
        rootNode.attachChild(blue_coin);

        rules = new BitmapText(defaultFont, false);
        loadText(rules, "GAME RULES", defaultFont, 0, 8, 0);
        rules.setLocalTranslation(rules.getLineWidth() * 0 + 530, rules.getLineHeight() * 4+110, 0);
        
        red = new BitmapText(font2, false);
        loadText(red, "DANGER AHEAD! Minus 2 points!", font2, 0, 8, 0);
        red.setLocalTranslation(red.getLineWidth() * 0 + 500, red.getLineHeight() * 8-130, 0);
        //red.setLocalTranslation(0.0f, 0.0f, 0.0f);
        
        blue = new BitmapText(font2, false);
        loadText(blue, "COOL BLUE! 2 points!", font2, 0, 8, 0);
        blue.setLocalTranslation(red.getLineWidth() * 2 + 300, blue.getLineHeight() * 8-130, 0);
        
        green = new BitmapText(font2, false);
        loadText(green, "GO FOR GREEN! 4 points!", font2, 0, 8, 0);
        green.setLocalTranslation(red.getLineWidth() * 0 + 110, green.getLineHeight() * 8-130, 0);
    }

    @Override
    public void update(float tpf) {
        player_girl.rotate(0, 2 * tpf, 0);
        player_boy.rotate(0, 2 * tpf, 0);
        red_coin.rotate(2 * tpf, 0, 0);
        blue_coin.rotate(2 * tpf, 0, 0);
        green_coin.rotate(2 * tpf, 0, 0);
    }

    public void Keys() {
        inputManager.addMapping("STARTGM", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("PICK", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListner, "STARTGM");
        inputManager.addListener(analogListener, "PICK");
    }
    public ActionListener actionListner = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if ("STARTGM".equals(name)) {
                app.changeGameStateTo(GameStates.COIN_FIELD, StartScreen.this);
            }
        }
    };

    private void loadText(BitmapText txt, String text, BitmapFont font, float x, float y, float z) {
        txt.setSize(font.getCharSet().getRenderedSize());
//        txt.setLocalTranslation(txt.getLineWidth() * x+500, txt.getLineHeight() * y+320, z);
        txt.setColor(ColorRGBA.White);
        txt.setText(text);
        guiNode.attachChild(txt);
    }
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) {
            if (name.equals("PICK")) {
                // Reset results list.
                CollisionResults results = new CollisionResults();
                // Convert screen click to 3d position
                Vector2f click2d = inputManager.getCursorPosition();
                Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
                Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
                // Aim the ray from the clicked spot forwards.
                Ray ray = new Ray(click3d, dir);
                // Collect intersections between ray and all nodes in results list.
                rootNode.collideWith(ray, results);
                // (Print the results so we see what is going on:)
//                for (int i = 0; i < results.size(); i++) {
//                    // (For each “hit”, we know distance, impact point, geometry.)
//                    float dist = results.getCollision(i).getDistance();
//                    Vector3f pt = results.getCollision(i).getContactPoint();
//                    String target = results.getCollision(i).getGeometry().getName();
//                    Node node = results.getCollision(i).getGeometry().getParent().getParent();
//                    //String target = results.getCollision(i).get
//                    if (node != null) {
//                        System.out.println("Results:" + results.size() + ": Selection #" + i + ": " + target + "[" + node.getName() + "] at " + pt + ", " + dist + " WU away.");
//                    }
//                }
                // Use the results -- we rotate the selected geometry.
                if (results.size() > 0) {
                    // The closest result is the target that the player picked:
                    Geometry target = results.getClosestCollision().getGeometry();
                    Node node = results.getClosestCollision().getGeometry().getParent().getParent();
                    //String target = results.getCollision(i).get
                    if (node != null) {
                        //System.out.println("Results:"+ results.size() +": ClosestCollision Selection # " + target + "["+node.getName()+"]. ");

                        // Here comes the action:
                        if (node.getName().equals("GIRL")) {
                            //System.out.println(node.getName() + " Selected ");
                            setSelectedPlayerNode(model_girl);
                        } else if (node.getName().equals("BOY")) {
                            //System.out.println(node.getName() + " Selected ");
                            setSelectedPlayerNode(model_boy);
                        }

                        app.changeGameStateTo(GameStates.COIN_FIELD, StartScreen.this);
                    }
                }
            }
        }
    };

    private void setSelectedPlayerNode(String playerModel) {
        Node selectedPlayerNode = new Node("SelectedPlayer");
        selectedPlayerNode.setUserData("SelectedPlayer", playerModel);
        guiNode.attachChild(selectedPlayerNode);
    }

    @Override
    public void cleanup() {
        rootNode.detachAllChildren();

        for (Spatial guiNd : guiNode.getChildren()) {
            if (null != guiNd) {
                if (null != guiNd.getName()) {
                    if (!guiNd.getName().equals("SelectedPlayer")) {
                        guiNode.detachChild(guiNd);
                    }
                } else {
                    guiNode.detachChild(guiNd);
                }
            }
        }
    }

    public void bind(Nifty nifty, Screen screen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onStartScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onEndScreen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}