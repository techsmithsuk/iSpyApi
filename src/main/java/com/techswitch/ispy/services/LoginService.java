package com.techswitch.ispy.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.techswitch.ispy.config.AppConfig;
import com.techswitch.ispy.config.LoginConfig;
import com.techswitch.ispy.models.LoginDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

@Service
public class LoginService {

    private LoginConfig config;
    private String signer;
    private Long hour = 1000L * 60L * 60L;

    @Autowired
    public LoginService(LoginConfig config, @Qualifier("signer") String signer) {
        this.config = config;
        this.signer = signer;
    }

    private boolean isUsernameValid(String username){
        boolean isValid = username.equals(config.getAdminUsername());
        return isValid;
    }

    private boolean isPasswordValid(String password){
        boolean isValid = password.equals(config.getAdminPassword());
        return isValid;
    }

    public boolean validateLogin(LoginDetails loginDetails){
        return isUsernameValid(loginDetails.getUsername()) && isPasswordValid(loginDetails.getPassword());
    }

    public String getToken(){
        Algorithm algorithm = Algorithm.HMAC256(signer);
        Date date = new Date(hour + System.currentTimeMillis());
        String token = JWT.create()
                .withIssuer("techswitch-ispy")
                .withExpiresAt(date)
                .sign(algorithm);
        return token;
    }

}