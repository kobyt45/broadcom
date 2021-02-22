package com.example.restapi.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
@Data
public class BroadcomStats {
    @NonNull
    private int nOfTotalMessagesReceived = 0;
    @NonNull
    private HashMap<String, Integer> nOfMessagesByTypeMap;
    @NonNull
    private String bonusDecoded = "";

}