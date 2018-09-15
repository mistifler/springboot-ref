package com.mistifler.demo.springbootioc.iocthinking;

/**
 * 模拟容器思想
 * Created by mistifler on 2018/9/15.
 */
public class MyContainer {
    public MyContainer() {

        Car car = new BlueCar();

        //构造函数注入
        User user1 = new User(car);

        //属性注入
        User user2 = new User();
        user2.setCar(car);

    }
}
