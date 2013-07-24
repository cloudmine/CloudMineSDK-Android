package com.cloudmine.api.db;

import com.cloudmine.api.CMGeoPointInterface;
import com.cloudmine.api.SimpleCMObject;
import com.cloudmine.api.rest.TransportableString;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link CMGeoPointInterface} that can be saved locally/synced to server eventually.
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseLocallySavableCMGeoPoint extends BaseLocallySavableCMObject implements CMGeoPointInterface {

    private static final Logger LOG = LoggerFactory.getLogger(BaseLocallySavableCMGeoPoint.class);
    private static final String[] LATITUDE_KEYS = {"latitude", "lat", "y"};
    private static final String[] LONGITUDE_KEYS = {"longitude", "lon", "lng", "x"};

    private double latitude;
    private double longitude;
    private final String __type__ = "geopoint";

    protected BaseLocallySavableCMGeoPoint() {

    }

    public BaseLocallySavableCMGeoPoint(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public BaseLocallySavableCMGeoPoint(TransportableString transportableString) {
        //is this hacky? yes it is!
        SimpleCMObject object = new SimpleCMObject(transportableString);
        this.latitude = getValueFromKey(object, LATITUDE_KEYS);
        this.longitude = getValueFromKey(object, LONGITUDE_KEYS);
    }

    protected void setWithOtherKey(String key, double value) {
        if(is(LATITUDE_KEYS, key)) {
            setLatitude(value);
        } else if(is(LONGITUDE_KEYS, key)) {
            setLongitude(value);
        }
    }

    @JsonAnySetter
    protected void setWithOtherKey(String key, String value) {
        try {
            double asDouble = Double.parseDouble(value);
            setWithOtherKey(key, asDouble);
        }catch(NumberFormatException e) {

        }
    }

    private boolean is(String[] possibleKeys, String key) {
        for(String possibleKey : possibleKeys) {
            if(possibleKey.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private double getValueFromKey(SimpleCMObject object, String[] keys) {
        for(String key : keys) {
            Double keyValue = object.getDouble(key);
            if(keyValue != null)
                return keyValue.doubleValue();
        }
        LOG.error("No value found for keys, using 0.0");
        return 0;
    }

    @Override
    public String getClassName() {
        return GEOPOINT_CLASS;
    }

    public String get__type__() {
        return __type__;
    }

    @Override public double getLatitude() {
        return latitude;
    }

    @Override public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override public double getLongitude() {
        return longitude;
    }

    @Override public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseLocallySavableCMGeoPoint geoPoint = (BaseLocallySavableCMGeoPoint) o;

        if (Double.compare(geoPoint.latitude, latitude) != 0) return false;
        if (Double.compare(geoPoint.longitude, longitude) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = latitude != +0.0d ? Double.doubleToLongBits(latitude) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = longitude != +0.0d ? Double.doubleToLongBits(longitude) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
