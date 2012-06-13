package com.cloudmine.api.rest;

import android.os.Parcel;
import android.os.Parcelable;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMUserToken;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.exceptions.CreationException;
import org.apache.http.message.AbstractHttpMessage;

/**
 * Extension of CMWebService that allows the service to be converted to a parcel for sending
 * between Android Activities
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 6/11/12, 2:16 PM
 */
public class AndroidCMWebService extends CMWebService implements Parcelable {
    /*************************IMPORTANT*****************************
     * Do not add any public static fields to this class. Anything that is public
     * will not be accessible until CMApiCredentials.initialize is called. Creator is fine
     * cause its just used by android
     ***************************************************************
     */
    public static final Creator<AndroidCMWebService> CREATOR =
            new Creator<AndroidCMWebService>() {
                @Override
                public AndroidCMWebService createFromParcel(Parcel parcel) {
                    return new AndroidCMWebService(parcel);
                }

                @Override
                public AndroidCMWebService[] newArray(int i) {
                    return new AndroidCMWebService[i];
                }
            };

    protected static final String CLOUD_MINE_AGENT = "Android 1.0";

    private static final AndroidCMWebService service = new AndroidCMWebService();

    /**
     *
     * @throws CreationException if CMApiCredentials.initialize has not been called yet
     * @return
     */
    public static AndroidCMWebService service() throws CreationException{
        return service;
    }

    private AndroidCMWebService(Parcel in) {
        this(in.readString());
    }

    /**
     *
     * @throws CreationException if CMApiCredentials.initialize has not been called yet
     */
    private AndroidCMWebService() throws CreationException {
        this(CMApiCredentials.applicationIdentifier());
    }

    private AndroidCMWebService(String appId) {
        super(appId, new AndroidAsynchronousHttpClient());
    }

    @Override
    protected void addCloudMineHeader(AbstractHttpMessage message) {
        super.addCloudMineHeader(message);
        message.addHeader(DeviceIdentifier.deviceIdentifierHeader());
    }


    @Override
    protected UserCMWebService createUserCMWebService(CMUserToken token) {
        return new AndroidUserCMWebService(baseUrl.user(), token, asyncHttpClient);
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
