package com.psldemo.gateway.models;

import lombok.*;

import java.util.Collection;


//@Getter
//@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String userId;
    private String accessToken;
    private String refreshToken;

    private long expireAt;

    private Collection<String> authorities;
    
    
}
