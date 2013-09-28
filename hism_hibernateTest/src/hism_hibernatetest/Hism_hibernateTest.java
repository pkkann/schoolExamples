/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hism_hibernatetest;

import entity.Guest;
import entity.Person;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.NewHibernateUtil;

/**
 *
 * @author patrick
 */
public class Hism_hibernateTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        Transaction ts = s.beginTransaction();
        
        Person p = new Person("Patrick", "", "Kann", "8-56", "21041989", "26092013", "", "", "", 0, 0, 0, "", 0, null);
        Guest g = new Guest(p, "Lars", "", "Kann", "21041989", "");
        Set guests = new HashSet();
        guests.add(g);
        p.setGuests(guests);
        s.save(p);
        ts.commit();
    }
}
