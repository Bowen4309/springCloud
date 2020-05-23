package com.sucsoft.toes.bean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Libin
 */
public class Page<T> {

    private List<T> dataList;

    private Integer pageSize;

    private Integer pageNo;

    private Integer rewordCount;

    private Integer pageCount;

    public List<T> getDataList() {
        return dataList;
    }

    public Page(List<T> dataList, Integer rewordCount, Integer pageNo, Integer pageSize){
        this.dataList = dataList;
        this.rewordCount = rewordCount;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.pageCount = (int)Math.ceil(rewordCount * 1.0 / pageSize);
    }

    public Page<T> setDataList(List<T> dataList) {
        this.dataList = dataList;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Page<T> setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Page<T> setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public Integer getRewordCount() {
        return rewordCount;
    }

    public Page<T> setRewordCount(Integer rewordCount) {
        this.rewordCount = rewordCount;
        return this;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public Page<T> setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
        return this;
    }
}
