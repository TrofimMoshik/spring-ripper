package ru.moshik.tm.ripper.screensaver;

import org.springframework.context.annotation.*;

import java.awt.*;
import java.util.Random;

@Configuration
@ComponentScan(basePackages = "ru.moshik.tm.ripper.screensaver")
public class Config {

    @Bean
    @Scope(scopeName = "prototype")
    public Color color() {
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    @Bean
    public ColorFrame colorFrame() {
        return new ColorFrame() {
            @Override
            protected Color getColor() {
                return color();
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext contex = new AnnotationConfigApplicationContext(Config.class);
        while (true) {
            Thread.sleep(100);
            contex.getBean(ColorFrame.class).showOnRandomPlace();
        }
    }

}
