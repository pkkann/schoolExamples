package entity;
// Generated 16-09-2013 11:11:04 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Kunde generated by hbm2java
 */
public class Kunde  implements java.io.Serializable {


     private int kundeId;
     private String navn;
     private String tlf;
     private Set tilmeldings = new HashSet(0);

    public Kunde() {
    }

	
    public Kunde(int kundeId) {
        this.kundeId = kundeId;
    }
    public Kunde(int kundeId, String navn, String tlf, Set tilmeldings) {
       this.kundeId = kundeId;
       this.navn = navn;
       this.tlf = tlf;
       this.tilmeldings = tilmeldings;
    }
    public Kunde(int kundeId, String navn, String tlf) {
       this.kundeId = kundeId;
       this.navn = navn;
       this.tlf = tlf;
    }
   
    public int getKundeId() {
        return this.kundeId;
    }
    
    public void setKundeId(int kundeId) {
        this.kundeId = kundeId;
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
    public Set getTilmeldings() {
        return this.tilmeldings;
    }
    
    public void setTilmeldings(Set tilmeldings) {
        this.tilmeldings = tilmeldings;
    }




}


