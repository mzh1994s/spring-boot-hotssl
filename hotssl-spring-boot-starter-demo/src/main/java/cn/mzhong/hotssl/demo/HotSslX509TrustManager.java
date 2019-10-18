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
