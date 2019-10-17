package cn.mzhong.spring.boot.hotssl.tomcat;

import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.AbstractHttp11JsseProtocol;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.net.NioChannel;
import org.apache.tomcat.util.net.NioEndpoint;

import javax.net.ssl.TrustManager;

/**
 * 自定义{@link ProtocolHandler}实现，解决证书热加载问题
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/16 17:08
 */
public class HotSslHttp11NioProtocol extends AbstractHttp11JsseProtocol<NioChannel> {

    private static final Log log = LogFactory.getLog(org.apache.coyote.http11.Http11NioProtocol.class);

    public HotSslHttp11NioProtocol(TrustManager[] trustManagers) {
        super(new HotSslNioEndpoint(trustManagers));
    }


    @Override
    protected Log getLog() {
        return log;
    }


    // -------------------- Pool setup --------------------

    /**
     * NO-OP.
     *
     * @param count Unused
     */
    public void setPollerThreadCount(int count) {
    }

    public int getPollerThreadCount() {
        return 1;
    }

    public void setSelectorTimeout(long timeout) {
        ((NioEndpoint) getEndpoint()).setSelectorTimeout(timeout);
    }

    public long getSelectorTimeout() {
        return ((NioEndpoint) getEndpoint()).getSelectorTimeout();
    }

    public void setPollerThreadPriority(int threadPriority) {
        ((NioEndpoint) getEndpoint()).setPollerThreadPriority(threadPriority);
    }

    public int getPollerThreadPriority() {
        return ((NioEndpoint) getEndpoint()).getPollerThreadPriority();
    }


    // ----------------------------------------------------- JMX related methods

    @Override
    protected String getNamePrefix() {
        if (isSSLEnabled()) {
            return "https-" + getSslImplementationShortName() + "-nio";
        } else {
            return "http-nio";
        }
    }
}
