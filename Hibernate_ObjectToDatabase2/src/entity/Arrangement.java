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
@Table (name = "arrangement")
public class Arrangement implements Serializable {
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "arrangementID")
    private int id;
    
    @Column (name = "navn")
    private String navn;
    
    @Column (name = "beskrivelse")
    private String beskrivelse;
    
    @Column (name = "dato")
    private String dato;

    public Arrangement() {
    }

    public Arrangement(String navn, String beskrivelse, String dato) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.dato = dato;
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

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
    
    
    
}
