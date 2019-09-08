package com.example.rabbitmq;

import com.example.rabbitmq.model.SampleMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RabbitmqApplicationTests {


	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Test
	public void sendMessageToMQ() {

		log.info("Sending message… Start");

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		IntStream.range(1, 2)
				.parallel()
				.forEach(val -> {
					rabbitTemplate.convertAndSend("test-queue-1", new SampleMessage(String.valueOf(val), "imgUrl"));
				});
		stopWatch.stop();
		log.info(stopWatch.toString());
		log.info("Sending message… End");
	}

}
