package com.group.artifact.domain;

import com.group.artifact.vo.CrawledBook;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 1)
    private String title;
    @Lob
    private String contents;
    @NotNull
    private String author;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)//todo
    private List<Review> reviews = new ArrayList<>();
    private String image;

    public Book() {
    }

    public Book(long id, @Size(min = 1) String title, String contents, @NotNull String author, List<Review> reviews, String image) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.reviews = reviews;
        this.image = image;
    }

    public static Book of(CrawledBook crawledBook, String contents) {
        return new Book(0, crawledBook.getTitle(), contents, crawledBook.getAuthor(), new ArrayList<>(), crawledBook.getImage());
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBestReview() {
        if (reviews.size() == 0) {
            return "등록된 리뷰가 없습니다";
        }
        return reviews.get(0).getReview();
    }

    public void deleteByUser(String user) {
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).isSameUser(user)) {
                reviews.remove(i);
                break;
            }
        }
    }

    public void update(User user, String text) {
        for (Review review : reviews) {
            if (review.isSameUser(user)) {
                review.setReview(text);
                break;
            }
        }
    }

    public Review getReviewsBySlackId(String slackId) {
        for (Review review : reviews) {
            if (review.isSameUser(slackId)) {
                return review;
            }
        }
        return null;
    }

    public Review addReview(Review review) {
        reviews.add(review);
        return review;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", author='" + author + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public boolean containInTitle(String s) {
        return this.title.contains(s);
    }

}
