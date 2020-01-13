package com.techswitch.ispy.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class UserService extends DatabaseService {


    @Autowired
    public UserService(DataSource dataSource) {
        super(dataSource);
    }

    public String getUsernameById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT username FROM users WHERE id = :id")
                .bind("id", id)
                .mapTo(String.class)
                .findOnly());
    }
}
