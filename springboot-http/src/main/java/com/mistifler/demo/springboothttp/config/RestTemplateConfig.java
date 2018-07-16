package com.mistifler.demo.springboothttp.config;


import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
public class RestTemplateConfig {

    @Bean("defaultRestTemplate")
    public RestTemplate defaultRestTemplate() {
        return new RestTemplate();
    }

    private static final int CONNECT_TIMEOUT = 5 * 1000;
    private static final int READ_TIMEOUT = 5 * 1000;
    private static final int CONN_REQUEST_TIMEOUT = 2 * 1000;
    private static final int POOL_MAX_TOTAL = 100;
    private static final int POOL_MAX_PER_ROUTE = 10;
    private static final String PROXY_ADDRESS = "http://xx.proxy.xx";
    private static final int PROXY_PORT = 8888;

    @Bean("restTemplate")
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        //连接超时5s
        httpRequestFactory.setConnectTimeout(CONNECT_TIMEOUT);
        // 数据读取超时时间，即Socket Read Timeout
        httpRequestFactory.setReadTimeout(READ_TIMEOUT);
        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的，HttpClient默认-1
        httpRequestFactory.setConnectionRequestTimeout(CONN_REQUEST_TIMEOUT);
        // 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
        httpRequestFactory.setBufferRequestBody(false);

        return new RestTemplate(httpRequestFactory);
    }


    @Bean(name = "clientRestTemplate")
    public RestTemplate crawlerRestTemplate() {

        //连接池相关配置
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(POOL_MAX_TOTAL);
        //该参数会对连接池按请求的host进行细分，默认为2。
        connectionManager.setDefaultMaxPerRoute(POOL_MAX_PER_ROUTE);

        //request超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(READ_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONN_REQUEST_TIMEOUT)
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


    @Bean("proxyRestTemplate")
    public RestTemplate proxyRestTemplate() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(POOL_MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(POOL_MAX_PER_ROUTE);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(READ_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONN_REQUEST_TIMEOUT)
                .build();

        HttpClient proxyClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .disableCookieManagement()
                .setProxy(new HttpHost(PROXY_ADDRESS, PROXY_PORT))
                .build();

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(proxyClient));
    }

@Bean(name = "HttpsRestTemplate")
public RestTemplate httpsRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
            .loadTrustMaterial(null, acceptingTrustStrategy)
            .build();

    //关闭域名验证
    // SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

    CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLSocketFactory(csf)
            .build();

    HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory();

    requestFactory.setHttpClient(httpClient);
    RestTemplate restTemplate = new RestTemplate(requestFactory);
    return restTemplate;
}
}
