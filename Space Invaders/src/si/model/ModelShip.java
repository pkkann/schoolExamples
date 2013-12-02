/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.model;

import java.util.Observable;

/**
 *
 * @author patrick
 */
public class ModelShip extends Observable{
    
    private int x = 50;
    private int y = 25;
    private int width = 85;
    private int height = 20;
    private int speed = 10;
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
        this.setChanged();
    }

    public void setY(int y) {
        this.y = y;
        this.setChanged();
    }

    public void setWidth(int width) {
        this.width = width;
        this.setChanged();
    }

    public void setHeight(int height) {
        this.height = height;
        this.setChanged();
    }
    
    public void moveUp() {
        y = y - speed;
        setChanged();
        notifyObservers();
    }
    
    public void moveDown() {
        y = y + speed;
        setChanged();
        notifyObservers();
    }
    
    public void moveRight() {
        x = x + speed;
        setChanged();
        notifyObservers();
    }
    
    public void moveLeft() {
        x = x - speed;
        setChanged();
        notifyObservers();
    }
    
    
}
