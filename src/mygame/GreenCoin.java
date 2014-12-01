/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

/**
 *
 * @author Rohan
 */
public class GreenCoin extends CoinPrototype {

    @Override
    public Node clone(float tpf) {
        return CoinPrototype.randomizeCoin(tpf, ColorRGBA.Green);
    }
}