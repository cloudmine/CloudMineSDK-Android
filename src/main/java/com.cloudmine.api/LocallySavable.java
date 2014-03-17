package com.cloudmine.api;

import android.content.Context;

import java.util.Date;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public interface LocallySavable {

    public boolean saveLocally(Context context);
    public boolean saveEventually(Context context);
    public int getLastLocalSavedDateAsSeconds();
    public Date getLastLocalSaveDate();
}
