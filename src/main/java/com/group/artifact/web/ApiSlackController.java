package com.group.artifact.web;

import com.group.artifact.service.SlackService;
import com.group.artifact.service.ServiceResolver;
import com.group.artifact.vo.SlackAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiSlackController {
    private static final Logger logger = LoggerFactory.getLogger(ApiSlackController.class);

    @Autowired
    private SlackService slackService;

    //todo : change mapping url
    @PostMapping("/valid")
    public String sortingHat(@RequestBody SlackAcceptor acceptor) { //Harry Potter
        if (acceptor.isChallenge()) {
            return acceptor.getChallenge();
        }

        ServiceResolver serviceResolver = acceptor.parse();
        return serviceResolver.doSomething(slackService);


//        TextAndAction textAndAction = acceptor.extractAction();
//        String text = textAndAction.getText();
//        Action action = textAndAction.loadAction();
//
//        if (slackService.isBookName(text)) {
//            textAndAction.resetAction();
//            if (action == Action.REVIEW_READ) {
//                slackService.sendReview(text, acceptor.getChannel());
//                return "READ REVIEW";
//            } else if (action == Action.REVIEW_CREATE || action == Action.REVIEW_UPDATE) {
//                textAndAction.saveBookName();
//                textAndAction.makeReadyState(action);
//                slackService.askReviewContents(acceptor.getChannel());
//                return "ASK REVIEW CONTENTS";
//            } else if (action == Action.REVIEW_DELETE) {
//                slackService.delete(text, textAndAction.getUser(), acceptor.getChannel());
//                return "DELETE REVIEW";
//            } else {
//                slackService.sendBookInfo(text, acceptor.getChannel());
//                return "SEND BOOK INFO";
//            }
//        } else {
//            if (action == Action.REVIEW_READ || action == Action.REVIEW_DELETE) {
//                slackService.askBookName(acceptor.getChannel());
//                return "ASK BOOK NAME";
//            } else if (action == Action.REVIEW_CREATE || action == Action.REVIEW_UPDATE) {
//                textAndAction.saveBookName();
//                slackService.askBookName(acceptor.getChannel());
//                return "ASK BOOK NAME";
//            } else if (action == Action.DoReviewCreate || action == Action.DoReviewUpdate) {
//                slackService.updateReview(textAndAction.loadBookName(), text, textAndAction.getUser());
//                textAndAction.resetAction();
//                return "DO REVIEW UPDATE OR CREATE";
//            }
//
//        }

//        if (acceptor.isUserMessageEvent()) {
//            slackService.echo(acceptor);
//            return "ECHO";
//        }
//
//        if (acceptor.isChallenge()) {
//            return acceptor.getChallenge();
//        }
//
//        return "NOT_MATCH";
    }
}
