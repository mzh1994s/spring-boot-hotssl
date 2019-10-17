package cn.mzhong.spring.boot.hotssl.tomcat;

import org.apache.tomcat.util.net.*;

import javax.net.ssl.TrustManager;

/**
 * 带证书热加载的NioEndpoint
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/16 17:18
 */
public class HotSslNioEndpoint extends NioEndpoint {

    private TrustManager[] trustManagers;

    public HotSslNioEndpoint(TrustManager[] trustManagers) {
        this.trustManagers = trustManagers;
    }

    @Override
    protected void createSSLContext(SSLHostConfig sslHostConfig) throws IllegalArgumentException {
        boolean firstCertificate = true;
        for (SSLHostConfigCertificate certificate : sslHostConfig.getCertificates(true)) {
            SSLUtil sslUtil = new HotSslJSSEUtil(trustManagers, certificate);
            if (firstCertificate) {
                firstCertificate = false;
                sslHostConfig.setEnabledProtocols(sslUtil.getEnabledProtocols());
                sslHostConfig.setEnabledCiphers(sslUtil.getEnabledCiphers());
            }

            SSLContext sslContext;
            try {
                sslContext = sslUtil.createSSLContext(negotiableProtocols);
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }

            certificate.setSslContext(sslContext);
        }
    }
}
