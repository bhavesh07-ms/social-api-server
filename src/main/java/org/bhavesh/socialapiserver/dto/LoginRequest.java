package org.bhavesh.socialapiserver.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}