package com.techswitch.ispy.models.api;

import com.techswitch.ispy.Filter;
import com.techswitch.ispy.models.database.SuspectDatabaseModel;

import java.util.List;

public class SuspectListResponseModel extends ListResponseModel<SuspectDatabaseModel, Filter>{
    public SuspectListResponseModel(List<SuspectDatabaseModel> suspectList, Filter filter, int numberOfSuspects) {
        super(suspectList, filter, numberOfSuspects);
    }

    @Override
    protected String getRootUrl() {
        return "/suspects";
    }
}
