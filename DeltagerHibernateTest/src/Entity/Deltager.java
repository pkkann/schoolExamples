package Entity;
// Generated Sep 10, 2013 12:26:05 PM by Hibernate Tools 3.6.0



/**
 * Deltager generated by hbm2java
 */
public class Deltager  implements java.io.Serializable {


     private Integer id;
     private String navn;
     private String email;

    public Deltager() {
    }

    public Deltager(String navn, String email) {
       this.navn = navn;
       this.email = email;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNavn() {
        return this.navn;
    }
    
    public void setNavn(String navn) {
        this.navn = navn;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }




}


