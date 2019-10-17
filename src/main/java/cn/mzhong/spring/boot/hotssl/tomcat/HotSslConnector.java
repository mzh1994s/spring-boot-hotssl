package cn.mzhong.spring.boot.hotssl.tomcat;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ProtocolHandler;

/**
 * TODO
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/16 17:41
 */
public class HotSslConnector extends Connector {

    private final ProtocolHandler protocolHandler;

    public HotSslConnector(ProtocolHandler protocolHandler) {
        this.protocolHandler = protocolHandler;
    }

    @Override
    public ProtocolHandler getProtocolHandler() {
        return protocolHandler;
    }
}

