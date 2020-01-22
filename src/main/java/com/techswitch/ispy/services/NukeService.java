package com.techswitch.ispy.services;

import com.sun.security.ntlm.Client;
import com.techswitch.ispy.Filter;
import com.techswitch.ispy.models.database.SuspectDatabaseModel;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NukeService {

    private Jdbi jdbi;

    @Autowired
    public NukeService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void truncateAllRowsInAllTables() {

        String SQL =    "TRUNCATE TABLE suspects; \n" +
                        "TRUNCATE TABLE suspect_photo_urls; \n" +
                        "TRUNCATE TABLE reports; \n";

        Handle client = jdbi.open();
        jdbi.withHandle(handle -> handle.createUpdate(SQL).execute());
        client.close();
    }


}
