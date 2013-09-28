/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author patrick
 */
public class TestClient {
    
    Thread t1;
    Thread t2;
    Thread t3;
    
    public void start() {
        t1 = new Thread(new TCPClient());
        t2 = new Thread(new TCPClient());
        t3 = new Thread(new TCPClient());
        
        t1.start();
        t2.start();
        t3.start();
    }
    
    public static void main(String[] args) {
        TestClient tc = new TestClient();
        tc.start();
    }
    
}
