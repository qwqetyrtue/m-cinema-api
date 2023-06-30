package cn.hnist.sharo.mcinema.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest(classes = CoreApplicationTests.class)
class CoreApplicationTests {

    @Test
    void contextLoads() {
        Long l = System.nanoTime();
        String a = "号";
        String b = "a";
        for (byte aByte : a.getBytes(StandardCharsets.US_ASCII)) {
            System.out.print(aByte + " ");
        }
        System.out.println();
        for (byte aByte : b.getBytes(StandardCharsets.US_ASCII)) {
            System.out.print(aByte + " ");
        }


        System.out.println();
//        System.out.println(hex10To62(new Long(-20)));
    }

    private static final char[] charSet = "qwertyuiopasdfghjklzxcvbnm0123456789QWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();

    /**
     * 10进制转62进制
     *
     * @param number
     * @return
     */
    public static String hex10To62(Long number) {
        Long rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(charSet[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    /**
     * 62进制转10进制
     *
     * @param sixty_str
     * @return
     */
    public static String hex62To10(String sixty_str) {
        Long dst = 0L;
        for (int i = 0; i < sixty_str.length(); i++) {
            char c = sixty_str.charAt(i);
            for (int j = 0; j < charSet.length; j++) {
                if (c == charSet[j]) {
                    dst = (dst * 62) + j;
                    break;
                }
            }
        }
        return dst.toString();
    }

    private void testListToMap() {
        List<User> l = new ArrayList<>();
        l.add(new User("aaa", 1));
        l.add(new User("bbb", 2));
        l.add(new User("ccc", 3));
        Map<Integer, User> m = l.stream().collect(Collectors.toConcurrentMap(each -> {
            return each.id;
        }, each -> {
            return each;
        }));
        System.out.println(m.entrySet());
    }


    private class User {
        private String name;
        private int id;

        public User(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
