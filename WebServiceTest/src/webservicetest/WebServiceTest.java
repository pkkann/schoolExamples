/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicetest;

/**
 *
 * @author patrick
 */
public class WebServiceTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(lort());
    }

    private static String lort() {
        webservicetest.NewWebService_Service service = new webservicetest.NewWebService_Service();
        webservicetest.NewWebService port = service.getNewWebServicePort();
        return port.lort();
    }
}
