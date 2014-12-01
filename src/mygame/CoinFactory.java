/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.scene.Node;

/**
 *
 * @author Rohan
 */
public class CoinFactory {
    
    private static final java.util.Map<String , CoinPrototype> prototypes = new java.util.HashMap<String , CoinPrototype>();
 
    static
    {
        prototypes.put("red", new RedCoin());
        prototypes.put("blue", new BlueCoin());
        prototypes.put("green", new GreenCoin());
    }
 
    public static Node getCoinInstance(float tpf) throws CloneNotSupportedException {
        
        int color = (int)(tpf*10000)%3;
        //System.out.println( "tpf "+ tpf +" :: "+ tpf*10000 + " :: "+ (tpf*10000)%3 + " :: "+ color);
        String colorName;
        switch(color) {
            case 0:
                colorName = "red";
                break;
            case 1:
                colorName = "blue";
                break;
            case 2:
                colorName = "green";
                break;
            default:
                colorName = "red";
        }
        
        return prototypes.get(colorName).clone(tpf);
    }
    
}
