package com.techswitch.ispy.services;

import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;

public abstract class DatabaseService {
    protected final Jdbi jdbi;

    protected DatabaseService(DataSource dataSource){
        this.jdbi = Jdbi.create(dataSource);
    }

}
