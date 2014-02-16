/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iteratorexample;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Patrick
 */
public class IteratorExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("lort");
        strings.add("tis");
        
        for(String s : strings){
            System.out.println(s);
        }
        
        
        Iterator<String> iterator = strings.iterator();
        
        while(iterator.hasNext()) {
            String s = iterator.next();
            if(s.equals("lort")) {
                iterator.remove();
            }
        }
        
        for(String s : strings){
            System.out.println(s);
        }
        
    }
    
}
