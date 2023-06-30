package cn.hnist.sharo.mcinema.core.util;

import cn.hnist.sharo.mcinema.core.config.kaptcha.KaptchaConfig;
import com.google.code.kaptcha.impl.DefaultKaptcha;

import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * <h3>验证码工具</h3>
 * <ul>
 *     <li>
 *         <h4>createCaptcha()</h4> 获取验证码信息对象
 *     </li>
 * </ul>
 */
public class CaptchaUtil {
    private static final String pk = "m-cinema-captcha";

    public static CaptchaInfo createCaptchaWithImage(DefaultKaptcha producer) {
        CaptchaInfo info = new CaptchaInfo();
        String captchaOrigin;
        String captchaVerify;
        BufferedImage image;
        // 生成uuid
        String uuid = UUID.randomUUID().toString();
        uuid += pk;
        if (producer.getClass().equals(KaptchaConfig.CaptchaMathProducer.class)) {
            captchaOrigin = producer.createText();
            captchaVerify = captchaOrigin.substring(captchaOrigin.lastIndexOf('=') + 1);
            captchaOrigin = captchaOrigin.substring(0, captchaOrigin.lastIndexOf('=') + 1) + "?";
        } else {
            captchaOrigin = producer.createText();
            captchaVerify = captchaOrigin;
        }
        image = producer.createImage(captchaOrigin);
        info.setUUID(uuid);
        info.setCaptchaOrigin(captchaOrigin);
        info.setCaptchaVerify(captchaVerify);
        info.setImage(image);
        return info;
    }

    public static CaptchaInfo createCaptcha(DefaultKaptcha producer){
        CaptchaInfo info = new CaptchaInfo();
        String captchaOrigin;
        String captchaVerify;
        // 生成uuid
        String uuid = UUID.randomUUID().toString();
        uuid += pk;
        captchaOrigin = producer.createText();
        captchaVerify = captchaOrigin;
        info.setUUID(uuid);
        info.setCaptchaOrigin(captchaOrigin);
        info.setCaptchaVerify(captchaVerify);
        return info;
    }

    public static class CaptchaInfo {
        private String captchaOrigin;
        private BufferedImage image;
        private String captchaVerify;
        private String UUID;

        @Override
        public String toString() {
            return "CaptchaInfo{" +
                    "captchaOrigin='" + captchaOrigin + '\'' +
                    ", image=" + image +
                    ", captchaVerify='" + captchaVerify + '\'' +
                    ", UUID='" + UUID + '\'' +
                    '}';
        }

        public String getCaptchaOrigin() {
            return captchaOrigin;
        }

        public void setCaptchaOrigin(String captchaOrigin) {
            this.captchaOrigin = captchaOrigin;
        }

        public BufferedImage getImage() {
            return image;
        }

        public void setImage(BufferedImage image) {
            this.image = image;
        }

        public String getCaptchaVerify() {
            return captchaVerify;
        }

        public void setCaptchaVerify(String captchaVerify) {
            this.captchaVerify = captchaVerify;
        }

        public String getUUID() {
            return UUID;
        }

        public void setUUID(String UUID) {
            this.UUID = UUID;
        }
    }
}
