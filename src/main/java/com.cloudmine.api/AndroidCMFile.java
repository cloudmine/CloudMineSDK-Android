package com.cloudmine.api;

import android.graphics.Bitmap;
import com.cloudmine.api.exceptions.CreationException;

import java.io.ByteArrayOutputStream;

/**
 * Android specific implementation of CMFile, which provides some convenient methods specific to the
 * Android platform
 * Copyright CloudMine LLC. All rights reserved<br> See LICENSE file included with SDK for details.
 */
public class AndroidCMFile extends CMFile {

    /**
     * Create an AndroidCMFile from a picture.
     * @param picture will be the contents of this file. Must not be null.
     * @throws CreationException if the given picture is null
     */
    public AndroidCMFile(Bitmap picture) throws CreationException {
        this(picture, null);
    }

    /**
     * Create an AndroidCMFile from a Bitmap, with the given file name
     * @param picture will be the contents of this file. Must not be null.
     * @param fileName the fileName to associate with this file. If null, a random fileName will be generated
     * @throws CreationException if given a null picture
     */
    public AndroidCMFile(Bitmap picture, String fileName) throws CreationException {
        super(getOutput(picture), fileName, IMAGE_PNG_CONTENT_TYPE);
    }

    private static byte[] getOutput(Bitmap picture) throws CreationException {
        if(picture == null) {
            throw new CreationException("Cannot create a CMFile off of null input");
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, output);

        return output.toByteArray();
    }
}
