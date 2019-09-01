package com.example.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SampleMessage implements Serializable {
    private String id;
    private String imgUrl;

}
