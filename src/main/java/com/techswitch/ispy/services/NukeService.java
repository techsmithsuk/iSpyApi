package com.techswitch.ispy.services;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NukeService {

    private Jdbi jdbi;

    @Autowired
    public NukeService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void truncateAllRowsInAllTables() {
        String SQL =    "TRUNCATE suspects, suspect_photo_urls, reports RESTART IDENTITY;";
        jdbi.withHandle(handle -> handle.createUpdate(SQL).execute());
    }
}
