package com.techswitch.ispy;

import org.springframework.stereotype.Component;

@Component
public class Filter {

    private Integer page;
    private Integer pageSize;

    public Filter() {
        this.page = 1;
        this.pageSize = 10;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getOffset() {
        return (page - 1) * pageSize;
    }

}
