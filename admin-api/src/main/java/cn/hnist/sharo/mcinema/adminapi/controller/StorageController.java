package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.adminapi.vo.StorageRec;
import cn.hnist.sharo.mcinema.core.exception.FileException;
import cn.hnist.sharo.mcinema.core.model.*;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.util.StorageUtil;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.db.pojo.StorageBase;
import cn.hnist.sharo.mcinema.db.service.system.StorageBaseService;
import cn.hnist.sharo.mcinema.storage.service.StorageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Api(tags = "文件接口")
@RestController
@RequestMapping("/resource/")
public class StorageController {
    @javax.annotation.Resource
    StorageBaseService storageBaseService;
    @javax.annotation.Resource
    StorageService storageService;

    @ApiOperation(value = "1.2 文件上传接口")
    @ApiOperationSupport(order = 2)
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
            } catch (URISyntaxException e){
                e.printStackTrace();
                throw new FileException(ErrorEnum.ILLEGAL_URL);
            }

        });
    }


    @ApiOperation(value = "2.1 文件列表名称,类型筛选")
    @ApiOperationSupport(order = 3)
    @RequestMapping(value = "/list/filter", method = RequestMethod.GET)
    public HttpRes storageListByFilterHandler(@ApiIgnore @Validated({ListBy.Filter.class}) StorageRec rec, PagingRec paging){
        PageHelper.startPage(paging.getPageNum(),paging.getPageSize());
        List<StorageBase> list = storageBaseService.listByFilter(rec);
        PageInfo<StorageBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功",info);
    }
}
