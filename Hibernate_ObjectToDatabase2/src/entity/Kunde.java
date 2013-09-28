/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author patrick
 */
@Entity
@Table (name = "kunde")
public class Kunde implements Serializable {
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "kundeID")
    private int id;
    
    @Column (name = "navn")
    private String navn;

    public Kunde() {
    }

    public Kunde(String navn) {
        this.navn = navn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }
    
    
}
