package com.group.artifact.helper;

import com.group.artifact.domain.Book;
import com.group.artifact.domain.Review;
import com.group.artifact.format.Attachments;
import com.group.artifact.format.Info;
import com.group.artifact.stateStarter.Command;
import com.group.artifact.vo.SlackAcceptor;
import com.group.artifact.vo.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RequestCreator {
    @Autowired
    private Token token;

    public HttpEntity<Void> crawlingHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", token.getNaverClientId());
        headers.add("X-Naver-Client-Secret", token.getNaverClientSecret());
        return new HttpEntity<>(headers);
    }

    public HttpEntity<Map<String, String>> echo(SlackAcceptor acceptor) {
        return simpleText(acceptor.getEchoText(), acceptor.getChannel());
    }

    public HttpEntity<Attachments> bookInfo(Book book, String channel) {
        HttpHeaders header = getJsonHeaders();

        Attachments bookInfo = Attachments.ofBookInfo(book, channel);
        return new HttpEntity<>(bookInfo, header);
    }

    public HttpEntity<Attachments> review(List<Review> reviews, String channel) {
        Attachments attachments = new Attachments(channel);
        for (Review review : reviews) {
            attachments.add(new Info("", review.getReview()));
        }
        return new HttpEntity<>(attachments,
                getJsonHeaders());
    }

    public HttpEntity<Map<String, String>> simpleText(String text, String channel) {
        Map<String, String> map = new HashMap<>();
        map.put("channel", channel);
        map.put("text", text);

        return new HttpEntity<>(map, getJsonHeaders());
    }

    public HttpEntity<Attachments> sendOneReview(Review review, String channel) {
        Attachments attachments = new Attachments(channel);
        attachments.add(new Info("책 이름", review.getBook().getTitle()));
        attachments.add(new Info("리뷰", review.getReview()));
        return new HttpEntity<>(attachments,
                getJsonHeaders());
    }

    private HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token.getBotToken());
        return headers;
    }

    public HttpEntity<Attachments> usage(String channel) {
        Attachments attachments = new Attachments(channel);
        attachments.add(new Info("사용법", Command.USAGE.getMatcher()));
        attachments.add(new Info("명령 초기화",Command.EXIT_COMMAND_MODE.getMatcher()));
        attachments.add(new Info("도서 정보","도서명 "+Command.BOOK_INFO.getMatcher()));
        attachments.add(new Info("리뷰 조회","도서명 "+Command.REVIEW_READ.getMatcher()));
        attachments.add(new Info("리뷰 등록","도서명 "+Command.REVIEW_CREATE.getMatcher()));
        attachments.add(new Info("리뷰 삭제","도서명 "+Command.REVIEW_DELETE.getMatcher()));
        attachments.add(new Info("리뷰 수정","도서명 "+Command.REVIEW_UPDATE.getMatcher()));
        attachments.add(new Info("모든 도서 리스트",Command.ALL_BOOK.getMatcher()));
        attachments.add(new Info("내가 쓴 리뷰",Command.MY_REVIEWS.getMatcher()));
        return new HttpEntity<>(attachments, getJsonHeaders());
    }

    public HttpEntity<Attachments> reviewWithBook(List<Review> reviews, String channel) {
        Attachments attachments = new Attachments(channel);
        for (Review review : reviews) {
            attachments.add(new Info(review.getBook().getTitle(), review.getReview()));
        }
        return new HttpEntity<>(attachments,
                getJsonHeaders());
    }
}
