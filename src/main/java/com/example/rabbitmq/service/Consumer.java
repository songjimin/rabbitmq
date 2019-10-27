package com.example.rabbitmq.service;

import com.example.rabbitmq.config.RabbitMQConfig;
import com.example.rabbitmq.model.SampleMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class Consumer {
    /**
     * 메시지 수신 시 처리할 Handler 함수
     * @param message RabbitMQ의 test-queue-1으로부터 수신한 메시지
     */
    @RabbitListener(containerFactory = "SampleContainerFactory", queues= RabbitMQConfig.QUEUE_NAME)
    public void onReceiveMessage(SampleMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("Receiver received message: {}", message);


//        if(Objects.nonNull(message)) {
//            channel.basicNack(tag, true, false);
//        }

        channel.basicNack(tag, true, false);
//        channel.basicAck(tag, false);

    }

    @RabbitListener(queues= RabbitMQConfig.DEAD_LETTER_QUEUE_NAME)
    public void onDLQReceiveMessage(SampleMessage message){
        log.error("DLQ Receiver received message: {}", message);
    }

}
