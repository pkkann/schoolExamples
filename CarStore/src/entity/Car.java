package entity;
// Generated 11-09-2013 13:11:20 by Hibernate Tools 3.2.1.GA



/**
 * Car generated by hbm2java
 */
public class Car  implements java.io.Serializable {


     private int id;
     private String model;
     private String color;

    public Car() {
    }

	
    public Car(int id) {
        this.id = id;
    }
    public Car(int id, String model, String color) {
       this.id = id;
       this.model = model;
       this.color = color;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getModel() {
        return this.model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    public String getColor() {
        return this.color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }




}


