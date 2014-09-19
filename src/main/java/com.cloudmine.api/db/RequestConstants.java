package com.cloudmine.api.db;

import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.rest.CMURLBuilder;

/**
 *
 */
public abstract class RequestConstants {

    static final String APP_SAVE_URL;
    static final String USER_SAVE_URL;
    static final String ACL_SAVE_URL;
    static final CMURLBuilder APP_SAVE_FILE_URL;
    static final CMURLBuilder USER_SAVE_FILE_URL;
    static {
        CMURLBuilder builder = new CMURLBuilder(CMApiCredentials.getApplicationIdentifier());
        ACL_SAVE_URL = builder.copy().user().access().asUrlString();
        APP_SAVE_URL = builder.copy().text().asUrlString();
        USER_SAVE_URL = builder.user().text().asUrlString();
        APP_SAVE_FILE_URL = new CMURLBuilder(CMApiCredentials.getApplicationIdentifier());
        USER_SAVE_FILE_URL = new CMURLBuilder(CMApiCredentials.getApplicationApiKey()).user();
    }
}
