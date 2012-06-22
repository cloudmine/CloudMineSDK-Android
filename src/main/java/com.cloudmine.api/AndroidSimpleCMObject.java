package com.cloudmine.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.cloudmine.api.exceptions.CreationException;
import com.cloudmine.api.exceptions.JsonConversionException;
import com.cloudmine.api.rest.JsonString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parcelable representation of SimpleCMObject
 * <br>Copyright CloudMine LLC. All rights reserved<br> See LICENSE file included with SDK for details.
 */
public class AndroidSimpleCMObject extends SimpleCMObject implements Parcelable {
    private static final Logger LOG = LoggerFactory.getLogger(AndroidSimpleCMObject.class);
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

    /**
     * Create a new AndroidSimpleCMObject based on an existing SimpleCMObject, so it can be serialized
     * @param object this new AndroidSimpleCMObject will be equivalent to this
     * @throws JsonConversionException If unable to convert the SimpleCMObject to json. Should never be thrown,
     *          as if the SimpleCMObject was created fine, this copy should be as well.
     * @throws CreationException if the SimpleCMObject doesn't have an objectId mapped to a content map. Should never
     *          be thrown, as if the SimpleCMObject was created fine, this copy should be as well.
     */
    public AndroidSimpleCMObject(SimpleCMObject object) throws JsonConversionException, CreationException {
        super(object);
    }

    private AndroidSimpleCMObject(Parcel in) throws JsonConversionException, CreationException {
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
