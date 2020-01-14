package com.techswitch.ispy.services;

import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private static UserService userService;
    private static Jdbi jdbi;

    @BeforeAll
    static void init() {
        jdbi = Jdbi.create("jdbc:h2:~/test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE");
        userService = new UserService(jdbi);

        jdbi.withHandle(handle -> handle.execute("CREATE TABLE users (id int serial primary key, username varchar(100) not null unique);"));
        jdbi.withHandle(handle -> handle.execute("INSERT INTO users (id,username) VALUES (1,'Tom09');"));
    }

    @AfterAll
    static void dropTable() {
        jdbi.withHandle(handle -> handle.execute("DROP TABLE users;"));
    }

    @Test
    void getUsernameById() {
        String result = userService.getUsernameById(1);
        assertThat(result).isEqualTo("Tom09");
    }

}