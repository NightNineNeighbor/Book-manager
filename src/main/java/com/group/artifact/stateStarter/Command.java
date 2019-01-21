package com.group.artifact.stateStarter;

import com.group.artifact.state.*;

import java.util.function.Supplier;

public enum Command {
    BOOK_INFO(BookInfo::new, "!정보"),
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


    private Supplier<State> stateIniter;
    private String matcher;

    Command(Supplier<State> stateIniter, String matcher) {
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

    public State initState() {
        return this.stateIniter.get();
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
