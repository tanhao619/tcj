package com.stylefeng.guns.modular.util.file;


import com.stylefeng.guns.modular.zate.dto.FileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class FileWriteUtil {
    private static Logger logger = LoggerFactory.getLogger(FileWriteUtil.class);

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};



    /**
     * 功能：图片上传并返回图片全路径
     *
     * @return
     */
    public static FileDto uploadFileGetFullPath(FileUploadMsg uImages) throws IOException {
        logger.info("文件开始上传,{}",uImages.getFile().getOriginalFilename());
        String img_url = null;
        FileDto fileDTO = new FileDto();
        // 获得文件：
        MultipartFile file = uImages.getFile();
        if (file != null && file.getSize() > 0) {
            String fileName = file.getOriginalFilename();
            String filedisk = getDatePath();

            int index = fileName.lastIndexOf(".");
            int length = fileName.length();
            String type = fileName.substring(index, length);
            String pathDisk = getTypeDiskPath(type);
            /*if (type.indexOf("png") > 0 || type.indexOf("jpg") > 0 || type.indexOf("bmp") > 0 || type.indexOf("gif") > 0 || type.indexOf("jpeg") > 0) {*/
                String uuId = generateShortUuid() + (int) (1 + Math.random() * (9999 - 0 + 1)) + new Date().getTime();
                String filePath = uImages.getFileMapPath() + uImages.getFilePath() + "/" + pathDisk + "/" + filedisk + "/" + uuId;
//            String newFileName = "ma_" + (int) (1 + Math.random() * (9999 - 0 + 1)) + new Date().getTime() + "" + type;
                String newFileName = fileName;
                File newFilePath = new File(filePath);
                if (!newFilePath.exists()) {
                    newFilePath.mkdirs();
                }

                filePath = filePath + "/" + newFileName;
                File newFile = new File(filePath);
                file.transferTo(newFile);
                img_url = uImages.getFileFullPath() + uImages.getFilePath() + "/" + pathDisk + "/" + filedisk + "/" +uuId+ "/" + newFileName;
                //fileDTO.setFullPath(uImages.getFileFullPath() + uImages.getFilePath() + "/" + pathDisk + "/" + filedisk + "/" +uuId+ "/" + newFileName);
                fileDTO.setUrl(uImages.getFilePath() + "/" + pathDisk + "/" + filedisk + "/" +uuId+ "/" + newFileName);
/*            }else{
                return null;
            }*/
        }
        logger.info("文件上传完成：{}",img_url);
        return fileDTO;
    }


    /**
     * 根据文件类型 生成 目录
     *
     * @param type
     * @return
     */
    public static String getTypeDiskPath(String type) {
        String pathDisk = null;
        if (type.indexOf("png") > 0 || type.indexOf("jpg") > 0 || type.indexOf("BMP") > 0 || type.indexOf("GIF") > 0 || type.indexOf("JPEG") > 0) {
            pathDisk = "image";
        } else if (type.indexOf("pdf") > 0 || type.indexOf("PDF") > 0) {
            pathDisk = "pdf";
        } else if (type.indexOf("zip") > 0 || type.indexOf("ZIP") > 0) {
            pathDisk = "zip";
        } else if (type.indexOf("mp4") > 0 || type.indexOf("rmvb") > 0 || type.indexOf("MPEG4") > 0 || type.indexOf("MP4") > 0) {
            pathDisk = "video";
        } else {
            pathDisk = "file";
        }
        return pathDisk;
    }

    /**
     * 获取时间格式目录
     *
     * @return
     */
    public static String getDatePath() {
        String dick = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            dick = sdf.format(new Date());
        } catch (Exception e) {
            dick = "null";
        }
        return dick;
    }


    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }
}
