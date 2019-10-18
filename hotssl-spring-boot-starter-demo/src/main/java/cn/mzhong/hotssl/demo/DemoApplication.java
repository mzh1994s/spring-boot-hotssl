package cn.mzhong.hotssl.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.X509TrustManager;

/**
 * 使用例子
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/17 11:11
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * 将自定义X509TrustManager注册为Bean，系统会自动配置，实现证书热加载
     *
     * @return
     */
    @Bean
    public X509TrustManager x509TrustManager() {
        return new HotSslX509TrustManager();
    }
}
