package cn.hnist.sharo.mcinema.userapi.controller;


import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.db.pojo.VTicket;
import cn.hnist.sharo.mcinema.db.redis.RedisUtil;
import cn.hnist.sharo.mcinema.userapi.vo.TicketRec;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Api(tags = "座位查询接口")
@ApiSupport(order = 9)

@RestController
@RequestMapping(value = "/api/user/seat")
public class SeatController {
    @Value(value = "${mcinema.redis-key.seat}")
    private String seatRoot;
    @Resource
    RedisUtil redisUtil;

    @ApiOperation("1.1 查询场中被预定的座位")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "场次编号", required = true),
    })
    @RequestMapping(value = "/list/reserved", method = RequestMethod.GET)
    public HttpRes ticketOrderHandler(@ApiIgnore @Validated({ListBy.Owner.class}) TicketRec rec) {
        try {
            Set<String> keys = redisUtil.getKeys(seatRoot + rec.getSceneId() + ":*");
            List<Integer> list = new ArrayList<>();
            String[] array = keys.toArray(new String[0]);
            for (String each : array) {
                list.addAll((ArrayList<Integer>) redisUtil.get(each));
            }
            return HttpResUtil.succeed("查询成功", list);
        } catch (Exception e) {
            return HttpResUtil.fail("查询失败", ErrorEnum.UNKNOWN_EXCEPTION);
        }


    }
}
