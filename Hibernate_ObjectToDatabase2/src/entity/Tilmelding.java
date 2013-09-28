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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author patrick
 */
@Entity
@Table (name = "tilmelding")
public class Tilmelding implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "tilmeldingID")
    private int id;
    
    @OneToOne
    @JoinColumn (name = "kundeID")
    @Cascade (CascadeType.SAVE_UPDATE)
    private Kunde kunde;
    
    @OneToOne
    @JoinColumn (name = "arrangementID")
    @Cascade (CascadeType.SAVE_UPDATE)
    private Arrangement arrangement;
    
    @Column (name = "antal")
    private int antal;

    public Tilmelding() {
    }

    public Tilmelding(Kunde kunde, Arrangement arrangement, int antal) {
        this.kunde = kunde;
        this.arrangement = arrangement;
        this.antal = antal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Arrangement getArrangement() {
        return arrangement;
    }

    public void setArrangement(Arrangement arrangement) {
        this.arrangement = arrangement;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }
    
    
    
}
