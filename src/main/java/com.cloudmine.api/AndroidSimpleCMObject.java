package com.cloudmine.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.cloudmine.api.rest.JsonString;

/**
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 6/11/12, 2:35 PM
 */
public class AndroidSimpleCMObject extends SimpleCMObject implements Parcelable {
    public static final Creator<AndroidSimpleCMObject> CREATOR =
            new Creator<AndroidSimpleCMObject>() {
                @Override
                public AndroidSimpleCMObject createFromParcel(Parcel parcel) {
                    return new AndroidSimpleCMObject(parcel);
                }

                @Override
                public AndroidSimpleCMObject[] newArray(int i) {
                    return new AndroidSimpleCMObject[i];
                }
            };


    public AndroidSimpleCMObject(SimpleCMObject object) {
        super(object);
    }

    public AndroidSimpleCMObject(Parcel in) {
        super(new JsonString(in.readString()));
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
