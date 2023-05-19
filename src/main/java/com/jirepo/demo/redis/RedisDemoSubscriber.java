package com.jirepo.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jirepo.core.json.JsonUtils;



/**
 * Redis Subscriber Demo 
 */
@Service
public class RedisDemoSubscriber implements MessageListener {
    
    /** topic 메시지를 deserialize하기 위해 RedisTemplate 주입 */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // pattern : “pattern matching the channel (if specified)”, but that it can be null.
        try {
            String msg = (String)redisTemplate.getStringSerializer().deserialize(message.getBody());
            System.out.println(msg);
            RedisDemoMessage data = (RedisDemoMessage)JsonUtils.toObject(msg, RedisDemoMessage.class);
            System.out.println(data.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//:

    
}
