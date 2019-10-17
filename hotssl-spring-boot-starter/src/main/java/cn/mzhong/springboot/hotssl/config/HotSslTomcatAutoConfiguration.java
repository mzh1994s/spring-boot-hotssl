package cn.mzhong.springboot.hotssl.config;

import cn.mzhong.springboot.hotssl.tomcat.DefaultX509TrustManager;
import cn.mzhong.springboot.hotssl.tomcat.HotSslWebServerFactoryCustomizer;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.UpgradeProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.X509TrustManager;
import javax.servlet.Servlet;

/**
 * 配置Tomcat，使得TrustManagerClassName可被设置
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/11 11:05
 */
@Configuration
@ConditionalOnClass({Servlet.class, Tomcat.class, UpgradeProtocol.class})
public class HotSslTomcatAutoConfiguration {

    @Autowired
    private X509TrustManager x509TrustManager;

    @Bean
    public HotSslWebServerFactoryCustomizer hotSslWebServerFactoryCustomizer() {
        return new HotSslWebServerFactoryCustomizer(x509TrustManager);
    }

    @Bean
    @ConditionalOnMissingBean
    public X509TrustManager x509TrustManager() {
        return new DefaultX509TrustManager();
    }
}


