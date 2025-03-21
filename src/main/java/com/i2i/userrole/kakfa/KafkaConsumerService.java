package com.i2i.userrole.kakfa;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class
KafkaConsumerService {
    @KafkaListener(topics = "my_topic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Received Message: " + message);
    }
}
