package cn.mzhong.spring.boot.hotssl.tomcat;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

import javax.net.ssl.TrustManager;
import java.io.File;

/**
 * TODO
 *
 * @author mzhong
 * @version 1.0
 * @date 2019/10/16 17:53
 */
public class HotSslTomcatServletWebServerFactory extends TomcatServletWebServerFactory {

    private TrustManager[] trustManagers;

    private int backgroundProcessorDelay;

    private File baseDirectory;

    public HotSslTomcatServletWebServerFactory(TrustManager[] trustManagers) {
        this.trustManagers = trustManagers;
    }

//    public int getBackgroundProcessorDelay() {
//        return backgroundProcessorDelay;
//    }
//
//    @Override
//    public void setBackgroundProcessorDelay(int backgroundProcessorDelay) {
//        this.backgroundProcessorDelay = backgroundProcessorDelay;
//    }
//
//    public File getBaseDirectory() {
//        return baseDirectory;
//    }
//
//    @Override
//    public void setBaseDirectory(File baseDirectory) {
//        this.baseDirectory = baseDirectory;
//    }
//
//    @Override
//    public WebServer getWebServer(ServletContextInitializer... initializers) {
//        Tomcat tomcat = new Tomcat();
//        File baseDir = (this.baseDirectory != null) ? this.baseDirectory : createTempDir("tomcat");
//        tomcat.setBaseDir(baseDir.getAbsolutePath());
//        Connector connector = new HotSslConnector(new HotSslHttp11NioProtocol(trustManagers));
//        tomcat.getService().addConnector(connector);
//        customizeConnector(connector);
//        tomcat.setConnector(connector);
//        tomcat.getHost().setAutoDeploy(false);
//        configureEngine(tomcat.getEngine());
//        for (Connector additionalConnector : getAdditionalTomcatConnectors()) {
//            tomcat.getService().addConnector(additionalConnector);
//        }
//        prepareContext(tomcat.getHost(), initializers);
//        return getTomcatWebServer(tomcat);
//    }
//
//    private void configureEngine(Engine engine) {
//        engine.setBackgroundProcessorDelay(this.backgroundProcessorDelay);
//        for (Valve valve : getEngineValves()) {
//            engine.getPipeline().addValve(valve);
//        }
//    }
}
