package com.group.artifact.format;

import com.group.artifact.domain.Book;
import com.group.artifact.domain.Review;

import java.util.ArrayList;
import java.util.List;

public class Attachments {
    private String channel;
    private List<Info> attachments = new ArrayList<>();

    public Attachments(){}

    public Attachments(String channel) {
        this.channel = channel;
    }

    public static Attachments ofBookInfo(Book book, String channel) {
        Attachments bookInfo = new Attachments(channel);
        bookInfo.add(new Info("제목", book.getTitle()));
        bookInfo.add(new Info("저자", book.getAuthor()));
        bookInfo.add(new Info("목차", book.getContents()));

        List<Review> reviews = book.getReviews();
        for (int i = 0; i < reviews.size(); i++) {
            bookInfo.add(new Info("리뷰 " + (i + 1), reviews.get(i).getReview()));
        }

        return bookInfo;
    }

    public static Attachments ofReview(List<Review> reviews, String channel) {
        Attachments reviewInfo = new Attachments(channel);
        for (Review review : reviews) {
            reviewInfo.add(new Info(review.getWrappedId(), review.getReview()));
        }
        return reviewInfo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<Info> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Info> attachments) {
        this.attachments = attachments;
    }

    public List<Info> add(Info info) {
        attachments.add(info);
        return attachments;
    }
}
