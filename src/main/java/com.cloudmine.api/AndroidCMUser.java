package com.cloudmine.api;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.cloudmine.api.exceptions.CreationException;

/**
 * Parcelable implementation of CMUser that encodes using the Android libraries
 * Copyright CloudMine LLC
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

    public AndroidCMUser(Parcel parcel) throws CreationException {
        super(parcel.readString(), parcel.readString());
    }

    /**
     * Create a new AndroidCMUser with the given email and password.
     * @param email a non null email address
     * @param password a non null password
     * @throws CreationException if given a null email or password
     */
    public AndroidCMUser(String email, String password) throws CreationException {
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
        parcel.writeString(getEmail());
        parcel.writeString(getPassword());
    }
}
