/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patrick
 */
public class Person {

    private String name;
    private char sex;

    public Person(String name, char sex) {
        setName(name);
        setSex(sex);
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.name = name;
        }
    }

    public void setSex(char sex) {
        if (sex == 'M' || sex == 'F') {
            this.sex = sex;
        } else {
            throw new IllegalArgumentException();
        }
    }

}
