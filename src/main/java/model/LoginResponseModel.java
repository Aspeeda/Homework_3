package model;

import lombok.Data;

public @Data class LoginResponseModel {
    private Long code;
    private String message, type;

}
