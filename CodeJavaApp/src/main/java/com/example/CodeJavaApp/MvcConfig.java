package com.example.CodeJavaApp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path brandUploadPrd = Paths.get("./uploads-image");
        String brandUploadPath = brandUploadPrd.toFile().getAbsolutePath();
        registry.addResourceHandler("/uploads-image/**").addResourceLocations("file:/"+ brandUploadPath+ "/");
    }
}
