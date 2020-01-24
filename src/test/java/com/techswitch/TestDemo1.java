package com.techswitch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"testDataSource","testSigner","testLoginConfig"})
public class TestDemo1 {


}
