package com.group.artifact.stateStarter;

import com.group.artifact.service.SlackService;
import com.group.artifact.state.concrete.*;
import com.group.artifact.state.container.StateContainer;
import com.group.artifact.state.State;

import java.util.function.BiFunction;

public enum Command {
    BOOK_INFO(BookInfoState::new, "!정보"),
    REVIEW_READ(ReviewRead::new, "!리뷰조회"),
    REVIEW_CREATE(ReviewUpdate::new, "!리뷰등록"),
    REVIEW_UPDATE(ReviewUpdate::new, "!리뷰수정"),
    REVIEW_DELETE(ReviewDelete::new, "!리뷰삭제"),
    NO_COMMAND(DoNothing::new, null),
    DO_NOTHING(DoNothing::new, null),
    EXIT_COMMAND_MODE(ExitCommand::new, "!탈출"),
    ALL_BOOK(AllBook::new, "!모든책"),
    MY_REVIEWS(MyReviews::new, "!나의리뷰"),
    USAGE(Usage::new, "!사용법");


    private BiFunction<SlackService, StateContainer, State> stateIniter;
    private String matcher;

    Command(BiFunction<SlackService, StateContainer, State> stateIniter, String matcher) {
        this.stateIniter = stateIniter;
        this.matcher = matcher;
    }

    public static Command matchCommand(String text) {
        if (text == null) {
            return Command.NO_COMMAND;
        }

        for (Command command : values()) {
            if (command.matcher != null && text.contains(command.matcher)) {
                return command;
            }
        }
        return Command.NO_COMMAND;
    }

    public State initState(SlackService service, StateContainer container) {
        return this.stateIniter.apply(service, container);
    }

    public String removeMatcher(String text) {
        if (this.matcher == null) {
            return text;
        }
        return text.replace(this.matcher, "");
    }

    public String getMatcher() {
        if (this.matcher == null) {
            return "";
        }
        return this.matcher;
    }
}
