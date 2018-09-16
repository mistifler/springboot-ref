package com.mistifler.demo.springbootresource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by mistifler on 2018/9/16.
 */
public class MessageUtil {


        private static Map<String, String> messages = new HashMap<>();

        static {
            Resource resource = new ClassPathResource("messages");

            try {
                InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream(), "UTF-8");
                ResourceBundle resources = new PropertyResourceBundle(inputStreamReader);
                Enumeration<String> keys = resources.getKeys();
                while (keys.hasMoreElements()) {
                    String key = keys.nextElement();
                    messages.put(key, resources.getString(key));
                }
            } catch (IOException e) {
                System.out.println("fail to init status");
                e.printStackTrace();
            }
        }

        public static String getMessage(String code) {
            return messages.getOrDefault(code, code);
        }
}
