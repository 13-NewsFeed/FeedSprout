package com.sparta.newsfeed.auth.template;

import com.sparta.newsfeed.auth.strategy.AuthorizationStrategy;

public class CommentRequestHandler extends AbstractRequestHandler {

    public CommentRequestHandler(AuthorizationStrategy authorizationStrategy) {
        super(authorizationStrategy);
    }

    @Override
    protected Long extractResourceId(String requestUri) {
        return Long.valueOf(requestUri.split("/")[2]);
    }
}
