package cn.hnist.sharo.mcinema.userapi.controller;

import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.model.PagingRec;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.core.validator.InsertBy;
import cn.hnist.sharo.mcinema.core.validator.ListBy;
import cn.hnist.sharo.mcinema.core.validator.UpdateFor;
import cn.hnist.sharo.mcinema.db.pojo.VTicket;
import cn.hnist.sharo.mcinema.db.service.user.U_TicketBaseService;
import cn.hnist.sharo.mcinema.userapi.annotation.UserJWT;
import cn.hnist.sharo.mcinema.userapi.vo.TicketRec;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "票务的操作接口")
@ApiSupport(order = 8)

@RestController
@RequestMapping(value = "/api/user/ticket")
public class TicketController {
    @Resource
    U_TicketBaseService uTicketBaseService;

    @ApiOperation("1.1 订票")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sceneId", value = "场次编号", required = true),
            @ApiImplicitParam(name = "inHallIndexList", value = "厅内座位序号数组", required = true),
    })
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public HttpRes ticketOrderHandler(@UserJWT Long userId, @ApiIgnore @RequestBody @Validated({InsertBy.class}) TicketRec rec) {
        return HttpResUtil.hasError(() -> {
            int res = uTicketBaseService.order(userId, rec.getSceneId(), rec.getInHallIndexList());
            return HttpResUtil.succeed("预定成功", res);
        });
    }


    @ApiOperation("2.1 查看我的电影票")
    @ApiOperationSupport(order = 10)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ticketStatus", value = "票务状态", required = true),
    })
    @RequestMapping(value = "/list/status", method = RequestMethod.GET)
    public HttpRes ticketListByStatusHandler(@UserJWT Long userId, @ApiIgnore @Validated({ListBy.Status.class}) TicketRec rec, PagingRec paging) {
//        PageHelper.startPage(paging.getPageNum(), paging.getPageSize());
        List<VTicket> list = uTicketBaseService.listByStatus(userId, rec.getTicketStatus());
//        PageInfo<VTicket> info = new PageInfo<>(list);
//        PageHelper.clearPage();
        return HttpResUtil.succeedList("查询成功", list);
    }

    @ApiOperation("3.1 模拟付款功能")
    @ApiOperationSupport(order = 20)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ticketId", value = "票务编号", required = true)
    })
    @RequestMapping(value = "/pay/mock", method = RequestMethod.POST)
    public HttpRes ticketPayByMockHandler(@UserJWT Long userId, @ApiIgnore @RequestBody @Validated({UpdateFor.Order.class}) TicketRec rec, PagingRec paging) {
        return HttpResUtil.hasError(() -> {
            int res = uTicketBaseService.payMock(userId, rec.getOrderId());
            if (res >= 1)
                return HttpResUtil.succeed("支付成功", res);
            else return HttpResUtil.fail("支付失败", ErrorEnum.UNKNOWN_EXCEPTION);
        });
    }
}
