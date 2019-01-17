package com.group.artifact.stateStarter;

import com.group.artifact.state.*;

import java.util.function.Supplier;

public enum Command {
    BOOK_INFO(BookInfo::new),
    REVIEW_READ(ReviewRead::new),
    REVIEW_CREATE(ReviewUpdate::new),
    REVIEW_UPDATE(ReviewUpdate::new),
    REVIEW_DELETE(ReviewDelete::new),
    NO_COMMAND(DoNothing::new),
    DO_NOTHING(DoNothing::new),
    EXIT_COMMAND_MODE(ExitCommand::new),
    USAGE(Usage::new);


    private Supplier<State> stateIniter;

    Command(Supplier<State> stateIniter) {
        this.stateIniter = stateIniter;
    }

    public State initState() {
        return this.stateIniter.get();
    }
}
