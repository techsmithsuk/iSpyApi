package com.techswitch.ispy.services;

import com.techswitch.ispy.models.database.SuspectsImagesDatabaseModel;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesService {

    private Jdbi jdbi;

    @Autowired
    public ImagesService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<SuspectsImagesDatabaseModel> getImageBySuspectId(int suspectId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM suspect_photo_urls WHERE suspectId = :suspectId")
                .bind("suspectId", suspectId)
                .mapToBean(SuspectsImagesDatabaseModel.class)
                .list());
    }
}
