package com.techswitch.ispy.services;

import com.techswitch.ispy.models.Sample;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class SampleService extends DatabaseService {

    public SampleService(DataSource dataSource) {
        super(dataSource);
    }

    public List<Sample> getSamples() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM sample")
                .mapToBean(Sample.class)
                .list());
    }
}
