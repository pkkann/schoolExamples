/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.control;

import java.util.ArrayList;
import si.model.EnemyFactoryIF;
import si.model.EnemyIF;

/**
 *
 * @author patrick
 */
public class ControlRedEnemy {
    
    private ArrayList<EnemyIF> enemies;
    private EnemyFactoryIF enemyFactory;
    
    public ControlRedEnemy(EnemyFactoryIF enemyFactory) {
        enemies = new ArrayList<>();
        this.enemyFactory = enemyFactory;
    }
    
    public void createEnemy() {
        EnemyIF enemy = enemyFactory.makeEnemy(1);
        enemies.add(enemy);
    }
    
    public void moveUp() {
        for(EnemyIF e : enemies) {
            e.moveUp();
        }
    }
    
    public void moveDown() {
        for(EnemyIF e : enemies) {
            e.moveDown();
        }
    }
    
    public void moveRight() {
        for(EnemyIF e : enemies) {
            e.moveRight();
        }
    }
    
    public void moveLeft() {
        for(EnemyIF e : enemies) {
            e.moveLeft();
        }
    }
    
    
}
