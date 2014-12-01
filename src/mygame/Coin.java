/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class Coin {

    public static Spatial createFirstCoin(SimpleApplication app) {
        Spatial coinSpatial = app.getAssetManager().loadModel("Models/coin/coin.j3o");
        return coinSpatial;
    }
    
    /**
     * Randomly Places a cube on the map between 30 and 90 paces away from player
     */
    static Node randomizeCoin(float tpf,SimpleApplication app,Node player,int difficulty,Spatial fcoin) {
        Node coin = (Node)fcoin.clone();
        int playerX = (int) player.getLocalTranslation().getX();
        int playerZ = (int) player.getLocalTranslation().getZ();
        float x = FastMath.nextRandomInt(playerX + difficulty + 30, playerX + difficulty + 90);
        float z = FastMath.nextRandomInt(playerZ - difficulty - 50, playerZ + difficulty + 50);
        coin.getLocalTranslation().set(x, 0, z);
        Material coinMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        coinMat.setColor("Color", ColorRGBA.Blue);
        coin.setMaterial(coinMat);
        app.getRootNode().attachChild(coin);
        return coin;
    }
    
    static void removePastCoins(ArrayList<Spatial> coinField,Node player) {
         for (int i = 0; i < coinField.size(); i++){
        //Remove cube if 10 world units behind player
        if (coinField.get(i).getLocalTranslation().getX() + 100 < player.getLocalTranslation().getX()){
            coinField.get(i).removeFromParent();
            coinField.remove(coinField.get(i));
        }
        }
    }
}
