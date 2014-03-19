package com.cloudmine.test;

import com.cloudmine.api.BaseCMUser;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ExtendedACMUser extends BaseCMUser {

    private boolean hasProperty;
    private int points;

    private ExtendedACMUser() {}

    public ExtendedACMUser(String username, String password) {
        this(username, password, true, 1000);
    }

    public ExtendedACMUser(String username, String password, boolean hasProperty, int points) {
        super(null, username, password);
        this.hasProperty = hasProperty;
        this.points = points;
    }

    public boolean isHasProperty() {
        return hasProperty;
    }

    public void setHasProperty(boolean hasProperty) {
        this.hasProperty = hasProperty;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
