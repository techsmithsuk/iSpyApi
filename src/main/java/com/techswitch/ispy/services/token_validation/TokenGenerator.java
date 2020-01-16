package com.techswitch.ispy.services.token_validation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.util.Date;

public class TokenGenerator {

    private static Long hour = 1000L * 60L * 60L;

    public static String createToken() throws JWTCreationException {

        Algorithm algorithm = Algorithm.HMAC256(System.getenv("SIGNER"));
        Date date = new Date(hour + System.currentTimeMillis());
        String token = JWT.create()
                .withIssuer("techswitch-ispy")
                .withExpiresAt(date)
                .sign(algorithm);
        return token;
    }
}