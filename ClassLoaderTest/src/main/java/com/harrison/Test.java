package com.harrison;


import com.harrison.classLoader.MyClassLoader;

public class Test {

    public static void main(String[] args) throws Exception{

    Application.run(MyClassLoader.class);

    }
}
