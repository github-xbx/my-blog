package com.xingbingxuan.blog.auth.uitls;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : xbx
 * @date : 2022/6/22 22:34
 */
@Data
@Component
@ConfigurationProperties("application")
public class ApplicationProperties {

    private final Security security = new Security();

    @Data
    public static class Security {
        private Oauth2 oauth2;
        private List<String> excludeUrls = new ArrayList<>();

        @Data
        public static class Oauth2 {
            private String issuerUrl;

        }
    }


}
