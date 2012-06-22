package com.cloudmine.api.rest;

import android.os.Parcel;
import android.os.Parcelable;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.exceptions.CreationException;
import org.apache.http.message.AbstractHttpMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extension of CMWebService that allows the service to be converted to a parcel for sending.
 * between Android Activities
 * <br>Copyright CloudMine LLC. All rights reserved<br> See LICENSE file included with SDK for details.
 */
public class AndroidCMWebService extends CMWebService implements Parcelable {
    private static final Logger LOG = LoggerFactory.getLogger(CMWebService.class);
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

    static final String CLOUD_MINE_AGENT = "Android 1.0";

    private static final AndroidCMWebService service;
    static {
        AndroidCMWebService temp = null;
        try {
            temp = new AndroidCMWebService();
        } catch (CreationException e) {
            LOG.error("Unable to instantiate service", e);
        } finally {
            service = temp;
        }
    }

    /**
     * Get an instance of the service. CMApiCredentials.initialize must have already been called
     * @throws CreationException if CMApiCredentials.initialize has not been called yet
     * @return
     */
    public static AndroidCMWebService getService() throws CreationException {
        if(service == null) {
            throw new CreationException("Service could not be instantiated. Has CMApiCredentials.initialize been called?");
        }
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
        this(CMApiCredentials.getApplicationIdentifier());
    }

    private AndroidCMWebService(String appId) {
        super(appId, new AndroidAsynchronousHttpClient());
    }

    @Override
    protected void addCloudMineHeader(AbstractHttpMessage message) {
        super.addCloudMineHeader(message);
        message.addHeader(DeviceIdentifier.getDeviceIdentifierHeader());
    }


    @Override
    protected UserCMWebService createUserCMWebService(CMSessionToken token) {
        return new AndroidUserCMWebService(baseUrl.user(), token, asyncHttpClient);
    }

    @Override
    protected String getCloudMineAgent() {
        return CLOUD_MINE_AGENT;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(baseUrl.getApplicationPath());
    }
}
