
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
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
public class TCPClient implements Runnable {

    @Override
    public void run() {
        Random r = new Random(0);
        try {
            Thread.sleep(r.nextInt(1000));
        } catch (InterruptedException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            System.out.println("CLIENT");
            int port = 6789;  //default
            
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            // To server on local host
            Socket clientSocket = new Socket("127.0.0.1", port);
            // To server on other host with IP-address = 83.92.58.109
            //Socket clientSocket = new Socket("83.92.58.109", port);
            PrintStream outToServer = new PrintStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            for (int i = 0; i < 5; i++) {
                outToServer.println("Besked " + i);
                System.out.println("Besked " + i);
                
                String modifiedSentence = inFromServer.readLine();
                System.out.println("Modtog besked: " + modifiedSentence);
            }


            clientSocket.close();
            System.out.println("færdig");
        } catch (UnknownHostException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
