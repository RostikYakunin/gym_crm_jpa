package com.crm;

import com.crm.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GymSpringApp {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(context.getStartupDate());
    }
}
