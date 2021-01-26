package com.imooc.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020-08-26-14:42
 */
@Component
@ConfigurationProperties(prefix = "file")//前缀
@PropertySource("classpath:file-upload-dev.properties")
public class FileUpload {

    private  String imageUserFaceLocation;
    private  String imageServerUrl;

    public String getImageUserFaceLocation() {
        return imageUserFaceLocation;
    }

    public void setImageUserFaceLocation(String imageUserFaceLocation) {
        this.imageUserFaceLocation = imageUserFaceLocation;
    }

    public String getImageServerUrl() {
        return imageServerUrl;
    }

    public void setImageServerUrl(String imageServerUrl) {
        this.imageServerUrl = imageServerUrl;
    }
}
