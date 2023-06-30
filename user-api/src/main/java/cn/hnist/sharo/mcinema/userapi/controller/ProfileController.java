package cn.hnist.sharo.mcinema.userapi.controller;

import cn.hnist.sharo.mcinema.core.exception.FileException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.FileInfo;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.StorageEnum;
import cn.hnist.sharo.mcinema.core.util.StorageUtil;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.db.pojo.StorageBase;
import cn.hnist.sharo.mcinema.db.pojo.UserBase;
import cn.hnist.sharo.mcinema.db.service.system.StorageBaseService;
import cn.hnist.sharo.mcinema.db.service.user.U_UserBaseService;
import cn.hnist.sharo.mcinema.storage.service.StorageService;
import cn.hnist.sharo.mcinema.userapi.annotation.UserJWT;
import cn.hnist.sharo.mcinema.userapi.vo.UserRec;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;

@Api(tags = "用户个人信息操作接口")
@ApiSort(2)
@RestController
@RequestMapping(value = "/api/user/profile")
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    @Resource
    U_UserBaseService uUserBaseService;
    @Resource
    StorageService storageService;
    @Resource
    StorageBaseService storageBaseService;

    @ApiOperation("1.1 修改个人信息")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = false),
            @ApiImplicitParam(name = "gender", value = "性别", required = false),
            @ApiImplicitParam(name = "age", value = "年龄", required = false),
            @ApiImplicitParam(name = "sign", value = "个性签名", required = false)
    })
    @RequestMapping(value = "/update/profile", method = RequestMethod.POST)
    public HttpRes userUpdateProfileHandler(@UserJWT Long userId, @ApiIgnore @RequestBody @Validated({UpdateFor.Profile.class}) UserRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = uUserBaseService.update(rec.updaterProfile(userId));
            if (res >= 1) return HttpResUtil.succeed("修改成功");
            else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }

    @ApiOperation("修改头像")
    @ApiOperationSupport(order = 2)
    @RequestMapping(value = "/update/avatar", method = RequestMethod.POST)
    public HttpRes userUpdateAvatarHandler(@UserJWT Long userId, @ApiParam(required = true) MultipartFile avatar) {
        return HttpResUtil.hasError(() -> {
            FileInfo info = StorageUtil.getInfo(avatar);

            if (info == null)
                throw new FileException(ErrorEnum.FAILED_LOAD);
            try {
                StorageBase storageInfo = storageService.store(
                        avatar.getInputStream(),
                        StorageEnum.IMAGE,
                        info
                );
                storageBaseService.insert(storageInfo);

                UserBase update = new UserBase();
                update.setUserId(userId);
                update.setAvatar(storageInfo.getUrl());
                int res = uUserBaseService.update(update);
                if (res >= 1) return HttpResUtil.succeed("修改成功");
                else return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);

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
