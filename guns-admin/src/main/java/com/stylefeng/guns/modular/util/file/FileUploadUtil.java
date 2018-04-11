package com.stylefeng.guns.modular.util.file;


import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.config.properties.FileProperties;
import com.stylefeng.guns.modular.util.SpringContextUtil;
import com.stylefeng.guns.modular.zate.dto.FileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
public class FileUploadUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
    // 注入文件上传服务器信息
    private static FileProperties fileProperties = SpringContextUtil.getBean(FileProperties.class);

    /*
    * 上传文件
    * */
    public static FileDto upload(MultipartFile file) throws Exception{
        String serverIp = fileProperties.getServerIp();
        String path = fileProperties.getPath();
        String fileAddress = fileProperties.getFileAddress();
        FileDto fileDTO = fileUpload(file, serverIp, path, fileAddress);
        return fileDTO;
    }
    /**
     *
     * @param file 文件
     * @param serverIp  文件服务器地址
     * @param path 映射路径
     * @param fileAddress 文件自定义路径
     * @return
     */
    private static FileDto fileUpload(MultipartFile file, String serverIp, String path, String fileAddress) throws Exception {
        FileUploadMsg fileUploadMsg = new FileUploadMsg();
        fileUploadMsg.setFile(file);
        fileUploadMsg.setFileMapPath(path);
        fileUploadMsg.setFilePath(fileAddress);
        fileUploadMsg.setFileFullPath(serverIp);
        FileDto fileDTO = FileWriteUtil.uploadFileGetFullPath(fileUploadMsg);
        fileDTO.setOriginalName(file.getOriginalFilename());
        return fileDTO;

    }
    /**
     * 删除文件
     * filePath : 图片半路径
     */
    public static Boolean removeFile(String filePath) throws Exception{
        Boolean reBool = false;
        String serverIp = fileProperties.getPath();
        String fullPath = serverIp + filePath;
        try {
            File delFile = new File(fullPath);
            if (delFile.exists()) {
                delFile.delete();
                reBool = true;
            }
        } catch (Exception e) {
            logger.info(Constant.FILE_DEl_ERROR + "---" + fullPath);
        }
        return reBool;
    }
}
