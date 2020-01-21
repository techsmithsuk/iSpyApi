package com.techswitch.ispy.services.token_validation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TokenValidator {

    private String signer;

    @Autowired
    public TokenValidator(@Qualifier("signer") String signer) {
        this.signer = signer;
    }

    public boolean validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(signer);
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
