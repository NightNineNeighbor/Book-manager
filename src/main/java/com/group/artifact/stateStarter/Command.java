package com.group.artifact.stateStarter;

import com.group.artifact.state.*; import java.util.function.Supplier;

public enum Command {
    REVIEW_READ(ReadReviewWithBookName::new, ReadReviewWithoutBookName::new),
    REVIEW_CREATE(CreateReviewWithBookName::new, CreateReviewWithoutBookName::new),
    REVIEW_UPDATE(UpdateReviewWithBookName::new, UpdateReviewWithoutBookName::new),
    REVIEW_DELETE(DeleteReviewWithBookName::new, DeleteReviewWithoutBookName::new),
    NO_COMMAND(BookInfo::new,Echo::new);

    private Supplier<State> isBookName;
    private Supplier<State> noBookName;

    Command(Supplier<State> isBookName, Supplier<State> noBookName) {
        this.noBookName = noBookName;
        this.isBookName = isBookName;
    }

    public State initState(boolean isBookName) {
        if (isBookName) {
            return this.isBookName.get();
        }
        return noBookName.get();
    }

}
