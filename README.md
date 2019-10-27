docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 --restart=unless-stopped -e RABBITMQ_DEFAULT_USER=username -e RABBITMQ_DEFAULT_PASS=password rabbitmq:management


기본셋팅
https://leeyh0216.github.io/spring/2018/12/30/spring-rabbitmq-1.html

MVC
http://www.nurettinyakit.com/2018/09/how-to-run-rabbitmq-on-docker-and.html

참고해볼만한 사이트
https://cheese10yun.github.io/spring-rabbitmq/
https://heowc.tistory.com/36
https://teragoon.wordpress.com/category/rabbitmq/page/1/
https://m.blog.naver.com/PostView.nhn?blogId=willygwu2003&logNo=130171987216&proxyReferer=https%3A%2F%2Fwww.google.com%2F

https://www.rabbitmq.com/#features

반응형 웹 플럭스
https://kimseunghyun76.tistory.com/455

dead-letter-queue
https://zoltanaltfatter.com/2016/09/06/dead-letter-queue-configuration-rabbitmq/
https://blog.leocat.kr/notes/2018/06/20/rabbitmq-dead-lettering-with-reject-or-nack

retry template
https://docs.spring.io/spring-amqp/docs/current/reference/html/#template-retry

real retry arch
https://engineering.nanit.com/rabbitmq-retries-the-full-story-ca4cc6c5b493


try (not yet)
https://gist.github.com/trippstowe/8b72c65e0651d7aef71549744b7e7ccd#file-springrabbitmqdeadletterconfiguration-java