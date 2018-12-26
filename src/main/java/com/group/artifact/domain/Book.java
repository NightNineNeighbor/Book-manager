package com.group.artifact.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @Id
    private long id;
    private String title;
    @Lob
    private String contents;
    private String author;
    @OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //todo
    public String getContents() {
        return contents.replace("\\n", "\n");
    }

    public void setContents(String contents) {
        this.contents = contents.replace("\\n", "\n");
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBestReview() {
        if (reviews.size() == 0) {
            return "등록된 리뷰가 없습니다";
        }
        return reviews.get(0).getReview();
    }
}
