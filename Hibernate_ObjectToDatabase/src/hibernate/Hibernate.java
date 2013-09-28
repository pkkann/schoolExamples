/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import entity.Arrangement;
import entity.Kunde;
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
        
        Kunde k = new Kunde("TestKunde");
        Arrangement a = new Arrangement("test", "test", "test");
        k.addArrangement(a);
        Arrangement a2 = new Arrangement("test2", "test2", "test");
        k.addArrangement(a2);
        
        s.save(k);
        
        Arrangement a3 = new Arrangement("test3", "test3", "test3");
        s.save(a3);
        tx.commit();
        
        k = null;
        a = null;
        a2 = null;
        a3 = null;
        
        k = (Kunde) s.get(Kunde.class, 1);
        System.out.println(k.getNavn());
        System.out.println(k.getArrangementer().size());
        
    }
    
}
