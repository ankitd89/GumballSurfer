/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppState;

/**
 *
 * @author Rohan
 */
public class ScoreUpdateObserver {

    AppState app;
    private CollisionDetectionSubject subject;
    
    ScoreUpdateObserver(CollisionDetectionSubject collisionDtctSubject,AppState app) {
        this.subject = collisionDtctSubject;
        this.app = app;
    }
    
    void showUpdatedScore() {
        ((GumballFieldState)app).setScoreText(subject.getScore());
    }
    
}
