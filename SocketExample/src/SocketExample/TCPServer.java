/*
 * TCPServer.java
 *
 * Created on 22. februar 2008, 12:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package SocketExample;

import java.io.*;
import java.net.*;

class TCPServer {

    public static void main(String argv[]) throws Exception {
        System.out.println("SERVER");
        ServerSocket welcomeSocket = new ServerSocket(6789);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            PrintStream outToClient = new PrintStream(connectionSocket.getOutputStream());
            String clientSentence = inFromClient.readLine();
            System.out.println("FROM CLIENT: " + clientSentence);
            String capitalizedSentence = clientSentence.toUpperCase();
            outToClient.println(capitalizedSentence);
        }
    }
}
