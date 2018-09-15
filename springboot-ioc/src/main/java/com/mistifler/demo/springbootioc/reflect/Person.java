package com.mistifler.demo.springbootioc.reflect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by mistifler on 2018/9/15.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String name;
    private Integer age;

    public String ok() {
        return "ok";
    }
}
