package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "arrangement")
public class Arrangement implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "arrangementID")
    private int arrangementId;
    
    @Column(name = "navn")
    private String navn;
    
    @Column(name = "beskrivelse")
    private String beskrivelse;
    
    @Column(name = "dato")
    private String dato;

    public Arrangement() {
    }

    public Arrangement(String navn, String beskrivelse, String dato) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.dato = dato;
    }

    public int getArrangementId() {
        return this.arrangementId;
    }

    public void setArrangementId(int arrangementId) {
        this.arrangementId = arrangementId;
    }

    public String getNavn() {
        return this.navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getBeskrivelse() {
        return this.beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getDato() {
        return this.dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
}
