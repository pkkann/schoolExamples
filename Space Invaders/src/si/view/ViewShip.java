/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package si.view;

import si.model.ModelShip;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;

/**
 *
 * @author patrick
 */
public class ViewShip extends JLabel implements Observer{
    
    public ViewShip(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);	//(x,y,højde,bredde)
        this.setOpaque(true);				//så kan selve komponenten også ses
        this.setForeground(Color.white);
        this.setBackground(Color.green);
    }

    @Override
    public void update(Observable o, Object arg) {
        ModelShip t = (ModelShip)o;
        this.setBounds(t.getX(), t.getY(), t.getWidth(), t.getHeight());
    }
    
}
