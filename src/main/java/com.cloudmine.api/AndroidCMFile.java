package com.cloudmine.api;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 6/11/12, 2:23 PM
 */
public class AndroidCMFile extends CMFile {

    public AndroidCMFile(Bitmap picture) {
        this(picture, null);
    }

    public AndroidCMFile(Bitmap picture, String key) {
        super(getOutput(picture), key, IMAGE_PNG_CONTENT_TYPE);
    }

    public static byte[] getOutput(Bitmap picture) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, output);

        return output.toByteArray();
    }
}
