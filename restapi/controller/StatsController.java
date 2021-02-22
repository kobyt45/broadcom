package com.example.restapi.controller;

import com.example.restapi.model.BroadcomStats;
import com.example.restapi.utils.MessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {

    @Autowired
    MessageReceiver messageReceiver;
    @Autowired
    BroadcomStats broadcomStats;

    @GetMapping("/v1/stats")
    public BroadcomStats get(){
        messageReceiver.decodeBonus();
        return broadcomStats;
    }
}
