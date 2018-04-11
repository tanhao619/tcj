package com.stylefeng.guns.modular.util.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileUploadMsg {
    //文件上传路径
    private String filePath;
    //图片服务器映射路径
    private String fileMapPath;
    //图片服务器地址
    private String fileFullPath;

    //HttpServletRequest
    private MultipartFile file;
    //图片列表上传
    List<MultipartFile> listFile;

    public List<MultipartFile> getListFile() {
        return listFile;
    }

    public void setListFile(List<MultipartFile> listFile) {
        this.listFile = listFile;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFileFullPath() {
        return fileFullPath;
    }
    public void setFileFullPath(String fileFullPath) {
        this.fileFullPath = fileFullPath;
    }
    public String getFileMapPath() {
        return fileMapPath;
    }
    public void setFileMapPath(String fileMapPath) {
        this.fileMapPath = fileMapPath;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
