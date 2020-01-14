package com.techswitch.ispy.services;

import com.techswitch.ispy.models.Sample;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService {
    private Jdbi jdbi;

    @Autowired
    public SampleService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<Sample> getSamples() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM sample")
                .mapToBean(Sample.class)
                .list());
    }
}
