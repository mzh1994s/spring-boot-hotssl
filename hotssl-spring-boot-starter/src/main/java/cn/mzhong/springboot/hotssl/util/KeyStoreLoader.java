package cn.mzhong.springboot.hotssl.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.security.KeyStore;

/**
 * KeyStore加载器，主要完成从文件加载
 *
 * @author mzhong
 * @version 1.0
 */
public class KeyStoreLoader {

    private KeyStoreLoader() {
    }

    private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    public static KeyStore load(String name, String password, String type) {
        Resource resource = resourcePatternResolver.getResource(name);
        KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance(type);
            keyStore.load(resource.getInputStream(), password.toCharArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return keyStore;
    }
}
