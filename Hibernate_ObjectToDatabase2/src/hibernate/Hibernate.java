/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import entity.Arrangement;
import entity.Kunde;
import entity.Tilmelding;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author patrick
 */
public class Hibernate {
    
    public static void main(String[] args) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        
        Arrangement a = new Arrangement("A1", "A1", "A1");
        Kunde k = new Kunde("K1");
        Tilmelding t = new Tilmelding(k, a, 3);
        s.save(t);
        tx.commit();
    }
    
}
