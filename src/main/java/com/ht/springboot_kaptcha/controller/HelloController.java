package com.ht.springboot_kaptcha.controller;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.ht.springboot_kaptcha.config.HtSysConfig;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class HelloController {

    /**
     * // Properties properties = captchaProducer.getConfig().getProperties();
     * 通过properties可以拿到文件的属性
     */
    @Resource(name = "captchaProducer")
    private DefaultKaptcha captchaProducer;


    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public String getCode(HttpServletResponse response) throws IOException {

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = HtSysConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            // 生成文件
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            // 生成图片
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();

        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 转换为base64  可以通过data:image/png;base64, 字符串... 展示图片
        return Base64.encodeBase64String(os.toByteArray());

    }

    @GetMapping("/test")
    public void test(HttpServletResponse response) throws IOException {

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = HtSysConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            // 生成文件
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            // 生成图片
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        response.setContentType("image/jpg");
        ServletOutputStream outputStream = response.getOutputStream();

        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
