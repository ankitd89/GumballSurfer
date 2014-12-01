/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppState;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 *
 * @author Rohan
 */
public class CollisionDetectionSubject {
    
    private ScoreUpdateObserver scoreUpdtObserver;
    private AppState app;
    private static int score = 0;
    private CoinScoreHandler red  ;
    private CoinScoreHandler blue ;
    private CoinScoreHandler green;

    public int getScore(){
        return score;
    }
    
    CollisionDetectionSubject(AppState app) {
        this.app = app;
        this.scoreUpdtObserver = new ScoreUpdateObserver(this,app);
        red = new RedCoinScoreHandler();
        blue = new BlueCoinScoreHandler();
        green = new GreenCoinScoreHandler();
        
        red.setNextHandler(blue);
        blue.setNextHandler(green);
    }
    
    public void checkCollision(ArrayList<Spatial> coinField, Node player) {
    
        for (int i = 0; i < coinField.size(); i++){
            	
            	Spatial playerModel = (Spatial) player.getChild(0);
                Spatial coin = coinField.get(i);
                coin.updateGeometricState();
                CollisionResults results = new CollisionResults();
                BoundingVolume bv = playerModel.getWorldBound();
                coin.collideWith(bv, results);
                
               

                if (results.size() > 0) {
                    
                    score += red.handleRequest(coin);
                    coinField.get(i).removeFromParent();
                    coinField.remove(coinField.get(i));
                    notifyScoreUpdtObserver();
                    break;
                }
            }
    }
    
    public void notifyScoreUpdtObserver() {
        scoreUpdtObserver.showUpdatedScore();
    }
    
}
