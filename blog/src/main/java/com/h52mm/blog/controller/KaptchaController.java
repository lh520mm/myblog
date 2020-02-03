package com.h52mm.blog.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "kaptcha")
public class KaptchaController {

    @Autowired
    private Producer captchaProducer;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "getcode", method = RequestMethod.GET)
    public void getKaptchaCode(HttpServletRequest request, HttpServletResponse response,String vtoken) {
        response.setDateHeader("Expires", 0);

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");


        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = captchaProducer.createText();

        // store the text in the session
        ServletOutputStream out = null;

        try {
            // create the image with the text
            redisTemplate.opsForValue().set(vtoken,capText,60, TimeUnit.SECONDS);
            BufferedImage bi = captchaProducer.createImage(capText);
            out = response.getOutputStream();

            // write the data out
            ImageIO.write(bi, "jpg", out);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
