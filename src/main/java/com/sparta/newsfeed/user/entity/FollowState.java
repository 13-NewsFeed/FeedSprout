package com.sparta.newsfeed.user.entity;

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
        public static final String FOLLOWING = "following";
        public static final String WAITING = "waiting";
        public static final String BLOCKED = "blocked";
    }
}
