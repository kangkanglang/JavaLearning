package com.harrison;

public class User {

    public void sayHello(){
        System.out.println("hello , this is user.");
        System.out.println("user's classLoader is : " + getClass().getClassLoader());
    }




}
