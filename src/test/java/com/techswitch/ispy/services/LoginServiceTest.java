package com.techswitch.ispy.services;

import com.techswitch.ispy.config.LoginConfig;
import com.techswitch.ispy.models.LoginDetails;
import com.techswitch.ispy.services.token_validation.TokenValidator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginServiceTest {

    private String correctUsername = "testUsername";
    private String correctPassword = "testPassword";
    private String wrongUsername = "wrongUsername";
    private String wrongPassword = "wrongPassword";
    private String testSigner = "testSigner!£$%^&";

    private LoginConfig loginConfig = new LoginConfig(correctUsername, correctPassword);
    private LoginService loginService = new LoginService(loginConfig, testSigner);

    @Test
    public void loginDetailsAreValid() {
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setUsername(correctUsername);
        loginDetails.setPassword(correctPassword);
        assertThat(loginService.validateLogin(loginDetails)).isTrue();
    }

    @Test
    public void loginDetailsAreInvalid() {
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setUsername(wrongUsername);
        loginDetails.setPassword(wrongPassword);
        assertThat(loginService.validateLogin(loginDetails)).isFalse();
    }

    @Test
    public void usernameIsInvalid() {
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setUsername(wrongUsername);
        loginDetails.setPassword(correctPassword);
        assertThat(loginService.validateLogin(loginDetails)).isFalse();
    }

    @Test
    public void passwordIsInvalid() {
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setUsername(correctUsername);
        loginDetails.setPassword(wrongPassword);
        assertThat(loginService.validateLogin(loginDetails)).isFalse();
    }

    @Test
    public void createValidToken(){
        TokenValidator tokenValidator = new TokenValidator(testSigner);
        assertThat(tokenValidator.validateToken(loginService.getToken()));
    }

}