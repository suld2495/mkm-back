package kr.co.mkm.config;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ComponentScan("kr.co.mkm")
@Import(SecurityConfig.class)
@PropertySource("classpath:static/properties/globals.properties")
public class RootConfig {
    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Value("${email.userid}")
    private String id;

    @Value("${email.password}")
    private String password;

    @Bean
    public JavaMailSenderImpl mailSender() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(id);
        mailSender.setPassword(password);
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}
