package com.h52mm.blog.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.Properties;

@Configuration
@PropertySource(value={"classpath:config/kaptcha.properties"},encoding = "UTF-8")
public class CaptchaConfig {
    @Resource
    Environment environment;
    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Properties properties=new Properties();

        properties.setProperty("kaptcha.textproducer.char.string",environment.getProperty("kaptcha.textproducer.char.string"));
        properties.setProperty("kaptcha.textproducer.char.length",environment.getProperty("kaptcha.textproducer.char.length"));
        properties.setProperty("kaptcha.border",environment.getProperty("kaptcha.properties.kaptcha.border"));
        properties.setProperty("kaptcha.image.width",environment.getProperty("kaptcha.properties.kaptcha.image.width"));
        properties.setProperty("kaptcha.image.height",environment.getProperty("kaptcha.properties.kaptcha.image.height"));
        properties.setProperty("kaptcha.border.color",environment.getProperty("kaptcha.properties.kaptcha.border.color"));
        properties.setProperty("kaptcha.textproducer.font.color",environment.getProperty("kaptcha.properties.kaptcha.textproducer.font.color"));
        properties.setProperty("kaptcha.textproducer.font.size",environment.getProperty("kaptcha.properties.kaptcha.textproducer.font.size"));
        properties.setProperty("kaptcha.textproducer.font.names",environment.getProperty("kaptcha.properties.kaptcha.textproducer.font.names"));
        properties.setProperty("kaptcha.obscurificator.impl",environment.getProperty("kaptcha.properties.kaptcha.obscurificator.impl"));
        properties.setProperty("kaptcha.session.key",environment.getProperty("kaptcha.properties.kaptcha.session.key"));
        properties.setProperty("kaptcha.noise.color",environment.getProperty("kaptcha.properties.kaptcha.noise.color"));
        properties.setProperty("kaptcha.background.clear.from",environment.getProperty("kaptcha.properties.kaptcha.background.clear.from"));
        properties.setProperty("kaptcha.background.clear.to",environment.getProperty("kaptcha.properties.kaptcha.background.clear.to"));
        properties.setProperty("kaptcha.textproducer.char.space",environment.getProperty("kaptcha.textproducer.char.space"));

        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
