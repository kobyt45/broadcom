package com.example.restapi.utils;

import com.example.restapi.listener.KafkaConsumer;
import com.example.restapi.model.Bonus;
import com.example.restapi.model.BroadcomMessage;
import com.example.restapi.model.BroadcomStats;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.logging.Logger;

@Component
public class MessageReceiver {

    @Autowired
    BroadcomStats broadcomStats;
    @Autowired
    Bonus bonusObj;

    private static final Logger LOGGER = Logger.getLogger(KafkaConsumer.class.getName());

    private void bonusHandler(BroadcomMessage broadcomMessage){
        String mMessage = broadcomMessage.getMessage();
        String mSecret = broadcomMessage.getSecret();
        String bonus = bonusObj.getBonus();
        if (mMessage!=null && mMessage.contains("ConcatMe") && (!mMessage.equals("ConcatMe4"))){
            if (!bonus.contains(mSecret)){
                bonus += mSecret;
                bonusObj.setBonus(bonus);
            }
        }
    }

    public void decodeBonus(){
        broadcomStats.setBonusDecoded(new String(DatatypeConverter.parseBase64Binary(bonusObj.getBonus())));
    }

    public void messageReceived(BroadcomMessage broadcomMessage){
        int total_messages = broadcomStats.getNOfTotalMessagesReceived();
        broadcomStats.setNOfTotalMessagesReceived(++total_messages);

        HashMap<String, Integer> nOfMessagesByTypeMap = broadcomStats.getNOfMessagesByTypeMap();

        String mTypeId = broadcomMessage.getTypeId();
        if (nOfMessagesByTypeMap.containsKey(mTypeId)){
            int countOfTypeId = nOfMessagesByTypeMap.get(mTypeId);
            nOfMessagesByTypeMap.put(mTypeId, ++countOfTypeId);
        }
        else{
            nOfMessagesByTypeMap.put(mTypeId, 1);
        }
        bonusHandler(broadcomMessage);
    }

    public BroadcomMessage convertToMessageObject(String message){
        try {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(message, BroadcomMessage.class);

        } catch (JsonSyntaxException e) {
            LOGGER.warning("Not a JSON");
        }catch (NullPointerException e){
            LOGGER.warning("Wrong keys/values");
        }
        return null;
    }
}