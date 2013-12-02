/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.view;

import javax.swing.JPanel;

/**
 *
 * @author patrick
 */
public class PanelTool{
    
    private static JPanel instance = null;
    
    private PanelTool() {}
    
    public static JPanel getInstance() {
        if(instance == null) {
            constructInstance();
            return instance;
        } else {
            return instance;
        }
    }
    
    private static void constructInstance() {
        instance = new JPanel();
    }
    
}
