package com.javatechie;

import com.javatechie.job.ReportGenerationScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootSchedulerApplication implements CommandLineRunner {

    @Autowired
    private ReportGenerationScheduler scheduler;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSchedulerApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        scheduler.generateReportAndSendEmail();
    }
}
