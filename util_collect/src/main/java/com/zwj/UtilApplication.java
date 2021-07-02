package com.zwj;

import com.zwj.common.util.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * @author zwj
 * @date 2020-12-22
 */
@SpringBootApplication
public class UtilApplication {
    public static void main(String[] args) {
        SpringApplication.run(UtilApplication.class, args);
    }

    @Resource
    private RestTemplateBuilder builder;

    @Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }

    /**配置https访问：
     *RestTemplate  中默认字符格式是 ios-8859-1
     * @param httpsFactory
     * @return
     */
    @Bean
    public RestTemplate restTemplate(HttpComponentsClientHttpRequestFactory httpsFactory) {
        RestTemplate restTemplate = new RestTemplate(httpsFactory);
        restTemplate.setErrorHandler(
                new ResponseErrorHandler() {
                    @Override
                    public boolean hasError(ClientHttpResponse clientHttpResponse) {
                        return false;
                    }

                    @Override
                    public void handleError(ClientHttpResponse clientHttpResponse) {
                        // 默认处理非200的返回，会抛异常
                    }
                });
        // 将转换器的编码换成utf-8
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("utf-8")));
        return restTemplate;
    }

    @Bean(name = "httpsFactory")
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory()
            throws Exception {
        CloseableHttpClient httpClient = HttpClientUtils.acceptsUntrustedCertsHttpClient();
        HttpComponentsClientHttpRequestFactory httpsFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        httpsFactory.setReadTimeout(40000);
        httpsFactory.setConnectTimeout(40000);
        return httpsFactory;
    }
}
