package com.sxdx.common.util;


import lombok.Data;

import java.util.List;

@Data
public class Page {

    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //总记录数
    private long total;
    //总页数
    private int pages;

    private int firstResult;
    private int maxResults;

    //结果集
    private Object list;

    public Page(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.firstResult = (pageNum-1) * pageSize;
        this.maxResults = pageSize;
    }
}
