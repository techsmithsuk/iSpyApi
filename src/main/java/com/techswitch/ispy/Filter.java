package com.techswitch.ispy;

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

    public Integer getOffset() {
        return (page - 1) * pageSize;
    }

}
