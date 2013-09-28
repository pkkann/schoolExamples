/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernatearrangementadmv3;

import Entity.Adresse;
import Entity.AnnotadedAdresse;
import Entity.AnnotadedKunde;
import Entity.Kunde;
import Util.HibernateUtil;
import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Kunde kunde = new Kunde(null, "Henrik", "111111111");
        Adresse a = new Adresse(kunde, "test", "4000", "Roskilde");
        kunde.setAdresse(a);
        System.out.println("kunde" + kunde);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Serializable kundeId = session.save(kunde);
        System.out.println("kundeId: " + kundeId);
        tx.commit();
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        kunde = (Kunde) session.get(Kunde.class, kundeId);
        System.out.println("Kunde: " + kunde);
        tx.commit();
        session.close();

        AnnotadedKunde kunde2 = new AnnotadedKunde(null, "Michael", "2222222");
        AnnotadedAdresse a2 = new AnnotadedAdresse(kunde2, "JavaVej", "4000", "JavaBy");
        System.out.println("Adresse: " + a2);
        kunde2.setAdresse(a2);
        System.out.println("kunde" + kunde2);
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        kundeId = session.save(kunde2);
        System.out.println("kundeId: " + kundeId);
        tx.commit();
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        kunde2 = (AnnotadedKunde) session.get(AnnotadedKunde.class, kundeId);
        System.out.println("Kunde: " + kunde2);
        tx.commit();
        session.close();


    }
}
