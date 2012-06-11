package com.cloudmine.api;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

/**
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 6/11/12, 2:45 PM
 */
public class AndroidUser extends CMUser implements Parcelable {

    public static final Parcelable.Creator<AndroidUser> CREATOR =
            new Parcelable.Creator<AndroidUser>() {

                @Override
                public AndroidUser createFromParcel(Parcel parcel) {
                    return new AndroidUser(parcel);
                }

                @Override
                public AndroidUser[] newArray(int i) {
                    return new AndroidUser[i];
                }
            };

    public AndroidUser(Parcel parcel) {
        super(parcel.readString(), parcel.readString());
    }

    public AndroidUser(String email, String password) {
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
