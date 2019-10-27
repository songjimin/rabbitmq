package com.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;


@Configuration
@EnableRabbit
public class RabbitMQConfig {
    private static final String RABBITMQ_HOST = "localhost";
    private static final int RABBITMQ_PORT = 5672;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public static final String QUEUE_NAME = "q";
    public static final String ROUTING_KEY = "rk";
    public static final String EXCHANGE_NAME = "ex";


    public static final String DEAD_LETTER_QUEUE_NAME = "dlq";


    /**
     *
     * RabbitMQ Server와의 Connection을 생성하는 Factory 객체를 선언
     */
    @Bean
    public ConnectionFactory getConnectionFactory(){
        ConnectionFactory connectionFactory = new CachingConnectionFactory(RABBITMQ_HOST, RABBITMQ_PORT);
        ((CachingConnectionFactory) connectionFactory).setUsername(USERNAME);
        ((CachingConnectionFactory) connectionFactory).setPassword(PASSWORD);
        return connectionFactory;
    }

    /**
     * RabbitMQ에서 사용할 Exchange를 선언
     * Exchange 종류는 Topic 사용
     */
    @Bean
    public TopicExchange getExchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    /**
     * RabbitMQ에서 사용할 Queue를 선언
     */
    @Bean
    public Queue queue(){

        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_NAME)
                .build();
    }

    /**
     * Dead-Letter-Queue 선언
     */
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_NAME)
                .build();
    }


    /**
     * Exchange와 Queue를 연결하는 Binding 객체를 선언
     *
     * @param queue 초기화된 Queue
     * @param exchange 초기화된 TopicExchange
     * @return Binding 객체
     */

    @Bean
    public Binding getBinding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    /**
        RabbitMQ의 Publisher가 사용하는 Template
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(getConnectionFactory());
        template.setRoutingKey(ROUTING_KEY);
        template.setExchange(EXCHANGE_NAME);
        template.setMessageConverter(getMessageConverter());
        template.setRetryTemplate(retryTemplate());
        return template;
    }

    /**
     * RabbitMQ에서 메시지를 수신할 Listener의 Factory를 선언하고 반환한다.
     *
     * @param connectionFactory ConnectionFactory 객체
     * @param converter Jackson Converter 객체. byte[] <-> 메시지 간 변환을 담당
     * @return 초기화된 Listener Factory 객체
     */
    @Bean("SampleContainerFactory")
    SimpleRabbitListenerContainerFactory getSampleContainerFactory(ConnectionFactory connectionFactory,
                                                                   Jackson2JsonMessageConverter converter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
      //factory.setConcurrency("4");
      //factory.setPrefetchCount(20);
        return factory;
    }





    @Bean
    public Jackson2JsonMessageConverter getMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * https://docs.spring.io/spring-amqp/docs/current/reference/html/#template-retry
     * https://skibis.tistory.com/309
     **/

    public RetryTemplate retryTemplate() {
        //create retryTemplate and attach the backoffPolicy
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(exponentialBackOffPolicy());

        //create a SimpleRetryPolicy and attach to the retryTemplate
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(5);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

    public ExponentialBackOffPolicy exponentialBackOffPolicy(){
        //setup ExponentialBackOffPolicy
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(3000);

        return backOffPolicy;
    }




}
