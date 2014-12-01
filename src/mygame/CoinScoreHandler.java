/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.scene.Spatial;

/**
 *
 * @author Rohan
 */
public interface CoinScoreHandler {
    public abstract int handleRequest(Spatial coin);
    public abstract void setNextHandler(CoinScoreHandler handler);
}
