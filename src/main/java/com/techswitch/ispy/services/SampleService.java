package com.techswitch.ispy.services;

import com.techswitch.ispy.models.Sample;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService extends DatabaseService {

    public SampleService(String url) {
        super(url);
    }

    public List<Sample> getSamples() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM samples"))
                .mapTo(Sample.class)
                .list();
    }
}
