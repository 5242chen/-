package model;


public class WebPage {
    private String url;
    private String title;

    public WebPage(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public WebPage() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}