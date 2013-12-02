/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si.control;

import si.control.ControlShip;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author patrick
 */
public class KeyboardHandler extends KeyAdapter {
    
    private ControlShip controlShip;

    public KeyboardHandler(ControlShip controlShip) {
        this.controlShip = controlShip;
    }

    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                controlShip.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                controlShip.moveDown();
                break;
            case KeyEvent.VK_LEFT:
                controlShip.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                controlShip.moveRight();
                break;
        }
    }

}
