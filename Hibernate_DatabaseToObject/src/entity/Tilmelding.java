package entity;
// Generated 16-09-2013 11:11:04 by Hibernate Tools 3.2.1.GA



/**
 * Tilmelding generated by hbm2java
 */
public class Tilmelding  implements java.io.Serializable {


     private int id;
     private Arrangement arrangement;
     private Kunde kunde;
     private String dato;
     private String antal;

    public Tilmelding() {
    }

	
    public Tilmelding(int id) {
        this.id = id;
    }
    public Tilmelding(int id, Arrangement arrangement, Kunde kunde, String dato, String antal) {
       this.id = id;
       this.arrangement = arrangement;
       this.kunde = kunde;
       this.dato = dato;
       this.antal = antal;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Arrangement getArrangement() {
        return this.arrangement;
    }
    
    public void setArrangement(Arrangement arrangement) {
        this.arrangement = arrangement;
    }
    public Kunde getKunde() {
        return this.kunde;
    }
    
    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }
    public String getDato() {
        return this.dato;
    }
    
    public void setDato(String dato) {
        this.dato = dato;
    }
    public String getAntal() {
        return this.antal;
    }
    
    public void setAntal(String antal) {
        this.antal = antal;
    }




}


