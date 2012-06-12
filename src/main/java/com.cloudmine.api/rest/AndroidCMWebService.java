package com.cloudmine.api.rest;

import android.os.Parcel;
import android.os.Parcelable;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.DeviceIdentifier;
import org.apache.http.message.AbstractHttpMessage;

/**
 * Extension of CMWebService that allows the service to be converted to a parcel for sending
 * between Android Activities
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 6/11/12, 2:16 PM
 */
public class AndroidCMWebService extends CMWebService implements Parcelable {


    public static final String CLOUD_MINE_AGENT = "Android 1.0";

    public AndroidCMWebService(Parcel in) {
        this(in.readString());
    }

    public AndroidCMWebService() {
        this(CMApiCredentials.applicationIdentifier());
    }

    public AndroidCMWebService(String appId) {
        super(appId, new AndroidAsynchronousHttpClient());
    }

    @Override
    protected void addCloudMineHeader(AbstractHttpMessage message) {
        super.addCloudMineHeader(message);
        message.addHeader(DeviceIdentifier.deviceIdentifierHeader());
    }

    @Override
    protected String cloudMineAgent() {
        return CLOUD_MINE_AGENT;
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
