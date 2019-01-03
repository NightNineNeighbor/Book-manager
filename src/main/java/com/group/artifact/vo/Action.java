package com.group.artifact.vo;

import com.group.artifact.state.State;

import java.util.function.Consumer;

public enum Action {
    ReviewRead,
    ReviewDelete,
    ReviewUpdate,
    ReviewCreate,
    DoReviewCreate,
    DoReviewUpdate,
    NoAction;


}
