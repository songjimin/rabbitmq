package com.example.rabbitmq.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SampleMessage implements Serializable {
    private String id;
    private String imgUrl;

}
