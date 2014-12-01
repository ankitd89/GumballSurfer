package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController extends SimpleApplication {

    public static void main(String[] args) {
        GameController app = new GameController();
        app.start();
    }
    
    //private Trigger pause_trigger = new KeyTrigger(KeyInput.KEY_BACK);
    //private Trigger save_trigger = new KeyTrigger(KeyInput.KEY_RETURN);
    //private boolean isRunning = false; // starts at startscreen
    AbstractAppState startScreen;
    AbstractAppState gumballFieldState;
    AbstractAppState timeExpiredState;
    GameStates currentGameState;
    
    AbstractAppState appStateToDetach;
    boolean changeState;
    
    public enum GameStates {
        START,
        COIN_FIELD,
        TIME_EXP
    }
    
    public void changeGameStateTo(GameStates gState,AbstractAppState appStateToDetach) {
        this.changeState = true;
        this.currentGameState = gState;
        
        this.appStateToDetach = appStateToDetach;
    }
    
    
    /**
     * Initializes game
     */
    @Override
    public void simpleInitApp() {
        Logger.getLogger("com.jme3").setLevel(Level.WARNING);
        flyCam.setEnabled(false);
        setDisplayStatView(false);

        //startState = new StartState();
        startScreen = new StartScreen();
        gumballFieldState = new GumballFieldState();
        timeExpiredState = new TimeExpiredState();

        this.changeGameStateTo(GameStates.START,null);

//        inputManager.addMapping("Game Pause Unpause", pause_trigger);
//        inputManager.addListener(actionListener, new String[]{"Game Pause Unpause"});
//        inputManager.addMapping("Toggle Settings", save_trigger);
//        inputManager.addListener(actionListener, new String[]{"Toggle Settings"});
    }
//    private ActionListener actionListener = new ActionListener() {
//        public void onAction(String name, boolean isPressed, float tpf) {
//            System.out.println("key" + name);
//            if (name.equals("Game Pause Unpause") && !isPressed) {
//                if (isRunning) {
//                    stateManager.detach(gameRunningState);
//                    stateManager.attach(startScreenState);
//                    System.out.println("switching to startscreen...");
//
//                } else {
//                    stateManager.detach(startScreenState);
//                    stateManager.attach(gameRunningState);
//                    System.out.println("switching to game...");
//                }
//                isRunning = !isRunning;
//            } else if (name.equals("Toggle Settings") && !isPressed && !isRunning) {
//                if (!isRunning && stateManager.hasState(startScreenState)) {
//                    stateManager.detach(startScreenState);
//                    stateManager.attach(settingsScreenState);
//                    System.out.println("switching to settings...");
//                } else if (!isRunning && stateManager.hasState(settingsScreenState)) {
//                    stateManager.detach(settingsScreenState);
//                    stateManager.attach(startScreenState);
//                    System.out.println("switching to startscreen...");
//                }
//            }
//        }
//    };

    @Override
    public void simpleUpdate(float tpf) {
       if(changeState) { 
        switch(currentGameState){

            case START:
                stateManager.detach(appStateToDetach);
                stateManager.attach(startScreen);
                break;

            case COIN_FIELD:
                stateManager.detach(appStateToDetach);
                stateManager.attach(gumballFieldState);
                break;

            case TIME_EXP:
                stateManager.detach(appStateToDetach);
                stateManager.attach(timeExpiredState);
                break;
        }
        changeState = false;
       }
    }

    @Override
    public void requestClose(boolean esc) {
        if (!esc) {
            System.out.println("The game was quit.");
        }
        context.destroy(false);
    }
}