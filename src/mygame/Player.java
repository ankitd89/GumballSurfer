/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Dell
 */
public class Player {
    
   private static float fpsRate = 1000f / 1f; 
    
   public static Node createPlayer(Application app,String playerModel) {
        Spatial playerMesh = app.getAssetManager().loadModel(playerModel);
        Quaternion a = new Quaternion();
        a.fromAngleAxis(FastMath.PI/2, new Vector3f(0, 1, 0));
        playerMesh.setLocalRotation(a);
        Box floor = new Box(Vector3f.ZERO.add(playerMesh.getLocalTranslation().getX(), playerMesh.getLocalTranslation().getY() - 1, 0), 2, 0, 2);
        Geometry floorMesh = new Geometry("Box", floor);
        Material floorMaterial = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        floorMaterial.setColor("Color", ColorRGBA.Yellow);
        floorMesh.setMaterial(floorMaterial);

        floorMesh.setName("floor");
        Node playerNode = new Node();
        playerNode.attachChild(playerMesh);
        playerNode.attachChild(floorMesh);
        return playerNode;
    }

    /**
     * Forcefully takes over Camera adding functionality and placing it behind the character
     * @param tpf Tickes Per Frame
     */
    static float camTakeOver(float tpf, Application app, Node player,float camAngle) {
        app.getCamera().setLocation(player.getLocalTranslation().add(-15, 2, 0));
       // main.getCamera().setLocation(player.getLocalTranslation().add(-6, 2, 0));
        app.getCamera().lookAt(player.getLocalTranslation(), Vector3f.UNIT_Y);
        Quaternion rot = new Quaternion();
        rot.fromAngleNormalAxis(camAngle, Vector3f.UNIT_Z);
        app.getCamera().setRotation(app.getCamera().getRotation().mult(rot));
        camAngle *= FastMath.pow(0.99F, fpsRate * tpf);
        return camAngle;
    }
    
}
