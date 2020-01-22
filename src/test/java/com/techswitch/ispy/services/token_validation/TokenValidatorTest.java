package com.techswitch.ispy.services.token_validation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.techswitch.ispy.config.LoginConfig;
import com.techswitch.ispy.services.LoginService;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class TokenValidatorTest {

    private LoginConfig loginConfig = new LoginConfig("","");


    @Test
    public void testValidatesTokenWithCorrectSigner(){
        LoginService loginService = new LoginService(loginConfig,"samplesigner");
        TokenValidator tokenValidator = new TokenValidator("samplesigner");
        String token = loginService.getToken();
        assertThat(tokenValidator.validateToken(token)).isTrue();
    }

    @Test
    public void testInvalidatesTokenWithDifferentSigner(){
        LoginService loginService = new LoginService(loginConfig,"differentsigner");
        TokenValidator tokenValidator = new TokenValidator("samplesigner");
        String token = loginService.getToken();
        assertThat(tokenValidator.validateToken(token)).isFalse();
    }

    @Test
    public void testInvalidatesExpiredToken(){

        Algorithm algorithm = Algorithm.HMAC256("samplesigner");

        Date date = new Date(System.currentTimeMillis() - (1000));
        String token = JWT.create()
                .withIssuer("techswitch-ispy")
                .withExpiresAt(date)
                .sign(algorithm);

        TokenValidator tokenValidator = new TokenValidator("samplesigner");

        assertThat(tokenValidator.validateToken(token)).isFalse();
    }

    @Test
    public void returnsFalseIfTokenNull(){
        TokenValidator tokenValidator = new TokenValidator("samplesigner");
        assertThat(tokenValidator.validateToken(null)).isFalse();
    }
}