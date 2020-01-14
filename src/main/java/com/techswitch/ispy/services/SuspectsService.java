package com.techswitch.ispy.services;

import com.techswitch.ispy.Filter;
import com.techswitch.ispy.models.Suspect;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuspectsService {
    private Jdbi jdbi;

    @Autowired
    public SuspectsService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<Suspect> getAllSuspects(Filter filter){
        List<Suspect> allSuspects = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM all_suspects LIMIT :limit OFFSET :offset")
                .bind("limit",filter.getPageSize())
                .bind("offset",filter.getOffset())
                .mapToBean(Suspect.class)
                .list());
        return allSuspects;

    }
}
