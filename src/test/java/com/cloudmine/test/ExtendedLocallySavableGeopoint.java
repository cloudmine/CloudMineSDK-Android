package com.cloudmine.test;

import com.cloudmine.api.db.LocallySavableCMGeoPoint;
import com.cloudmine.api.persistance.ClassNameRegistry;

/**
 * <br>
 * Copyright CloudMine, Inc. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ExtendedLocallySavableGeopoint extends LocallySavableCMGeoPoint {
    public static final String CLASS_NAME = "ExtendedLocallySavableGeopoint";
    static {
        ClassNameRegistry.register(CLASS_NAME, ExtendedLocallySavableGeopoint.class);
    }

    private String locationName;

    public ExtendedLocallySavableGeopoint() {}

    public ExtendedLocallySavableGeopoint(String locationName, double lat, double lng) {
        super(lat, lng);
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getClassName() {
        return CLASS_NAME;
    }
}
