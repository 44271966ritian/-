package org.example.hsp;

public class TargerObject {

    private String value;

    public TargerObject(){
        value = "JavaGuide";
    }

    public void publicMethod(String s){
        System.out.println("I love "+s);
    }

    private void privateMethod(){
        System.out.println("value is "+value);
    }

}
