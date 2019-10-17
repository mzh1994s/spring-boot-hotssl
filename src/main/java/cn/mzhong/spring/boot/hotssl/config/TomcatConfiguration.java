package cn.mzhong.spring.boot.hotssl.config;

import cn.mzhong.spring.boot.hotssl.tomcat.HotSslTrustManager;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.UpgradeProtocol;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.Servlet;
import java.security.KeyStore;

/**
 * 配置Tomcat，使得TrustManagerClassName可被设置
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/11 11:05
 */
@Configuration
@ConditionalOnClass({Servlet.class, Tomcat.class, UpgradeProtocol.class})
@ConditionalOnMissingBean(value = ServletWebServerFactory.class, search = SearchStrategy.CURRENT)
public class TomcatConfiguration {

    @PostConstruct
    public void init() {
        // 初始化文件受信证书库
        KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HotSslTrustManager.KEY_STORE = keyStore;

        // 从这里装载数据库、文件中的受信证书
        // TODO
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        return null;
    }
}


