package com.toughchow.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by toughChow
 * 2019-02-27 13:44
 */
public class ReflectionTest {

    public static void main(String[] args) throws Exception {
        refGetClass();

        refGetPublicConstructor();

        refGetPrivateConstructor();

        refGetMethodWithNoArg();

        refGetMethodWithArg();

        refGetField();
    }


    /**
     * 获取类的字段
     * @throws Exception
     */
    private static void refGetField() throws Exception {
        Class clazz = Class.forName("com.toughchow.reflection.Person");
        Constructor constructor = clazz
                .getDeclaredConstructor(new Class[] {String.class});
        // 获取控制权
        constructor.setAccessible(true);
        Person person = (Person) constructor.newInstance(new Object[] {"I am a reflect name!"});

        Field field = clazz.getField("name");
        Object value = field.get(person);
        Class type = field.getType();
        System.out.println(type);

        if(type.equals(String.class)) {
            System.out.println((String) value);
        }
        System.out.println("<--------->");
    }

    /**
     * 获取有参方法 fun
     * @throws Exception
     */
    private static void refGetMethodWithArg() throws Exception {
        Class clazz = Class.forName("com.toughchow.reflection.Person");
        Constructor constructor = clazz.getConstructor(null);
        Person person = (Person) constructor.newInstance(null);

        Method method = clazz.getMethod("fun", new Class[] {String.class});
        method.invoke(person, new Object[] {"I am a felect method"});
        System.out.println("<--------->");
    }

    /**
     * 获取无参方法 fun
     * @throws Exception
     */
    private static void refGetMethodWithNoArg() throws Exception {
        Class clazz = Class.forName("com.toughchow.reflection.Person");
        Constructor constructor = clazz.getConstructor(null);
        Person person = (Person) constructor.newInstance(null);

        Method method = clazz.getMethod("fun", null);
        method.invoke(person, null);
        System.out.println("<--------->");
    }

    /**
     * 获取私有含参构造函数
     * @throws Exception
     */
    private static void refGetPrivateConstructor() throws Exception {
        Class clazz = Class.forName("com.toughchow.reflection.Person");
        Constructor constructor = clazz
                .getDeclaredConstructor(new Class[]{String.class});
        constructor.setAccessible(true);
        Person person = (Person) constructor.newInstance(new Object[] {"I am a reflect name"});
        System.out.println("<--------->");
    }

    /**
     * 获取无参构造函数
     * @throws Exception
     */
    private static void refGetPublicConstructor() throws Exception {
        Class clazz = Class.forName("com.toughchow.reflection.Person");
        Constructor constructor = clazz.getConstructor(null);
        Person person = (Person) constructor.newInstance(null);
        System.out.println("<--------->");
    }

    private static void refGetClass() throws Exception {
        Class clazz = Class.forName("com.toughchow.reflection.Person");
        Class clazz1 = new Person().getClass();
        Class clazz2 = Person.class;
        System.out.println("<--------->");
    }
}
