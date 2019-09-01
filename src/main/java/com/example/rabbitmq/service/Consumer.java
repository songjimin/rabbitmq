package com.example.rabbitmq.service;

import com.example.rabbitmq.model.SampleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {
    /**
     * 메시지 수신 시 처리할 Handler 함수
     * @param message RabbitMQ의 test-queue-1으로부터 수신한 메시지
     */
    @RabbitListener(containerFactory = "SampleContainerFactory", queues="test-queue-1")
    public void onReceiveMessage(SampleMessage message){
        log.info("Receiver received message: {}", message);
    }
}
