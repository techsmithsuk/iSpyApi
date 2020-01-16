package com.techswitch.ispy.services.token_validation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenValidator {

    public static boolean validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(System.getenv("SIGNER"));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("techswitch-ispy")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception){
            //Invalid signature/claims
            return false;
        }
    }
}
