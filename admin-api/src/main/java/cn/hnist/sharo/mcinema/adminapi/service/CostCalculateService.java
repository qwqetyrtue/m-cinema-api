package cn.hnist.sharo.mcinema.adminapi.service;

import cn.hnist.sharo.mcinema.core.exception.CalculateException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.db.pojo.*;
import cn.hnist.sharo.mcinema.db.service.admin.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class CostCalculateService {
    @Resource
    TicketBaseService ticketService;
    @Resource
    SceneBaseService sceneService;
    @Resource
    DiscountBaseService discountService;
    @Resource
    HallSeatService seatService;
    @Resource
    HallSeatLevelService seatLevelService;


    /**
     * <h3>计算票价</h3>
     * <h>票价分为以下部分</h>
     * <ul>
     *     <li>基础价格:由影片和观影类型确定</li>
     * </ul>
     * @param ticketId 观影票编号
     * @return 票价
     */
    public BigDecimal calCost(Long ticketId){
        TicketBase ticket = ticketService.oneByPK(ticketId);
        Long sceneId = ticket.getSceneId();
        SceneBase scene = sceneService.oneByPK(sceneId);
        if(scene == null) throw new CalculateException("", ErrorEnum.MISSING_CALCULATE_OBJECT,ticket.getTicketId());
        //场次基础价格
        BigDecimal scenePrice = scene.getPrice();
        // 场次类型价格
        HallSeat seat = seatService.oneByPK(ticket.getSeatId());
        HallSeatLevel hallSeatLevel = seatLevelService.oneByPK(seat.getLevel());
        // 座位类型价格
        BigDecimal seatLevelPrice = hallSeatLevel.getPrice();
        return seatLevelPrice.add(scenePrice).add(seatLevelPrice);
    }

    public BigDecimal calDiscountCost(BigDecimal init,BigDecimal ...discount){
        for (BigDecimal each : discount) {
            init = init.multiply(each);
        }
        return init;
    }
}
