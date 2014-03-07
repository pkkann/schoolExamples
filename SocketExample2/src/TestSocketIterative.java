
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author patrick
 */
public class TestSocketIterative {
    
    public static void main(String[] args) {
        try {
            ServerSocket welcomeSocket = new ServerSocket(6789);
            
            while(true) {
                System.out.println("Waiting");
                Socket connectionSocket = welcomeSocket.accept();
                SocketIterative si = new SocketIterative(connectionSocket);
                si.run();
            }
        } catch (IOException ex) {
            Logger.getLogger(TestSocketIterative.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
