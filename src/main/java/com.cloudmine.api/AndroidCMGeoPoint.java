package com.cloudmine.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.cloudmine.api.exceptions.JsonConversionException;
import com.cloudmine.api.rest.JsonString;

/**
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 6/11/12, 3:47 PM
 */
public class AndroidCMGeoPoint extends CMGeoPoint implements Parcelable {
    public static final Creator<AndroidCMGeoPoint> CREATOR =
            new Creator<AndroidCMGeoPoint>() {
                @Override
                public AndroidCMGeoPoint createFromParcel(Parcel parcel) {
                    return new AndroidCMGeoPoint(parcel);
                }

                @Override
                public AndroidCMGeoPoint[] newArray(int i) {
                    return new AndroidCMGeoPoint[i];
                }
            };
    public AndroidCMGeoPoint(double longitude, double latitude) {
        super(longitude, latitude);
    }

    public AndroidCMGeoPoint(double longitude, double latitude, String key) {
        super(longitude, latitude, key);
    }

    public AndroidCMGeoPoint(Parcel parcel) throws JsonConversionException{
        super(new JsonString(parcel.readString()));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(asJson());
    }
}
