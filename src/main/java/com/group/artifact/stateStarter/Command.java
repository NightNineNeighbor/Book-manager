package com.group.artifact.stateStarter;

import com.group.artifact.state.ReviewRead;
import com.group.artifact.state.ReviewUpdate;
import com.group.artifact.state.ReviewDelete;
import com.group.artifact.state.State;

import java.util.function.Supplier;

public enum Command {
    STATE_ZERO(null),
    REVIEW_READ(ReviewRead::new),
    REVIEW_CREATE(ReviewUpdate::new),
    REVIEW_UPDATE(ReviewUpdate::new),
    REVIEW_DELETE(ReviewDelete::new),
    NO_COMMAND(null),
    DO_NOTHING(null);


    private Supplier<State> stateIniter;

    Command(Supplier<State> stateIniter) {
        this.stateIniter = stateIniter;
    }

    public State initState() {
        return this.stateIniter.get();
    }
}
