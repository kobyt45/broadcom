package com.example.restapi.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@Data
public class Bonus {
    @NonNull
    private String bonus = "";
}