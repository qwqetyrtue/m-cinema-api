package cn.hnist.sharo.mcinema.core.util;

import cn.hnist.sharo.mcinema.core.model.FileInfo;
import cn.hnist.sharo.mcinema.core.model.StorageEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <h3>文件操作工具</h3>
 */
public class StorageUtil {
    private static final Logger logger = LoggerFactory.getLogger(StorageUtil.class);
    public static final String defaultRootPath = "C:\\Users\\86180\\IdeaProjects\\m-cinema\\core\\src\\main\\resources\\static\\upload\\";
    public static final StorageEnum defaultType = StorageEnum.TEMP;

    public static String bufferImgToBase64(BufferedImage image, String type) {
        if (image == null) return "";
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, type, os);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return Base64.getEncoder().encodeToString(os.toByteArray());
    }

    public static FileInfo getInfo(MultipartFile file){
        if(file.getSize() == 0 ) return null;
        FileInfo fileInfo = new FileInfo();
        fileInfo.setContentType(file.getContentType());
        fileInfo.setContentLength(file.getSize());
        if (file.getOriginalFilename() == null) {
            return fileInfo;
        }
        String orgName = file.getOriginalFilename();
        int point = orgName.lastIndexOf('.');
        if (point == -1) {
           fileInfo.setName(orgName);
        } else {
            fileInfo.setName(orgName.substring(0, point));
            fileInfo.setSuffix(orgName.substring(point + 1));
        }
        return fileInfo;
    }

    public static Map<String, Object> getFileInfo(MultipartFile file) {
        Map<String, Object> info = new HashMap<>();
        if (file == null || file.getOriginalFilename() == null) {
            info.put("fileName", "");
            info.put("fileType", "");
            return info;
        }
        String orgName = file.getOriginalFilename();
        int point = orgName.lastIndexOf('.');
        if (point == -1) {
            info.put("filename", orgName);
            info.put("fileType", "");
        } else {
            info.put("filename", orgName.substring(0, point));
            info.put("fileType", orgName.substring(point + 1));
        }
        return info;
    }

}
