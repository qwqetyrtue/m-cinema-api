package cn.hnist.sharo.mcinema.core.model;


public class PagingRec {
    private int pageNum = 1;
    private int pageSize = 10;

    public PagingRec() {
    }

    public PagingRec(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
