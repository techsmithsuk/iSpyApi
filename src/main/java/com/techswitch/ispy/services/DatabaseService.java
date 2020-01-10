package com.techswitch.ispy.services;

import org.jdbi.v3.core.Jdbi;

public abstract class DatabaseService {
    protected final Jdbi jdbi;

    protected DatabaseService(String url){
        this.jdbi = Jdbi.create(url);
    }

}
