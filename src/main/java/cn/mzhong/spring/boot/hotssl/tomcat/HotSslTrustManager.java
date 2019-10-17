package cn.mzhong.spring.boot.hotssl.tomcat;

import sun.security.x509.X509CertImpl;

import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * TODO
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/16 17:54
 */
public class HotSslTrustManager implements X509TrustManager {

    public static KeyStore KEY_STORE;

    /**
     * 使用此方法更新证书
     *
     * @param alias     证书别名
     * @param publicKey 证书文件byte[]
     * @throws CertificateException
     * @throws KeyStoreException
     */
    public static void updateKeyStore(String alias, byte[] publicKey) throws CertificateException, KeyStoreException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509CertImpl x509Cert = new X509CertImpl(certificateFactory.generateCertificate(new ByteArrayInputStream(publicKey)).getEncoded());
        KEY_STORE.setCertificateEntry(alias, x509Cert);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        for (X509Certificate certificate : x509Certificates) {
            try {
                String alis = KEY_STORE.getCertificateAlias(certificate);
                if (alis != null) {
                    return;
                }
            } catch (KeyStoreException e) {
                throw new CertificateException(e);
            }
        }
        throw new CertificateException("证书错误！");
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

}
