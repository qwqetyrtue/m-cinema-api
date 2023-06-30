package cn.hnist.sharo.mcinema.core.util;

public class HallSeatUtil {
    // 过道等不是座位的标志
    public static final Integer UNAVAILABLE_LOCATION = -1;
    // 被选了的座位
    public static final Integer OCCUPIED_SEAT = 0;
    // 被选了的座位
    public static final Integer USABLE_SEAT = 1;

    /**
     * <h3>获取座位排布矩阵中的座位数量</h3>
     *
     * @param seatArrange 座位排布
     * @return 座位数
     */
    public static Integer getSeatNum(Integer[][] seatArrange) {
        int setNum = 0;
        for (Integer[] row : seatArrange)
            for (Integer each : row) {
                if (each > 0)
                    setNum++;
            }
        return setNum;
    }

    /**
     * <h3>由座位排布矩阵获取占坐矩阵</h3>
     *
     * @param seatArrange 座位排布矩阵,默认传入的是合法的矩阵对矩阵进行判断
     * @return 占座矩阵
     */
    public static Integer[][] getChooseSeatArrange(Integer[][] seatArrange) {
        int rowNum = seatArrange.length;
        int columNum = seatArrange[0].length;
        Integer[][] chooseSeatArrange = new Integer[rowNum][columNum];
        for (int i = 0; i < chooseSeatArrange.length; i++) {
            for (int j = 0; j < chooseSeatArrange[i].length; j++) {
                if (seatArrange[i][j].equals(UNAVAILABLE_LOCATION))
                    chooseSeatArrange[i][j] = UNAVAILABLE_LOCATION;
                else chooseSeatArrange[i][j] = USABLE_SEAT;
            }
        }
        return chooseSeatArrange;
    }

    public static String getInHallLocation(Integer[][] seatArrange, Integer inHallIndex) {
        return "";
    }
}
