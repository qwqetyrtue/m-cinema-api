package cn.hnist.sharo.mcinema.core.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;


/**
 * <h3>数据工具</h3>
 */
public class DataUtil {

    private static final char[] charSet = "qwertyuiopasdfghjklzxcvbnm0123456789QWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();

    /**
     * charSequence:
     * <ul>
     *     <li>"PT20.345S" -- parses as "20.345 seconds"</li>
     *     <li>"PT15M"     -- parses as "15 minutes" (where a minute is 60 seconds)</li>
     *     <li>"PT10H"     -- parses as "10 hours" (where an hour is 3600 seconds)</li>
     *     <li>"P2D"       -- parses as "2 days" (where a day is 24 hours or 86400 seconds)</li>
     *     <li>"P2DT3H4M"  -- parses as "2 days, 3 hours and 4 minutes"</li>
     * </ul>
     *
     * @param now          起始时间
     * @param charSequence 如上的字符串参数,用于设置时间间隔
     * @return 计算后的Date
     */
    public static Date calculateTime(Date now, String charSequence) throws DateTimeParseException {
        Duration duration = Duration.parse(charSequence);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        calendar.add(Calendar.SECOND, (int) duration.getSeconds());
        return calendar.getTime();
    }


    public static String hex10To62(Long number) {
        Long rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(charSet[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    public static String hex62To10(String number) throws StringIndexOutOfBoundsException {
        long dst = 0L;
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            for (int j = 0; j < charSet.length; j++) {
                if (c == charSet[j]) {
                    dst = (dst * 62) + j;
                    break;
                }
            }
        }
        return Long.toString(dst);
    }

    public static boolean checkMatrix(Integer[][] target, Integer row, Integer column, Boolean canBeNull) {
        if (!canBeNull && (row == 0 || column == 0))
            return false;
        else {
            if (target == null || !checkEmpty(row) || !checkEmpty(column)) return false;
            if (row != target.length) return false;
            for (Integer[] each : target) {
                if (each.length != column) return false;
            }
            return true;
        }
    }

    // 矩阵 判断传入的是否是个矩阵,以及行,列数是否错误
    public static boolean checkMatrix(Integer[][] target, Integer row, Integer column) {
        return checkMatrix(target, row, column, true);
    }

    public static boolean checkEmpty(Integer[] list) {
        return list != null && list.length != 0;
    }

    public static boolean checkEmpty(String[] list) {
        return list != null && list.length != 0;
    }

    // 字符串
    public static boolean checkEmpty(String target) {
        return target != null && target.trim().length() != 0;
    }

    // 数字
    public static boolean checkEmpty(Integer target) {
        return target != null;
    }

    public static boolean checkEmpty(Long target) {
        return target != null;
    }

    public static boolean checkEmpty(Short target) {
        return target != null;
    }

    // 数字_范围
    public static boolean checkRange(Integer target, int min, int max) {
        return target != null && target >= min && target <= max;
    }

    public static boolean checkRange(Long target, long min, long max) {
        return target != null && target >= min && target <= max;
    }

    public static boolean checkRange(Short target, short min, short max) {
        return target != null && target >= min && target <= max;
    }

    // 时间
    public static boolean checkEmpty(LocalDateTime target) {
        return target != null;
    }

    // 布尔值
    public static boolean checkEmpty(Boolean target) {
        return target != null;
    }
}
