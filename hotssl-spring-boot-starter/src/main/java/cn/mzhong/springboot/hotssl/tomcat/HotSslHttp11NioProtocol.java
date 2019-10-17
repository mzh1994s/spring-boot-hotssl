package cn.mzhong.springboot.hotssl.tomcat;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http11.Http11NioProtocol;

/**
 * 使用自定义{@link org.apache.coyote.ProtocolHandler},主要是为了设置{@link AbstractHttp11Protocol#setTrustManagerClassName(String)}}
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/17 17:00
 */
public class HotSslHttp11NioProtocol extends Http11NioProtocol {

    public HotSslHttp11NioProtocol() {
        super.setTrustManagerClassName(HotSslWebServerFactoryCustomizer.HotSslTrustManager.class.getName());
    }
}
