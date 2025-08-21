package com.jspark.demorabbitmq.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMessageListener {

    private final Executor taskExecutor;

    @RabbitListener(id = "demoListener",queues="demo-queue")
    public void recevie(String message){

        // 여기는 rabbitListener Thread 제어구간이지? 그리고 이건 스레드가 하나인걸까?

        taskExecutor.execute(
                ()->{
                    log.info("Thread name: {}", Thread.currentThread().getName());
                    Thread.currentThread().setUncaughtExceptionHandler((t, e) -> {
                        log.warn("Uncaught exception in thread {}: {}", t.getName(), e.getMessage(), e);
                    });

                    for(int i=0;i<=100;i++)
                    {
                        log.info("Received {} message from rabbitMQ {}",i,message);
                    }

                    log.info("Received message from rabbitMQ {}",message);
                }
        );

        // 여기는 rabbitListener Thread 제어구간이지?

    }
}
