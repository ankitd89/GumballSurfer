/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Rohan
 */
public abstract class CoinPrototype extends Node {
 
    static SimpleApplication app;
    static Node player;
    static int difficulty;
    static Spatial fcoin;
    
    public static void initialize(Application app,Node player,int difficulty,Spatial fcoin){
        CoinPrototype.app = (SimpleApplication)app;
        CoinPrototype.player = player;
        CoinPrototype.difficulty = difficulty;
        CoinPrototype.fcoin = fcoin;
    }
    
    public static Spatial createFirstCoin() {
        Spatial coinSpatial = app.getAssetManager().loadModel("Models/coin/coin.j3o");
        
        Material coinMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        coinMat.setColor("Color", ColorRGBA.Blue);
        coinSpatial.setMaterial(coinMat);
        
        fcoin = coinSpatial;
        return coinSpatial;
    }
    
    static Node randomizeCoin(float tpf,ColorRGBA color) {
        Node coin = (Node)fcoin.clone();
        int playerX = (int) player.getLocalTranslation().getX();
         
        int playerZ = (int) player.getLocalTranslation().getZ();
        
        float x = FastMath.nextRandomInt(playerX + difficulty + 30, playerX + difficulty + 90);
        float z = FastMath.nextRandomInt(playerZ - difficulty - 50, playerZ + difficulty + 50);
        coin.getLocalTranslation().set(x, 0, z);
        Material coinMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        coinMat.setColor("Color", color);
        coin.setMaterial(coinMat);
        coin.setUserData("Color", color);
        
        app.getRootNode().attachChild(coin);
        return coin;
    }
    
    public abstract Node clone(float tpf);
}
