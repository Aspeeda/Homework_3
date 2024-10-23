package model;

import lombok.Data;

public @Data class UserModel {
    private String username, firstName, lastName, email, password, phone;
    private int id, userStatus;
  }
