package com.cloudmine.api.rest.callbacks;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class AuthorizeResponseCallback implements Callback<String> {

    private long startTime = 0L;

    @Override
    public void onCompletion(String response) {

    }

    @Override
    /**
     * Only the message will exist - for convenience {@link #onFailure(String)} is provided. Only override
     * one of the onFailure methods.
     * @param error will always be null
     * @param message an error message, not intended for display
     */
    public void onFailure(Throwable error, String message) {
        onFailure(message);
    }

    /**
     * Convenience version of {@link #onFailure(Throwable, String)}. Only one of the two should be overridden
     * @param message an error message, not intended for display
     */
    public void onFailure(String message) {

    }

    @Override
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }
}
