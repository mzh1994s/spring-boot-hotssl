package cn.mzhong.springboot.hotssl.tomcat;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * [热加载证书]，热加载{@link TomcatServletWebServerFactory}定制器,为{@link TomcatServletWebServerFactory}定制Protocol
 * 类型为{@link HotSslHttp11NioProtocol}，{@link HotSslHttp11NioProtocol}默认trustManagerClassName为
 * {@link HotSslWebServerFactoryCustomizer.HotSslTrustManager},以此实现客户端受信证书自定义管理，实现热加载证书
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/17 14:32
 */
public class HotSslWebServerFactoryCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    private static X509TrustManager x509TrustManager;

    public HotSslWebServerFactoryCustomizer(X509TrustManager x509TrustManager) {
        HotSslWebServerFactoryCustomizer.x509TrustManager = x509TrustManager;
    }

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.setProtocol(HotSslHttp11NioProtocol.class.getName());
    }

    /**
     * 热加载受信证书管理器
     */
    public static class HotSslTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            x509TrustManager.checkClientTrusted(x509Certificates, s);
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            x509TrustManager.checkServerTrusted(x509Certificates, s);
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return x509TrustManager.getAcceptedIssuers();
        }
    }
}
