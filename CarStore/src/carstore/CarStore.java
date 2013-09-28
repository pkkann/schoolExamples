/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carstore;

import entity.Car;
import hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author patrick
 */
public class CarStore {
    
    public CarStore() {
        
    }
    
    public static void main(String[] args) {
        Car c = new Car(1, "diller", "Rød");
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        
        s.save(c);
        tx.commit();
        
        s.close();
    }
    
}
