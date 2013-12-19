package com.cloudmine.test;

import com.cloudmine.api.CacheableCMFile;
import com.cloudmine.api.exceptions.CreationException;

import java.io.InputStream;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ExtendedCMFile extends CacheableCMFile {

    private String text;

    public ExtendedCMFile(InputStream contents, String text) throws CreationException {
        super(contents);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
