package com.isoft.bean;

import java.util.List;

public class Page<T> {
    /**
     * 记录总数
     */
    private Integer total ;
    /**
     * 页码总数
     */
    private Integer pageCount ;
    /**
     * 当前页
     */
    private Integer page ;
    /**
     * 每页记录个数
     */
    private Integer size ;
    /**
     * 当前页数据
     */
    private List<T> records ;

    public Page() {
    }

    public Page(Integer total, Integer pageCount, Integer page, Integer size, List<T> records) {
        this.total = total;
        this.pageCount = pageCount;
        this.page = page;
        this.size = size;
        this.records = records;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "Page{" +
                "total=" + total +
                ", pageCount=" + pageCount +
                ", page=" + page +
                ", size=" + size +
                ", records=" + records +
                '}';
    }
}
