# SpringBoot 实现Https双向认证 + 证书热加载
## 实现原理
使用自定义javax.net.ssl.X509TrustManager,自定义证书验证逻辑，实现从数据库加载证书、文件加载证书、缓存加载证书等。
## 添加maven依赖
```
<dependency>
  <groupId>cn.mzhong</groupId>
  <artifactId>hotssl-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```
## application.yml 添加配置
```
server:
  ssl:
    key-store: classpath:k-server.p12
    key-store-password: jzsy123456
    client-auth: need
    trust-store: classpath:t-server.p12
    trust-store-password: jzsy123456
```
## 将k-server.p12、t-server.p12两个证书库文件添加到classpath目录下
### k-server.p12
服务端证书库，用于将证书发送给浏览器、客户端。浏览器提示证书不受信，就是因为此证书库中的证书是不受信任的机构颁发的（如用Keytool或者openssl生成的自签名证书）
### t-server.p12
服务端受信证书库。服务端信不信任浏览器的证书，就看浏览器发过来的证书是否能在此证书库中找到。
## 代码
使用简单，只需要两个类就可以实现最小证书热加载系统。
### 1、HotSslX509TrustManager.java
```
package cn.mzhong.hotssl.demo;

import cn.mzhong.springboot.hotssl.util.KeyStoreLoader;
import org.springframework.beans.factory.annotation.Value;

import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 测试用的X509TrustManager
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/18 10:49
 */
public class HotSslX509TrustManager implements X509TrustManager {

    @Value("${server.ssl.trust-store}")
    private String trustStore;

    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword = "";


    @Value("${server.ssl.trust-store-type:JKS}")
    private String trustStoreType;

    /**
     * @param x509Certificates 客户端传过来的证书
     * @param s                加密方式
     * @throws CertificateException
     */
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        // 加入自定义证书认证逻辑。认证不同过就抛出CertificateException，否则什么都不做
        // eg： 从数据库、缓存、文件中加载证书，依次判断证书合法性
        KeyStore keyStore = KeyStoreLoader.load(trustStore, trustStorePassword, trustStoreType);
        for (X509Certificate x509Certificate : x509Certificates) {
            try {
                if (keyStore.getCertificateAlias(x509Certificate) != null) {
                    System.out.println("客户端证书验证通过");
                    return;
                }
            } catch (Exception e) {
                // pass
            }
        }
        System.out.println("客户端证书验证不通过");
        throw new CertificateException();
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        // 一般不用管，验证服务端的证书是否都有效
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
```
### 2、DemoApplication.java
```
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
```
## 运行和测试
### 1、启动系统
运行DemoApplication
### 2、访问系统
https://localhost:8080
### 3、测试结果
后台出现：“客户端证书验证不通过”，是因为没有安装证书库。windows系统可将t-server.p12双击打开，并导入客户端证书库，证书密码为jzsy123456。<br>
再访问时，后台应该出现：“客户端证书验证通过”了。