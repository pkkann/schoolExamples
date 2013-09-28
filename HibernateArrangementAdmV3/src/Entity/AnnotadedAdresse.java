package Entity;
// Generated 21-09-2009 17:38:20 by Hibernate Tools 3.2.1.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "adresse")
public class AnnotadedAdresse implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ADRESSE_ID")
    private Integer id;

    @Column(name = "VEJ")
    private String vej;

    @Column(name = "POSTNUMMER")
    private String postnummer;

    @Column(name = "CITY")
    private String city;

    @OneToOne(mappedBy = "adresse")
    private AnnotadedKunde kunde;

    public AnnotadedAdresse() {
    }

    public AnnotadedAdresse(AnnotadedKunde kunde, String vej, String postnummer, String city) {
        this.kunde = kunde;
        this.vej = vej;
        this.postnummer = postnummer;
        this.city = city;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AnnotadedKunde getKunde() {
        return this.kunde;
    }

    public void setKunde(AnnotadedKunde kunde) {
        this.kunde = kunde;
    }

    public String getVej() {
        return this.vej;
    }

    public void setVej(String vej) {
        this.vej = vej;
    }

    public String getPostnummer() {
        return this.postnummer;
    }

    public void setPostnummer(String postnummer) {
        this.postnummer = postnummer;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String toString() {
        return " adresseId: " + id + " vej: " + vej + " postnummer: " + postnummer + " by: " + city;
    }
}


