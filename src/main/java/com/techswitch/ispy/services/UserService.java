package com.techswitch.ispy.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService extends DatabaseService {


    @Autowired
    public UserService(@Qualifier("databaseUrl") String url) {
        super(url);
    }

    public String getUsernameById(int id) {
        String username = jdbi.withHandle(handle -> handle.createQuery("SELECT username FROM users WHERE id = :id")
                .bind("id", id)
                .mapTo(String.class)
                .findOnly());
        return username;
    }
}
