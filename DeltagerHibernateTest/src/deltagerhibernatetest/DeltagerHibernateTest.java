/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deltagerhibernatetest;

import Entity.Deltager;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author henrikh
 */
public class DeltagerHibernateTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Deltager d = new Deltager("kringle", "BullerFis@easj.dk");
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        
        s.save(d);
        tx.commit();
        
        d = (Deltager) s.get(Deltager.class, 2);
        System.out.println(d.getId());
        System.out.println(d.getNavn());
        System.out.println(d.getEmail());
        
        s.close();
    }
    
}
