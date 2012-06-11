package com.cloudmine.api;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

/**
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 6/11/12, 2:45 PM
 */
public class AndroidCMUser extends CMUser implements Parcelable {

    public static final Parcelable.Creator<AndroidCMUser> CREATOR =
            new Parcelable.Creator<AndroidCMUser>() {

                @Override
                public AndroidCMUser createFromParcel(Parcel parcel) {
                    return new AndroidCMUser(parcel);
                }

                @Override
                public AndroidCMUser[] newArray(int i) {
                    return new AndroidCMUser[i];
                }
            };

    public AndroidCMUser(Parcel parcel) {
        super(parcel.readString(), parcel.readString());
    }

    public AndroidCMUser(String email, String password) {
        super(email, password);
    }

    @Override
    public String encodeString(String toEncode) {
        return Base64.encodeToString(toEncode.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email());
        parcel.writeString(password());
    }
}
