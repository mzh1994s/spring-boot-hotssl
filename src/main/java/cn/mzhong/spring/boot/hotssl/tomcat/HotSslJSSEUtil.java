package cn.mzhong.spring.boot.hotssl.tomcat;

import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.apache.tomcat.util.net.jsse.JSSEUtil;

import javax.net.ssl.TrustManager;

/**
 * TODO
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/16 17:25
 */
public class HotSslJSSEUtil extends JSSEUtil {

    TrustManager[] trustManagers;

    public HotSslJSSEUtil(TrustManager[] trustManagers, SSLHostConfigCertificate certificate) {
        super(certificate);
        this.trustManagers = trustManagers;
    }

    @Override
    public TrustManager[] getTrustManagers() throws Exception {
        return this.trustManagers;
    }
}
