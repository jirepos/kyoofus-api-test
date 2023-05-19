package com.jirepo.demo.redis;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo/redis")
public class RedisDemoController {

    @Autowired
    private RedisMessageListenerContainer redisMessageListenerContainer;
    @Autowired
    private RedisDemoPublisher redisDemoPublisher;
    @Autowired
    private RedisDemoSubscriber redisDemoSubscriber;
    @Autowired
    private RedisDemoService redisDemoService;

    private Map<String, ChannelTopic> channels;

    @PostConstruct
    public void init() {
        channels = new HashMap<>();
    }

    /**
     * 토픽 구독을 생성한다.
     * 
     * @param name 토픽 이름
     */
    @GetMapping("/topics/subscribe/{name}")
    public ResponseEntity<String> createTopic(@PathVariable String name) {
        ChannelTopic channel = new ChannelTopic(name);
        // listner를 등록
        redisMessageListenerContainer.addMessageListener(redisDemoSubscriber, channel);
        channels.put(name, channel); // 채널을 저장한다.

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>("Subscribed", headers, HttpStatus.OK);

    }

    /**
     * 메세지 발생
     * 
     * @param name    토픽 이름
     * @param message 발행 메시지
     */
    @GetMapping("/topics/publish/{name}/{message}")
    public ResponseEntity<String> pushMessage(@PathVariable String name, @PathVariable String message) {
        ChannelTopic channel = channels.get(name);
        redisDemoPublisher.publish(channel, message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>("Published", headers, HttpStatus.OK);
    }

    /**
     * Topic을 제거한다. 구독을 취소한다. 
     * 
     * @param name 토픽 이름
     */
    @GetMapping("/topics/remove/{name}")
    public ResponseEntity<String> deleteTopic(@PathVariable String name) {
        ChannelTopic channel = channels.get(name);
        redisMessageListenerContainer.removeMessageListener(redisDemoSubscriber, channel); // 토픽 제거
        channels.remove(name);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>("Unsubscribed", headers, HttpStatus.OK);
    }

    /** 도메인만 입력 시 랜딩 페이지 표시 */
    @GetMapping("/")
    public String baseUrl() {
        return "redis/redis";
    }

    /** Redis에 객체 저장 */
    @GetMapping("/set")
    public ResponseEntity<RedisDemoData> getRedisDemoBean(HttpServletRequest request, HttpServletResponse response) {
        RedisDemoData bean = this.redisDemoService.setOperation();
        return new ResponseEntity<RedisDemoData>(bean, HttpStatus.OK);
    }// :

    /** Redis에서 객체 조회 */
    @GetMapping("/get")
    public ResponseEntity<RedisDemoData> setRedisDemoBen(HttpServletRequest request, HttpServletResponse response) {
        RedisDemoData bean = this.redisDemoService.getOperation();
        return new ResponseEntity<RedisDemoData>(bean, HttpStatus.OK);
    }// :

    /**
     * 토픽 구독을 생성한다.
     * 
     * @param name 토픽 이름
     */
    @GetMapping("/delete")
    public ResponseEntity<String> createTopic() {
        this.redisDemoService.delete(); // 삭제
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>("Deleted", headers, HttpStatus.OK);

    }

    /** Redis에 list 저장 */
    @GetMapping("/list")
    public ResponseEntity<List<String>> getList(HttpServletRequest request, HttpServletResponse response) {
        List<String> list = this.redisDemoService.listOperation();
        return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    }// :

    /** Set 연산 */
    @GetMapping("/forset")
    public ResponseEntity<String> forSet(HttpServletRequest request, HttpServletResponse response) {
        this.redisDemoService.forSetOperation();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>("Succeed", HttpStatus.OK);
    }// :

    /** Set 연산 */
    @GetMapping("/sortedset")
    public ResponseEntity<String> sortedSet(HttpServletRequest request, HttpServletResponse response) {
        this.redisDemoService.sortedSetOperation();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>("Succeed", HttpStatus.OK);
    }// :

    /** Set 연산 */
    @GetMapping("/hashset")
    public ResponseEntity<String> hashSet(HttpServletRequest request, HttpServletResponse response) {
        this.redisDemoService.hashSetOperation();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>("Succeed", HttpStatus.OK);
    }// :

}
