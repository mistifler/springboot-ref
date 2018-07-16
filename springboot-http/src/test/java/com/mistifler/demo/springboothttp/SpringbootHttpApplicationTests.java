package com.mistifler.demo.springboothttp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootHttpApplicationTests {

    private static final String TEST_URL = "https://gturnquist-quoters.cfapps.io/api/random";

    @Autowired
    @Qualifier("clientRestTemplate")
    RestTemplate restTemplate;

    @Test
    public void contextLoads() {
        ResponseEntity<String> ret = restTemplate.getForEntity(TEST_URL, String.class);
        System.out.println(ret.getBody());
    }

}
