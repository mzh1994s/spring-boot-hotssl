package cn.mzhong.springboot.hotssl.tomcat;

import cn.mzhong.springboot.hotssl.util.KeyStoreLoader;
import org.springframework.beans.factory.annotation.Value;

import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 默认从server.ssl.trust-store配置的文件中读取，只是不会缓存，效率不高。<br/>
 * 请自行实现证书管理器以满足从数据库加载，缓存加载等需求。
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/17 10:27
 */
public class DefaultX509TrustManager implements X509TrustManager {

    @Value("${server.ssl.trust-store}")
    private String trustStore;

    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword = "";


    @Value("${server.ssl.trust-store-type:JKS}")
    private String trustStoreType;

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        KeyStore keyStore = KeyStoreLoader.load(trustStore, trustStorePassword, trustStoreType);
        for (X509Certificate x509Certificate : x509Certificates) {
            try {
                if (keyStore.getCertificateAlias(x509Certificate) != null) {
                    return;
                }
            } catch (Exception e) {
                // pass
            }
        }
        throw new CertificateException();
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
