package com.example.rabbitmq.controller;


import com.example.rabbitmq.model.SampleMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("publisher")
public class PublishController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/message")
    public SampleMessage publicshMessage(@RequestBody SampleMessage sampleMessage) {
        rabbitTemplate.convertAndSend(sampleMessage);
        return new SampleMessage();
    }
}
