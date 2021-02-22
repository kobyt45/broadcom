package com.example.restapi.listener;

import com.example.restapi.model.BroadcomMessage;
import com.example.restapi.utils.MessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service
public class KafkaConsumer {

    @Autowired
    MessageReceiver messageReceiver;
    private static final Logger LOGGER = Logger.getLogger(KafkaConsumer.class.getName());

    @KafkaListener(topics = "tdad.sw.interview", groupId = "S8Y9R5PUQL")
    public void consumeJson(String message){

        LOGGER.info("Consumed JSON Message: " + message);

        BroadcomMessage broadcomMessage = messageReceiver.convertToMessageObject(message);
        if (broadcomMessage != null && broadcomMessage.isValid()){
            messageReceiver.messageReceived(broadcomMessage);
        }
        else{
            LOGGER.warning("message is not " + BroadcomMessage.class.toString());
        }
    }
}