package cn.hnist.sharo.mcinema.core.config.kaptcha;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.Random;

public class KaptchaMathCreator extends DefaultTextCreator {
    @Override
    public String getText() {
        // 生成算式 [x][操作符][y][=][z]
        String text = "";
        Random random = new Random();
        int x = random.nextInt(9) + 1;
        int y = random.nextInt(9) + 1;
        int op = random.nextInt(3) + 1;
        int res;
        switch (op) {
            case 1:
                res = x + y;
                text = x + "+" + y + "=" + res;
                break;
            case 2:
                res = x - y;
                text = x + "-" + y + "=" + res;
                break;
            case 3:
                res = x * y;
                text = x + "*" + y + "=" + res;
                break;
        }
        return text;
    }
}
