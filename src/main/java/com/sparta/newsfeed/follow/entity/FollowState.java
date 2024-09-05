package com.sparta.newsfeed.follow.entity;

public enum FollowState {

    FOLLOWING(State.FOLLOWING),
    WAITING(State.WAITING),
    BLOCKED(State.BLOCKED);

    private final String state;

    FollowState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static class State {
        public static final String FOLLOWING = "FOLLOWING";
        public static final String WAITING = "WAITING";
        public static final String BLOCKED = "BLOCKED";
    }
}
