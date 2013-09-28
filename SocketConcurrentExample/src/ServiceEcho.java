
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
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
public class ServiceEcho implements Runnable {

    private Socket connectionSocket;

    public ServiceEcho(Socket connection) {
        this.connectionSocket = connection;
    }

    @Override
    public void run() {
        System.out.println("Connected");
        PrintStream outToClient = null;
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            outToClient = new PrintStream(connectionSocket.getOutputStream());
            String clientSentence = inFromClient.readLine();

            while (clientSentence != null) {
                System.out.println("FROM CLIENT: " + clientSentence);
                String capitalizedSentence = clientSentence.toUpperCase();
                outToClient.println(capitalizedSentence);
                clientSentence = inFromClient.readLine();
            }


        } catch (IOException ex) {
            Logger.getLogger(ServiceEcho.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            outToClient.close();
        }
    }
}
