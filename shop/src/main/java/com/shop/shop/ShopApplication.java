package com.shop.shop;

import com.shop.shop.entity.PhotoProduct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableConfigurationProperties({
        PhotoProduct.class
})
public class ShopApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }
}
