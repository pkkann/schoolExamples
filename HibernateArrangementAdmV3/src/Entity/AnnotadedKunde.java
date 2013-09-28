package Entity;
// Generated 21-09-2009 17:38:20 by Hibernate Tools 3.2.1.GA

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

@Entity
@Table(name = "kunde")
public class AnnotadedKunde implements java.io.Serializable {

    @Id
    @GeneratedValue (strategy= GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAVN")
    private String navn;
    
    @Column(name = "TLF")
    private String tlf;

    @OneToOne
    @JoinColumn(name = "ADRESSE_ID")
    @Cascade (CascadeType.SAVE_UPDATE)
    private AnnotadedAdresse adresse;

    public AnnotadedKunde() {
    }

    public AnnotadedKunde(AnnotadedAdresse adresse, String navn, String tlf) {
        this.adresse = adresse;
        this.navn = navn;
        this.tlf = tlf;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AnnotadedAdresse getAdresse() {
        return this.adresse;
    }

    public void setAdresse(AnnotadedAdresse adresse) {
        this.adresse = adresse;
        System.out.println("setAdresse: " + adresse);
    }

    public String getNavn() {
        return this.navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getTlf() {
        return this.tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String toString() {
        return " kundeID: " + id + " navn: " + navn + " tlf: " + tlf + " Adresse: " + adresse;
    }
}


