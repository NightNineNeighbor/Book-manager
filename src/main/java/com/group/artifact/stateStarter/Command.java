package com.group.artifact.stateStarter;

import com.group.artifact.state.ReviewCreate;
import com.group.artifact.state.State;

import java.util.function.Supplier;

public enum Command {
    STATE_ZERO(null),
    REVIEW_READ(ReviewCreate::new),
    REVIEW_CREATE(ReviewCreate::new),
    REVIEW_UPDATE(null),
    REVIEW_DELETE(null),
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
