package com.techswitch.ispy.services;


import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class UserService {
    private Jdbi jdbi;

    @Autowired
    public UserService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public String getUsernameById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT username FROM users WHERE id = :id")
                .bind("id", id)
                .mapTo(String.class)
                .one());
    }
}
