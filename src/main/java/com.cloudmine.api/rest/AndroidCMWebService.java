package com.cloudmine.api.rest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Extension of CMWebService that allows the service to be converted to a parcel for sending
 * between Android Activities
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 6/11/12, 2:16 PM
 */
public class AndroidCMWebService extends CMWebService implements Parcelable {



    public AndroidCMWebService(Parcel in) {
        super(new CMURLBuilder(in.readString()), new AndroidAsynchronousHttpClient());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(baseUrl.appPath());
    }
}
