package cn.hnist.sharo.mcinema.userapi.controller;

import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.db.pojo.AdvertisingBase;

import cn.hnist.sharo.mcinema.db.service.user.U_AdvertisingBaseService;
import cn.hnist.sharo.mcinema.userapi.vo.AdvertisingRec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@ApiSort(1)
@Api(tags = "广告操作接口")
@RestController
@RequestMapping(value = "/api/user/advertising")
public class AdvertisingController {
    @Resource
    U_AdvertisingBaseService uAdvertisingService;

    @ApiOperation(value = "1.1 查询最近几条广告")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "查询条数", required = false),
    })
    @RequestMapping(value = "/list/num", method = RequestMethod.GET)
    public HttpRes advertisingListByNameHandler(@ApiIgnore AdvertisingRec rec) {
        PageHelper.startPage(1,rec.getNum());
        List<AdvertisingBase> list = uAdvertisingService.list();
        PageInfo<AdvertisingBase> info = new PageInfo<>(list);
        return HttpResUtil.succeedList("查询成功", info);
    }

}
