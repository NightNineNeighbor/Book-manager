package com.group.artifact.format;

import com.group.artifact.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookInfo {
    private String channel;
    private List<Info> attachments = new ArrayList<>();

    public static BookInfo of(Book book, String channel) {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setChannel(channel);
        bookInfo.add(new Info(book.getTitle(), book.getBestReview()));
        bookInfo.add(new Info("저자", book.getAuthor()));
        bookInfo.add(new Info("목차", book.getContents()));
        return bookInfo;
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
