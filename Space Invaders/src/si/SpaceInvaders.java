/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si;

import si.control.KeyboardHandler;
import si.control.ControlShip;
import si.view.ViewShip;
import si.model.ModelShip;
import javax.swing.JFrame;
import javax.swing.JPanel;
import si.view.PanelTool;

/**
 *
 * @author patrick
 */
public class SpaceInvaders {
    
    private JFrame frame;
    private JPanel panel;
    
    private ModelShip modelShip;
    private ViewShip viewShip;
    private ControlShip controlShip;
    
    public SpaceInvaders() {
        modelShip = new ModelShip();
        viewShip = new ViewShip(modelShip.getX(), modelShip.getY(), modelShip.getWidth(), modelShip.getHeight());
        controlShip = new ControlShip(modelShip);
        frame = new JFrame("Space Invaders");
    }
    
    private void makeFrame() {
        panel = PanelTool.getInstance();
        
        modelShip.addObserver(viewShip);
        
        panel.add(viewShip);
        panel.setLayout(null);
        panel.addKeyListener(new KeyboardHandler(controlShip));
        
        frame.setSize(800, 600);
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        panel.requestFocus();
    }

    public static void main(String[] args) {
        SpaceInvaders si = new SpaceInvaders();
        si.makeFrame();
    }

}
