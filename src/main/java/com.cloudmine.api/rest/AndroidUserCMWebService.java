package com.cloudmine.api.rest;

import android.os.Parcel;
import android.os.Parcelable;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.DeviceIdentifier;
import org.apache.http.message.AbstractHttpMessage;

/*
 * There is some code duplication in here from AndroidCMWebService, because Java doesn't let us do
 * multiple inheritance. Silly Java.
 */
/**
 * Android specific implementation of UserCMWebService, which allows it to be parcelable
 * Copyright CloudMine LLC
 * Date: 6/11/12, 2:41 PM
 */
public class AndroidUserCMWebService extends UserCMWebService implements Parcelable {
    public static final Creator<AndroidUserCMWebService> CREATOR =
            new Creator<AndroidUserCMWebService>() {
                @Override
                public AndroidUserCMWebService createFromParcel(Parcel parcel) {
                    return new AndroidUserCMWebService(parcel);
                }

                @Override
                public AndroidUserCMWebService[] newArray(int i) {
                    return new AndroidUserCMWebService[i];
                }
            };


    protected AndroidUserCMWebService(Parcel in) {
        super(new CMURLBuilder(in.readString()),
                CMSessionToken.CMSessionToken(in.readString()),
                new AndroidAsynchronousHttpClient());
    }

    protected AndroidUserCMWebService(CMURLBuilder baseUrl, CMSessionToken token, AsynchronousHttpClient asynchronousHttpClient) {
        super(baseUrl, token, asynchronousHttpClient);
    }

    @Override
    protected void addCloudMineHeader(AbstractHttpMessage message) {
        super.addCloudMineHeader(message);
        message.addHeader(DeviceIdentifier.getDeviceIdentifierHeader());
    }

    @Override
    protected String getCloudMineAgent() {
        return AndroidCMWebService.CLOUD_MINE_AGENT;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(baseUrl.getApplicationPath());
        parcel.writeString(sessionToken.asJson());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
