/*
 * TCPClient.java
 *
 * Created on 22. februar 2008, 12:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package SocketExample;

import java.io.*;

import java.net.*;

public class TCPClient {

    public static void main(String argv[]) throws Exception {
        System.out.println("CLIENT");
        int port = 6789;  //default
        if (argv.length > 0) {
            port = Integer.parseInt(argv[0]);
        }
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        // To server on local host
        Socket clientSocket = new Socket("127.0.0.1", port);
        // To server on other host with IP-address = 83.92.58.109
        //Socket clientSocket = new Socket("83.92.58.109", port);
        PrintStream outToServer = new PrintStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String sentence = inFromUser.readLine();
        outToServer.println(sentence);
        String modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);
        clientSocket.close();
        System.out.println("færdig");
    }
}
