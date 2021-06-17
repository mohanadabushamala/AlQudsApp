package com.example.alqudsapp;

public class LastNewsItem {

    private String title;
    private String urlToImage;
    private String url;

    public LastNewsItem(String title, String urlToImage, String url) {
        this.title = title;
        this.urlToImage = urlToImage;
        this.url = url;
    }

    public LastNewsItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
