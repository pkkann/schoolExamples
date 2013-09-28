
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author patrick
 */
public class TestSocketConcurrent {

    public static void main(String[] args) throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        System.out.println("Server started");
        while (true) {
            
            Socket connection = welcomeSocket.accept();
            ServiceEcho se = new ServiceEcho(connection);
            Thread myThread = new Thread(se);
            myThread.start();
            System.out.println("Got a connection");
        }
    }
}
