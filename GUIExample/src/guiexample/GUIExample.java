/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guiexample;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Patrick
 */
public class GUIExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Test");
        frame.setLayout(new BorderLayout());
        
        final JPanel panel1 = new JPanel();
        final JPanel panel2 = new JPanel();
        
        JButton b = new JButton("Test");
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(panel1);
                frame.add(panel2);
                frame.pack();
            }
        });
        panel1.add(b);
        
        JLabel l = new JLabel("YAY");
        panel2.add(l);
        
        frame.add(panel1);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        
    }
    
}
