package com.group.artifact.vo;

public class CrawledBook {
    public static final CrawledBook EMPTY = new CrawledBook("EMPTY", null, null, null);
    private String title;
    private String link;
    private String image;
    private String author;

    public CrawledBook() {
    }

    public CrawledBook(String title, String link, String image, String author) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.author = author;
    }

    public String getTitle() {
        return title.replace("<b>", "").replace("</b>", "");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    @Override
    public String toString() {
        return "CrawledBook{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
