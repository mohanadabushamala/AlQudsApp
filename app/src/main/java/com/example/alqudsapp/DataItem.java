package com.example.alqudsapp;

import java.util.List;

public class DataItem {

    private String status;
    private int totalResults;
    List<LastNewsItem> articles;

    public DataItem(String status, int totalResults, List<LastNewsItem> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public DataItem() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<LastNewsItem> getArticles() {
        return articles;
    }

    public void setArticles(List<LastNewsItem> articles) {
        this.articles = articles;
    }
}
