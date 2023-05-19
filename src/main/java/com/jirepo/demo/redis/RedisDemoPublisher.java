package com.jirepo.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.jirepo.core.json.JsonUtils;
import com.jirepo.core.web.exception.BaseBizErrorCode;
import com.jirepo.core.web.exception.BaseBizException;


/**
 * 토픽을 발행한다. 
 */
@Service
public class RedisDemoPublisher {
    /** topic을 발행하기 위해 RedisTemplate 주입 */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    
    /**
     * 토픽을 발행한다. 
     * @param topic 발행할 토픽
     * @param message   발행할 메세지
     */
    public void publish(ChannelTopic topic, String message) {
        RedisDemoMessage msg = new RedisDemoMessage();
        msg.setMessage(message);
        try { 
            redisTemplate.convertAndSend(topic.getTopic(), JsonUtils.toJSON(msg));
        }catch(Exception e) {
            e.printStackTrace();
            throw new BaseBizException(BaseBizErrorCode.INTERNAL_SERVER_ERROR); 
        }
    }
}///~
