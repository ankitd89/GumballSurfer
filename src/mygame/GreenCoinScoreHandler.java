/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;

/**
 *
 * @author Rohan
 */
public class GreenCoinScoreHandler implements CoinScoreHandler {
    private CoinScoreHandler nextScoreHandler;
    
    public int handleRequest(Spatial coin) {
       ColorRGBA color = coin.getUserData("Color");
        if(color.equals(ColorRGBA.Green)) {
            return 4;
        } else {
            return 0;
        }
    }

    public void setNextHandler(CoinScoreHandler handler) {
        nextScoreHandler = handler;
    }
}
