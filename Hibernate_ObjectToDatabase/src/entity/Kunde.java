/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author patrick
 */
@Entity
@Table(name = "kunde")
public class Kunde implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "kundeID")
    private int id;
    
    @Column(name = "navn")
    private String navn;
    
    @OneToMany
    @JoinColumn (name = "kundeID")
    @Cascade (CascadeType.SAVE_UPDATE)
    private Set<Arrangement> arrangementer;

    public Kunde() {
        arrangementer = new HashSet();
    }

    public Kunde(String navn) {
        this.navn = navn;
        arrangementer = new HashSet();
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

    public Set<Arrangement> getArrangementer() {
        return arrangementer;
    }

    public void setArrangementer(Set<Arrangement> arrangementer) {
        this.arrangementer = arrangementer;
    }
    
    public void addArrangement(Arrangement a) {
        arrangementer.add(a);
    }
    
    public void removeArrangemtn(Arrangement a) {
        arrangementer.remove(a);
    }
    
}
