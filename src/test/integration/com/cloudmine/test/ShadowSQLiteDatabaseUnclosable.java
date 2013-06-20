package com.cloudmine.test;

import android.database.sqlite.SQLiteDatabase;
import com.xtremelabs.robolectric.shadows.ShadowSQLiteDatabase;
import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@Implements(SQLiteDatabase.class)
public class ShadowSQLiteDatabaseUnclosable extends ShadowSQLiteDatabase {

    public void close() {}
}
