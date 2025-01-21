package com.soutenence.publiciteApp.ResponseAndRequest;

import lombok.Builder;

import java.util.List;

@Builder
public class PageResponse<T> {
    private List<T> content;
    private int nomber;
    private int size;
    private long totalElements;
    private int totalPage;
    private boolean firstPage;
    private  boolean lastPage;

    public PageResponse() {
    }

    public PageResponse(List<T> content, int nomber, int size, long totalElements, int totalPage, boolean firstPage, boolean lastPage) {
        this.content = content;
        this.nomber = nomber;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPage = totalPage;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getNomber() {
        return nomber;
    }

    public void setNomber(int nomber) {
        this.nomber = nomber;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }
}
