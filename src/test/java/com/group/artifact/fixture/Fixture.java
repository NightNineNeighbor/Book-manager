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

    public static User nnn;
    public static User mmm;
    public static Review review;
    public static Review review2;
    public static Book book;
    static{
        nnn = new User(1, "UD7QN97QS", new ArrayList<>());
        review = new Review(1, "어떻게 하면 실패하는 순간을 잘 극복하고 프로그래밍 공부를 지속가능하고 재미있게 할 수 있는지에 관한 저자들의 경험 이야기다.",
                null, nnn);
        book = new Book(1,
                "성공하는프로그래밍공부법",
                "프롤로그\n1장 프로그래밍 공부법 \n동의되지 않는 권위에 굴복하지 않기 \n__지식 중심의 공부법이라는 권위에 도전하기 \n__내가 프로그래밍을 공부하는 방법은? \n__내가 권위에 집착하는 이유 \n프로그래머가 내 직업이 되기까지 \n__프로그래밍 공부가 어려운 이유는? \n__각 단계를 극복하는 방법 \n전문가로 성장하기 위한 의식적인 연습 \n__의식적인 연습 \n__지속적인 성장을 위한 의식적인 연습 \n행복한 프로그래머 \n__일(노동)을 바라보는 관점의 변화 \n__몰입을 통한 행복한 삶 \n__작은 성공 \n변화를 만드는 데 도움을 주는 책",
                "포비포비",
                new ArrayList<>(),
                "https://bookthumb-phinf.pstatic.net/cover/139/936/13993648.jpg?type=m1&udate=20180918");
        book.getReviews().add(review);
        review.setBook(book);
        nnn.getReview().add(review);

        mmm = new User(2, "UD8QN98QS", new ArrayList<>());
        review2 = new Review(2, "두번째 리뷰", book, mmm);
        mmm.getReview().add(review2);
        book.getReviews().add(review2);
    }
}
