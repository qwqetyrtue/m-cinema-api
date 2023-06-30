package cn.hnist.sharo.mcinema.storageapi.controller;

import cn.hnist.sharo.mcinema.core.exception.FileException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.FileInfo;
import cn.hnist.sharo.mcinema.core.model.StorageEnum;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.util.StorageUtil;
import cn.hnist.sharo.mcinema.db.pojo.StorageBase;
import cn.hnist.sharo.mcinema.db.service.system.StorageBaseService;
import cn.hnist.sharo.mcinema.storage.service.StorageService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/resource/")
public class StorageController {
    @javax.annotation.Resource
    StorageBaseService storageBaseService;
    @javax.annotation.Resource
    StorageService storageService;


    @RequestMapping(value = "/s/image/{key:^[A-Za-z0-9]{1,15}\\.(?:jpg|png|tif|gif|svg|webp|avif|apng)$}", method = RequestMethod.GET)
    public ResponseEntity<Object> getImageHandler(@PathVariable String key) {
        return HttpResUtil.hasErrorEntity(() -> {
            StorageEnum storageEnum = StorageEnum.IMAGE;

            // 取消每次查看图片都访问数据库
//            StorageBase storageInfo = storageBaseService.oneByKey(key);
//            if (storageInfo == null)
//                throw new FileException(ErrorEnum.FAILED_FETCH);
//            MediaType mediaType = MediaType.parseMediaType(storageInfo.getContentType());

            Resource file = storageService.getResource(key, storageEnum);

            return ResponseEntity.ok().contentType(
                    MediaTypeFactory.getMediaType(key).orElse(MediaType.APPLICATION_OCTET_STREAM)
            ).body(file);
        });
    }


    @RequestMapping(value = "/upload/{type}", method = RequestMethod.POST)
    public ResponseEntity<Object> uploadStorageHandler(@PathVariable String type, @RequestParam("file") MultipartFile file) {
        return HttpResUtil.hasErrorEntity(() -> {
            StorageEnum storageEnum = StorageEnum.parse(type);
            FileInfo info = StorageUtil.getInfo(file);

            if (info == null)
                throw new FileException(ErrorEnum.FAILED_LOAD);
            try {
                StorageBase storageInfo = storageService.store(
                        file.getInputStream(),
                        storageEnum,
                        info
                );
                storageBaseService.insert(storageInfo);
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(HttpResUtil.succeed("上传成功", storageInfo));
            } catch (IOException e) {
                e.printStackTrace();
                throw new FileException(ErrorEnum.FAILED_UPLOAD);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new FileException(ErrorEnum.ILLEGAL_URL);

            }
        });
    }
}