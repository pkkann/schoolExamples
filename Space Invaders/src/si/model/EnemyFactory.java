/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.model;

import javax.swing.JPanel;
import si.view.PanelTool;
import si.view.ViewRedEnemy;

/**
 *
 * @author patrick
 */
public class EnemyFactory implements EnemyFactoryIF {
    
    private final JPanel pane;
    
    public EnemyFactory() {
        pane = PanelTool.getInstance();
    }

    @Override
    public EnemyIF makeEnemy(int type) {
        switch(type) {
            case 1:
                ModelRedEnemy mre = new ModelRedEnemy(15, 15, 85, 20, 5);
                ViewRedEnemy vre = new ViewRedEnemy(mre);
                pane.add(vre);
                return mre;
            case 2:
                return null;
            case 3:
                return null;
            default:
                return null;
        }
    }
    
}
