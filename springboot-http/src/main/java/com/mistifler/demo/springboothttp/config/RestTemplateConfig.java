package com.mistifler.demo.springboothttp.config;


import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private static final int LONG_TIMEOUT = 25 * 1000;

    private static final int REQUEST_TIMEOUT = 30 * 1000;

    private static final int MAX_TOTAL = 100;

    private static final int MAX_PER_ROUTE = 10;

    private static final int DEFAULT_CONN_REQUEST_TIMEOUT = 10000;

    private static final int DEFAULT_TIMEOUT = 10 * 1000;

    @Bean(name = "longtimeRestTemplate")
    public RestTemplate crawlerRestTemplate() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();

        //连接池相关配置
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(MAX_TOTAL);
        //该参数会对连接池按请求的host进行细分，默认为2
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);

        //request超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(LONG_TIMEOUT)
                .setConnectTimeout(LONG_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .build();

        //HttpClient配置, disableCookieManagement拒绝自动管理cookie
        HttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .disableCookieManagement()
                .build();

        //HttpComponentsClientHttpRequestFactory有一个RequestConfig和HttpClient,最好单独设置，否则RequestConfig可能会覆盖HttpClient的配置
        HttpComponentsClientHttpRequestFactory httpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(httpRequestFactory);
    }


    @Bean(name = "defaultRestTemplate")
    public RestTemplate defaultRestTemplate() {

        //HttpComponentsClientHttpRequestFactory只设置RequestConfig会生成一个默认的HttpClient,SimpleClientHttpRequestFactory非连接池
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();

        // 连接超时10s，HttpClient默认-1
        httpRequestFactory.setConnectTimeout(DEFAULT_TIMEOUT);
        // 数据读取超时时间，即SocketTimeout，HttpClient默认-1
        httpRequestFactory.setReadTimeout(DEFAULT_TIMEOUT);
        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的，HttpClient默认-1
        httpRequestFactory.setConnectionRequestTimeout(DEFAULT_CONN_REQUEST_TIMEOUT);
        // 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
        // clientHttpRequestFactory.setBufferRequestBody(false);

        return new RestTemplate(httpRequestFactory);
    }

    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
    }


}
