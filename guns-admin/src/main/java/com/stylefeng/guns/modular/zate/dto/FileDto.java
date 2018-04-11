package com.stylefeng.guns.modular.zate.dto;


public class FileDto {
    String url; //半路径
    //String fullPath; //全路径
    String originalName;// 文件原始名

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

 /*   public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }*/

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
}
