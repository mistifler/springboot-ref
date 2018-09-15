package com.mistifler.demo.springbootioc.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 通过反射构造Bean
 * <p>
 * Method相关操作部分的参数，可以通过配置文件或其他方式提供，这样就可以用反射功能来编写一些通用的代码，来完成实例化的功能
 * <p>
 * Created by mistifler on 2018/9/15.
 */
public class ReflectTest {

    public static Person generateBean() throws Exception {
        //通过ClassLoader获取Class对象
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class clazz = loader.loadClass("com.mistifler.demo.springbootioc.reflect.Person");

        //获取类的默认构造函数，并创建实例
        Constructor constructor = clazz.getDeclaredConstructor((Class[]) null);
        Person person = (Person) constructor.newInstance();

        //通过反射获取setter方法设置属性
        Method setName = clazz.getMethod("setName", String.class);
        setName.invoke(person, "xiaoming");
        Method setAge = clazz.getMethod("setAge", Integer.class);
        setAge.invoke(person, 18);

        return person;
    }

    public static void main(String[] args) throws Exception {
        Person person = ReflectTest.generateBean();
        System.out.println(person.toString());
    }
}
