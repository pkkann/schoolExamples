/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.model;

import si.model.EnemyIF;

/**
 *
 * @author patrick
 */
public interface EnemyFactoryIF {
    
    public EnemyIF makeEnemy(int type);
    
}
