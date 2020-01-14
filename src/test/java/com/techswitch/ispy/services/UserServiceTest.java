package com.techswitch.ispy.services;

import com.techswitch.ispy.config.IntegrationTestConfig;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = IntegrationTestConfig.class)
class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    void getUsernameById() {
        Jdbi jdbi = userService.getJdbi();
        jdbi.withHandle(handle -> handle.execute("CREATE TABLE users (id serial primary key, username varchar(100) not null unique);"));
        jdbi.withHandle(handle -> handle.execute("INSERT INTO users (id,username) VALUES (1,'Tom09');"));

        String result = userService.getUsernameById(1);
        assertThat(result).isEqualTo("Tom09");

        jdbi.withHandle(handle -> handle.execute("DROP TABLE users;"));
    }

}