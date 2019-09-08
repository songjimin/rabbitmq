package com.example.rabbitmq.service;

import com.example.rabbitmq.config.RabbitMQConfig;
import com.example.rabbitmq.model.SampleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class Consumer {
    /**
     * 메시지 수신 시 처리할 Handler 함수
     * @param message RabbitMQ의 test-queue-1으로부터 수신한 메시지
     */
    @RabbitListener(containerFactory = "SampleContainerFactory", queues= RabbitMQConfig.QUEUE_NAME)
    public void onReceiveMessage(SampleMessage message){
        log.info("Receiver received message: {}", message);

        if(Objects.nonNull(message)) {
            throw new AmqpRejectAndDontRequeueException("to dead-letter");
        }
    }

    @RabbitListener(queues= RabbitMQConfig.DEAD_LETTER_QUEUE)
    public void onDLQReceiveMessage(SampleMessage message){
        log.info("DLQ Receiver received message: {}", message);
    }

}
