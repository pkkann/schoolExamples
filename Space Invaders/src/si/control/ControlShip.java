/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.control;

import si.model.ModelShip;

/**
 *
 * @author patrick
 */
public class ControlShip {
    
    private ModelShip modelShip;
    private int speed = 10;
    
    public ControlShip(ModelShip modelShip) {
        this.modelShip = modelShip;
    }
    
    public void moveUp() {
        modelShip.moveUp();
    }
    
    public void moveDown() {
        modelShip.moveDown();
    }
    
    public void moveRight() {
        modelShip.moveRight();
    }
    
    public void moveLeft() {
        modelShip.moveLeft();
    }
    
    
    
}
