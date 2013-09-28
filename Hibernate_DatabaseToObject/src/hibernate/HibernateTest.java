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
public class HibernateTest {
    
    public static void main(String[] args) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        
        Kunde k = new Kunde(1, "Diller", "29456789");
        Arrangement a = new Arrangement(1, "duller", "daller", "idag");
        Tilmelding t = new Tilmelding(1, a, k, "idag", "mange");
        
        k.getTilmeldings().add(t);
        a.getTilmeldings().add(t);
        
        s.save(k);
        s.save(a);
        s.save(t);
        tx.commit();
        
        Kunde k1 = (Kunde)s.get(Kunde.class, 1);
        Arrangement a1 = (Arrangement)s.get(Arrangement.class, 1);
        Tilmelding t1 = (Tilmelding)s.get(Tilmelding.class, 1);
        System.out.println(k1.getNavn() + " " + a1.getNavn() + " " + t1.getAntal());
        tx = s.beginTransaction();
        s.delete(t);
        s.delete(a);
        s.delete(k);
        tx.commit();
    }
}
