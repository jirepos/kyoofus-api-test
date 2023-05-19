package com.jirepo.demo.rabbitmq;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo/rabbitmq")
public class RabbitDemoController {


    /** 메시지를 발행하려면 RabbitTemplate를 사용한다.  */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/worker/{workerId}/{message}")
    public ResponseEntity<String> produceMessage(@PathVariable String workerId, @PathVariable  String message) {
        System.out.println(message + " message is delivering");
        // worker.one 또는 worker.two로 메시지를 보낸다.
        // exchange 이름을 사용하여 routing key를 사용하여 메시지를 보낸다.
        // 
        // Syntax: rabbitTemplate.convertAndSend(exchange, routingKey, message)
        // Parameter:
        //    exchange: 전송할 exchange 이름 
        //    routingKey: 전송할 메시지의 routing key
        //    message: 전송할 메시지
        // worker.one으로 보내면 , worker.one으로 바인딩된 큐에서 메시지를 수신한다. 
        // worker.two로 보내면, worker.two로 바인딩된 큐에서 메시지를 수신한다. 
        
        for(int i=0; i < 20; i++) {
            rabbitTemplate.convertAndSend("task-exchange", "worker.".concat(workerId), message);
        }


        // Spring에서 제공하는 Thread Pool을 활용하는 비동기 메서드 
        // https://velog.io/@gillog/Spring-Async-Annotation%EB%B9%84%EB%8F%99%EA%B8%B0-%EB%A9%94%EC%86%8C%EB%93%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>("Succeed", HttpStatus.OK);
        
    }

}
