package com.example.distributedbookdetails.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/9/3 14:21
 * description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    private Es es = new Es();


    @Getter
    @Setter
    public static class Es {
        private String key;
        private String host;
        private String appId;
    }
}

