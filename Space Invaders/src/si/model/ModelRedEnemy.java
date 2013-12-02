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
public class ModelRedEnemy extends Observable implements EnemyIF {
    
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    private int speed = 5;
    
    public ModelRedEnemy(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        setChanged();
        notifyObservers();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        setChanged();
        notifyObservers();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        setChanged();
        notifyObservers();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        setChanged();
        notifyObservers();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        setChanged();
        notifyObservers();
    }
    
    

    @Override
    public void moveUp() {
        y -= speed;
        setChanged();
        notifyObservers();
    }

    @Override
    public void moveDown() {
        y += speed;
        setChanged();
        notifyObservers();
    }

    @Override
    public void moveLeft() {
        x -= speed;
        setChanged();
        notifyObservers();
    }

    @Override
    public void moveRight() {
        x += speed;
        setChanged();
        notifyObservers();
    }
    
}
