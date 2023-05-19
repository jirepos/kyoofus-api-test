package com.jirepo.demo.rabbitmq;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * Consumer 구현 클래스이다. 
 */
@Component
public class RabbitDemoConsumer {

    // https://jonnung.dev/rabbitmq/2019/02/06/about-amqp-implementtation-of-rabbitmq/

    /**
     * 큐에 도착한 메시지를 수신한다. 
     * @param message 메시지 
     */
    @RabbitListener(queues = "workerOne", concurrency = "2")
    public void workerOneMessage(String message) {
        try {
            // 1초간 대기한다. 
            Thread.sleep(1000);
            // String threadName =  String.format("Thread Name: %s", LocalDateTime.now().toString());
            // Thread.currentThread().setName(threadName);
            // System.out.print(threadName + " ");
            // wokerOne으로 받은 메시지를 출력한다.
            System.out.println("workerOne : "+message);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // https://docs.spring.io/spring-amqp/reference/html/#receiving-messages
    // 비동기적으로 메시지를 수신하는 가장 쉬운 방법은 
    // @RabbitListener 어노테이션을 사용하는 것이다.
    // Rabbit listener endpoint로써 관리되는 빈의 method를 노출시키도록 한다. 
    // annotated endpoint infrasctructure는  RabbitListenerContainerFactory를 사용하여
    //  message listener container를 생성한다. 
    // 
    // Enable Listener Endpoint Annotations
    // To enable support for @RabbitListener annotations, you can add @EnableRabbit to one of your @Configuration classes. The following example shows how to do so:
    // @RabbitListener를를 지원하기 위해서는 
    // @Configuration 클래스의 하나에 @EnableRabbit를 추가해야 한다. 
    //
    /**
     * 큐에 도착한 메시지를 수신한다. 
     * 사용자가 직접 concurrency를 설정해 concurrency consumer와 max conccurency consumer를 설정합니다.
     * {@literal @}RabbitListener(queues = "fanout-test", concurrency = "3")
     * 3개의 컨슈머를 설정. 컨슈머가 3개의 스레드로 동작해 메시지를 소비하고 한다. 
     * RabbitListener 설정값 
     *   concurrency :  이 리스너에 대한 리스너 컨테이너의 동시성을 설정합니다. 기본값은 리스너 컨테이너 팩토리에 의해 설정된 값을 재정의한다. 
     * @param message 메시지 
     */
    @RabbitListener(queues = "workerTwo")
    public void workerTwoMessage(String message) {
        // wokerTwo으로 받은 메시지를 출력한다.
        System.out.println("workerTwo : "+message);
    }



//    /**
//      * 순차적으로 처리하기 위해 Rabbitmq 큐에서 푸시할 메시지를 꺼내와 FCM 에 발송처리를 한다.
//      * RbbitConfig 에 설정된 mqPushRabbitContainerFactory 사용
//      * @param mPushMessage
//      */
//     @RabbitListener(queues = "${mq-config.push.queue}", containerFactory = "mqPushRabbitContainerFactory")
//     public void consumerMPushMessageQueue(MPushMessageReqDto mPushMessage){
//         try {
//             log.info("● consumerMPushMessageQueue : {}", mPushMessage);
//             mPushService.sendPush(mPushMessage);
//         }catch (Exception e){
//             log.info(e.getMessage());
//         }
//     }



    
}
