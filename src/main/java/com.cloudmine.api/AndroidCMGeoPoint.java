package com.cloudmine.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.cloudmine.api.exceptions.CreationException;
import com.cloudmine.api.exceptions.JsonConversionException;
import com.cloudmine.api.rest.JsonString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A parcelable implementation of CMGeoPoint
 * <br>Copyright CloudMine LLC. All rights reserved<br> See LICENSE file included with SDK for details.
 */
public class AndroidCMGeoPoint extends CMGeoPoint implements Parcelable {
    private static final Logger LOG = LoggerFactory.getLogger(AndroidCMGeoPoint.class);
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
    public AndroidCMGeoPoint(double longitude, double latitude) throws CreationException {
        super(longitude, latitude);
    }

    public AndroidCMGeoPoint(double longitude, double latitude, String objectId) throws CreationException {
        super(longitude, latitude, objectId);
    }

    public AndroidCMGeoPoint(Parcel parcel) throws JsonConversionException, CreationException {
        super(new JsonString(parcel.readString()));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeString(asJson());
        } catch (JsonConversionException e) {
            LOG.error("Unable to convert AndroidCMGeoPoint to json, unparcelling this is going to throw a CreationException");
        }
    }
}
