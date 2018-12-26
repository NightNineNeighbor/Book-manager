package com.group.artifact.fixture;

import com.group.artifact.domain.Book;
import com.group.artifact.vo.SlackAcceptor;
import com.group.artifact.vo.SlackEvent;

public class Fixture {
    public static SlackAcceptor echo;
    static{
        SlackEvent event = new SlackEvent();
        event.setChannel("CD7KGTJ3E");
        event.setUser("UD7QN97QS");
        event.setText("test text");

        echo = new SlackAcceptor();
        echo.setEvent(event);
    }

    public static Book book;
    static{
        book = new Book();
        book.setTitle("성공하는프로그래밍공부법");
        book.setAuthor("포비포비");
        book.setContents("프롤로그\n1장 프로그래밍 공부법 \n동의되지 않는 권위에 굴복하지 않기 \n__지식 중심의 공부법이라는 권위에 도전하기 \n__내가 프로그래밍을 공부하는 방법은? \n__내가 권위에 집착하는 이유 \n프로그래머가 내 직업이 되기까지 \n__프로그래밍 공부가 어려운 이유는? \n__각 단계를 극복하는 방법 \n전문가로 성장하기 위한 의식적인 연습 \n__의식적인 연습 \n__지속적인 성장을 위한 의식적인 연습 \n행복한 프로그래머 \n__일(노동)을 바라보는 관점의 변화 \n__몰입을 통한 행복한 삶 \n__작은 성공 \n변화를 만드는 데 도움을 주는 책");
    }

    public static String channel = "CD7KGTJ3E";

}
