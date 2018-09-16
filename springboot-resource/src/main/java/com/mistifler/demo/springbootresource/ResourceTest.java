package com.mistifler.demo.springbootresource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;

import java.io.*;

/**
 * Resource访问类路径下资源文件示例
 * <p>
 * Created by mistifler on 2018/9/16.
 */
public class ResourceTest {
    public static void main(String[] args) throws Exception{
        //文件系统路径
        String filePath = "D:\\GitHub\\springboot-ref\\springboot-resource\\src\\main\\resources\\test.conf";

        //使用系统文件路径方式访问资源
        PathResource pathResource = new PathResource(filePath);
        OutputStream outputStream = pathResource.getOutputStream();
        outputStream.write("write test info".getBytes());
        outputStream.flush();
        outputStream.close();

        //使用类路径方式访问资源
        ClassPathResource classPathResource = new ClassPathResource("test.conf");
        InputStream inputStream = classPathResource.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        while ((i = inputStream.read()) != -1) {
            byteArrayOutputStream.write(i);
        }
        System.out.println(byteArrayOutputStream.toString());

    }
}
