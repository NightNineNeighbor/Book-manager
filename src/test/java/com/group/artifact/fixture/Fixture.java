package com.group.artifact.fixture;

import com.group.artifact.domain.Book;
import com.group.artifact.domain.Review;
import com.group.artifact.domain.User;
import com.group.artifact.vo.SlackAcceptor;
import com.group.artifact.vo.SlackEvent;

import java.util.ArrayList;

public class Fixture {
    public static String channel = "CD7KGTJ3E";
    public static SlackAcceptor echo;
    static{
        SlackEvent event = new SlackEvent();
        event.setChannel("CD7KGTJ3E");
        event.setUser("UD7QN97QS");
        event.setText("test text");

        echo = new SlackAcceptor();
        echo.setEvent(event);
    }

    public static User defaultUser;
    public static User otherUser;
    public static Review review;
    public static Review review2;
    public static Book book;
    public static Book book2;
    static{
        defaultUser = new User(0, "UD7QN97QS", new ArrayList<>());
        otherUser = new User(0, "UCR3RN38X", new ArrayList<>());
        book = new Book(0,
                "첫 번째 책",
                "첫 번째 목차",
                "첫 번째 책 저자",
                new ArrayList<>(),
                "https://bookthumb-phinf.pstatic.net/cover/139/936/13993648.jpg?type=m1&udate=20180918");
        book2 = new Book(0,
                "두 번째 책",
                "두 번째 목차",
                "두 번째 책 저자",
                new ArrayList<>(),
                "https://example.com");
        review = new Review(0, "첫 번째 리뷰",null, null);
        review2 = new Review(0, "두 번째 리뷰", null, null);
    }

    public static Review reviewOne(){
        return new Review(0, "첫 번째 리뷰",null, null);
    }

    public static Review ReviewTwo(){
        return new Review(0, "두 번째 리뷰",null, null);
    }

}
