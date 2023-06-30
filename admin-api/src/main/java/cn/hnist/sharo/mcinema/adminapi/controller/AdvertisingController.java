package cn.hnist.sharo.mcinema.adminapi.controller;

import cn.hnist.sharo.mcinema.adminapi.vo.AdvertisingRec;
import cn.hnist.sharo.mcinema.core.exception.FileException;
import cn.hnist.sharo.mcinema.core.model.*;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.util.StorageUtil;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.OneBy;
import cn.hnist.sharo.mcinema.db.pojo.AdvertisingBase;
import cn.hnist.sharo.mcinema.db.pojo.StorageBase;
import cn.hnist.sharo.mcinema.db.service.admin.AdvertisingBaseService;
import cn.hnist.sharo.mcinema.db.service.system.StorageBaseService;
import cn.hnist.sharo.mcinema.storage.service.StorageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@ApiSort(1)
@Api(tags = "广告操作接口")
@RestController
@RequestMapping(value = "/api/admin/advertising")
public class AdvertisingController {
    @Resource
    AdvertisingBaseService advertisingService;
    @Resource
    StorageService storageService;
    @Resource
    StorageBaseService storageBaseService;


    @ApiOperation(value = "1.1 添加广告")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "广告名", required = true),
            @ApiImplicitParam(name = "link", value = "链接", required = true),
            @ApiImplicitParam(name = "text", value = "文字", required = true),
    })
    @RequiresPermissions("admin:advertising:insert")
    @RequestMapping(value = "/insert", method = RequestMethod.POST,consumes = {"multipart/form-data"})
    public HttpRes advertisingInsertHandler(@ApiIgnore @RequestPart("advertising") @Validated({InsertBy.class}) AdvertisingRec rec,@RequestParam("img") MultipartFile file) {
        return HttpResUtil.hasError(()-> {
            FileInfo info = StorageUtil.getInfo(file);

            if (info == null)
                throw new FileException(ErrorEnum.FAILED_LOAD);
            try {
                StorageBase storageInfo = storageService.store(
                        file.getInputStream(),
                        StorageEnum.IMAGE,
                        info
                );
                storageBaseService.insert(storageInfo);

                int res = advertisingService.insert(rec.adder(storageInfo.getUrl()));
                if (res >= 1)
                    return HttpResUtil.succeed("添加成功");
                else
                    return HttpResUtil.fail("添加失败", ErrorEnum.UNKNOWN_EXCEPTION);
            } catch (IOException e) {
                e.printStackTrace();
                throw new FileException(ErrorEnum.FAILED_UPLOAD);
            } catch (URISyntaxException e){
                e.printStackTrace();
                throw new FileException(ErrorEnum.ILLEGAL_URL);
            }
        });
    }

    @ApiOperation(value = "2.1 按名称查询列表")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "广告名", required = false),
    })
    @RequiresPermissions("admin:advertising:list")
    @RequestMapping(value = "/list/name", method = RequestMethod.GET)
    public HttpRes advertisingListByNameHandler(@ApiIgnore AdvertisingRec rec, PagingRec paging) {
        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<AdvertisingBase> list = advertisingService.listByName(rec.getName());
        PageInfo<AdvertisingBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

    @ApiOperation(value = "3.1 修改广告")
    @ApiOperationSupport(order = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertisingId", value = "广告编号", required = true),
    })
    @RequiresPermissions("admin:advertising:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public HttpRes advertisingUpdateHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) AdvertisingRec rec, PagingRec paging) {
        int res = advertisingService.update(rec.updater());
        if (res >= 1)
            return HttpResUtil.succeed("修改成功");
        else
            return HttpResUtil.fail("修改失败", ErrorEnum.UNKNOWN_EXCEPTION);
    }

    @ApiOperation(value = "4.1 删除广告")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertisingId", value = "广告编号", required = true),
    })
    @RequiresPermissions("admin:advertising:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HttpRes advertisingDeleteHandler(@ApiIgnore @RequestBody @Validated({OneBy.PK.class}) AdvertisingRec rec, PagingRec paging) {
        int res = advertisingService.delete(rec.getAdvertisingId());
        if (res >= 1)
            return HttpResUtil.succeed("删除成功");
        else
            return HttpResUtil.fail("删除失败", ErrorEnum.UNKNOWN_EXCEPTION);
    }
}
