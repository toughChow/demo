package com.toughchow.reflection;

/**
 * Created by toughChow
 * 2019-02-27 13:40
 */
public class Person {

    public String name = "default name";
    public int[] array = new int[10];

    public Person() {
        System.out.println(name);
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
    }

    private Person(String name) {
        this.name = name;
        System.out.println(name);
    }

    public void fun() {
        System.out.println("fun");
    }

    public void fun(String name) {
        System.out.println("fun" + name);
    }

}
