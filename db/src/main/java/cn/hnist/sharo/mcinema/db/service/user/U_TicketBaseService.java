package cn.hnist.sharo.mcinema.db.service.user;

import cn.hnist.sharo.mcinema.core.exception.DatabaseException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.StatusEnum;
import cn.hnist.sharo.mcinema.core.util.DataUtil;
import cn.hnist.sharo.mcinema.db.dao.*;
import cn.hnist.sharo.mcinema.db.pojo.*;
import cn.hnist.sharo.mcinema.db.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class U_TicketBaseService {
    @Value(value = "${mcinema.redis-key.seat}")
    private String seatRoot;
    @Value(value = "${mcinema.redis-key.ticket}")
    private String ticketRoot;

    @Resource
    TicketBaseMapper ticketMapper;
    @Resource
    VTicketMapper vTicketMapper;
    @Resource
    SceneBaseMapper sceneMapper;
    @Resource
    VSceneMapper vSceneMapper;
    @Resource
    HallSeatMapper hallSeatMapper;
    @Resource
    RedisUtil redisUtil;

    @Transactional
    public int order(Long userId, Long sceneId, Integer[] inHallIndexList) throws DatabaseException {
        try {
            // 查询场次
            VSceneExample.Criteria sC = VSceneExample.newAndCreateCriteria();
            sC.andSceneIdEqualTo(sceneId);
            VScene scene = vSceneMapper.selectOneByExample(sC.example());
            // 获取座位
            HallSeatExample.Criteria hC = HallSeatExample.newAndCreateCriteria();
            hC.andHallIdEqualTo(scene.getHallId()).andInHallIndexIn(Arrays.asList(inHallIndexList));
            List<HallSeat> hallSeats = hallSeatMapper.selectByExample(hC.example());
//        TicketBaseExample.Criteria tC = TicketBaseExample.newAndCreateCriteria();
            // 循环插入'
            AtomicInteger res = new AtomicInteger();
            LocalDateTime now = LocalDateTime.now();
            // 订单号
            String orderId = DataUtil.hex10To62(System.nanoTime());
            List<Integer> indexArray = new ArrayList<>();
            List<VTicket> ticketArray = new ArrayList<>();
            hallSeats.forEach(seat -> {
                VTicket ticket = new VTicket();
                //ticketId,filmName,beginTime,endTime,hallName,inHallIndex,price,refundable,orderId
                ticket.setSceneId(sceneId);
                ticket.setUserId(userId);
                ticket.setSeatId(seat.getSeatId());
                ticket.setCreateTime(now);
                ticket.setFilmName(scene.getFilmName());
                ticket.setHallName(scene.getHallName());
                ticket.setBeginTime(scene.getBeginTime());
                ticket.setEndTime(scene.getEndTime());
                ticket.setInHallIndex(seat.getInHallIndex());
                ticket.setTicketStatus(StatusEnum.TicketStatus.RESERVE.v());
                BigDecimal price = scene.getPrice();
                // price.add(seat.getPrice()) 加上座位的价格
                ticket.setPrice(price);
                ticket.setRefundable(scene.getRefundable());
                ticket.setOrderId(orderId);

                ticketArray.add(ticket);
                indexArray.add(seat.getInHallIndex());

                res.addAndGet(1);
            });
            //redis事务 预定票
            redisUtil.set(ticketRoot + userId + ":" + orderId, ticketArray, 5, TimeUnit.MINUTES);
            // redis事务 占座
            redisUtil.set(seatRoot + sceneId + ":" + orderId, indexArray, 5, TimeUnit.MINUTES);
            if (res.get() != hallSeats.size())
                throw new DatabaseException("预定失败", ErrorEnum.FAILED_TRANSACTION);
            return res.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("预定失败", ErrorEnum.FAILED_TRANSACTION);
        }
    }

    public List<VTicket> listByStatus(Long userId, Short ticketStatus) {
        if (ticketStatus.equals(StatusEnum.TicketStatus.RESERVE.v())) {
            Set<String> keys = redisUtil.getKeys(ticketRoot + userId + ":*");
            List<VTicket> list = new ArrayList<>();
            String[] array = keys.toArray(new String[0]);
            for (String each : array) {
                list.addAll((ArrayList<VTicket>) redisUtil.get(each));
            }
            return list;
        } else {
            VTicket.Column[] columns = {
                    VTicket.Column.ticketId,
                    VTicket.Column.filmName,
                    VTicket.Column.beginTime,
                    VTicket.Column.endTime,
                    VTicket.Column.hallName,
                    VTicket.Column.inHallIndex,
                    VTicket.Column.price,
                    VTicket.Column.refundable,
                    VTicket.Column.orderId
            };
            VTicketExample.Criteria criteria = VTicketExample.newAndCreateCriteria();
            criteria.andUserIdEqualTo(userId).andTicketStatusEqualTo(ticketStatus);
            return vTicketMapper.selectByExampleSelective(criteria.example(), columns);
        }

    }

    @Transactional
    public int payMock(Long userId, String orderId) {
        List<VTicket> list = (ArrayList<VTicket>) redisUtil.get(ticketRoot + userId + ":" + orderId);
        list = (list == null ? new ArrayList<>() : list);
        int res = 0;
        for (VTicket vTicket : list) {
            if (vTicket.getOrderId().equals(orderId)) {
                TicketBase ticket = new TicketBase();
                ticket.setCreateTime(vTicket.getCreateTime());
                ticket.setSceneId(vTicket.getSceneId());
                ticket.setSeatId(vTicket.getSeatId());
                ticket.setPrice(vTicket.getPrice());
                ticket.setOrderId(vTicket.getOrderId());
                ticket.setUserId(userId);
                ticket.setPayTime(LocalDateTime.now());
                ticket.setTicketStatus(StatusEnum.TicketStatus.PAID.v());
                ticket.setRefundable(vTicket.getRefundable());
                res += ticketMapper.insertSelective(ticket);
            }
        }
        redisUtil.delete(ticketRoot + userId + ":" + orderId);
        List<Integer> inHallSeatIndexList = (ArrayList<Integer>) redisUtil.get(seatRoot + list.get(0).getSceneId() + ":" + orderId);
        SceneBase scene = sceneMapper.selectByPrimaryKey(list.get(0).getSceneId());
        Integer[][] seatChooseArrange = scene.getSeatChooseArrange();
        Integer[][] seatArrange = scene.getSeatArrange();
        for (int row = 0; row < seatArrange.length; row++) {
            if (inHallSeatIndexList.size() == 0) break;
            for (int col = 0; col < seatArrange[row].length; col++) {
                if (inHallSeatIndexList.size() == 0) break;
                int i = inHallSeatIndexList.indexOf(seatArrange[row][col]);
                if (i != -1) {
                    seatChooseArrange[row][col] = 0;
                    inHallSeatIndexList.remove(i);
                }
            }
        }
        SceneBase record = new SceneBase();
        record.setSeatChooseArrange(seatChooseArrange);
        record.setSceneId(scene.getSceneId());
        sceneMapper.updateByPrimaryKeySelective(record);
        // 删除占用座位
        redisUtil.delete(seatRoot + scene.getSceneId());
        return res;
    }
}
