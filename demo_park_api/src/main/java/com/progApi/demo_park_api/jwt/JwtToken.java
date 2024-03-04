package com.progApi.demo_park_api.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtToken {
    private String token;

    public JwtToken(String token) {
    }
}
