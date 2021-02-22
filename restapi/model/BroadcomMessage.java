package com.example.restapi.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class BroadcomMessage {
    @NonNull
    private String message;
    @NonNull
    private String typeId;
    @NonNull
    private String secret;

    public boolean isValid(){
        return this.message != null && this.typeId != null && this.secret != null;
    }
}