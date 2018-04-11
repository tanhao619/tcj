package com.stylefeng.guns.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.stylefeng.guns.core.util.ToolUtil.getTempPath;
import static com.stylefeng.guns.core.util.ToolUtil.isEmpty;

/**
 * guns项目配置
 *
 * @author lgg
 * @Date 2017/11/10 16:55
 */
@Component
@ConfigurationProperties(prefix = FileProperties.PREFIX)
public class FileProperties {
    public static final String PREFIX = "default";


    @Value("${default.path}")
    private String path;
    @Value("${default.server}")
    private String serverIp;
    @Value("${default.fileAddress}")
    private String fileAddress;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }
}
