package ru.moshik.tm.ripper.quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainQuoter {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        context.getBean(Quoter.class).sayQuote();
    }
}
