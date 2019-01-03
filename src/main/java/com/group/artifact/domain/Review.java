package com.group.artifact.domain;


import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob
    private String review;
    @ManyToOne
    private Book book;
    @ManyToOne
    private User writer;

    public Review(){}

    public Review(long id, String review, Book book, User writer) {
        this.id = id;
        this.review = review;
        this.book = book;
        this.writer = writer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getWrappedId() {
        return writer.getWrappedSlackId();
    }

    public boolean isSameUser(User user) {
        return id == user.getId();
    }


    public boolean isSameUser(String slackId) {
        return slackId.equals(writer.getSlackId());
    }
}
