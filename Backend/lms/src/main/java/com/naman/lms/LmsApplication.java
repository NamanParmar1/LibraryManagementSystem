package com.naman.lms;

import com.naman.lms.serviceImplementation.BookServiceImplementation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LmsApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(LmsApplication.class, args);

        BookServiceImplementation bookservice = ctx.getBean(BookServiceImplementation.class);



    }

}
