package com.cloudmine.api.rest;

import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMSortOptions;
import com.cloudmine.api.rest.response.CMObjectResponse;

/**
 * A builder for creating complex object load requests
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ObjectLoadRequestBuilder extends RequestBuilder<ObjectLoadRequestBuilder, BaseObjectLoadRequest, CMObjectResponse>{

    private CMURLBuilder urlBuilder = BaseObjectLoadRequest.BASE_URL.copy();



    public ObjectLoadRequestBuilder(CMSessionToken sessionToken, Response.Listener<CMObjectResponse> success) {
        this(sessionToken, success, null);
    }

    public ObjectLoadRequestBuilder(CMSessionToken sessionToken, Response.Listener<CMObjectResponse> success, Response.ErrorListener error) {
        super(sessionToken, success, error);
    }

    public ObjectLoadRequestBuilder(Response.Listener< CMObjectResponse > success) {
        this(success, null);
    }

    public ObjectLoadRequestBuilder(Response.Listener< CMObjectResponse > success, Response.ErrorListener error) {
        super(success, error);
    }

    @Override
    public BaseObjectLoadRequest build() {
        return new BaseObjectLoadRequest(urlBuilder, sessionToken, serverFunction, successListener, errorListener);
    }

    public ObjectLoadRequestBuilder search(String searchQuery) {
        urlBuilder.removeAction(BaseObjectLoadRequest.BASE_ENDPOINT);
        urlBuilder.search(searchQuery);
        return this;
    }

    public ObjectLoadRequestBuilder startAt(int skip) {
        urlBuilder.addQuery("skip", skip);
        return this;
    }

    public ObjectLoadRequestBuilder sortBy(String sortField) {
        urlBuilder.addQuery("sort", sortField);
        return this;
    }

    public ObjectLoadRequestBuilder sortBy(String sortField, CMSortOptions.SortDirection sortDirection) {
        urlBuilder.addQuery("sort", sortField + ":" + sortDirection.toString());
        return this;
    }

    public ObjectLoadRequestBuilder limit(int limit) {
        urlBuilder.addQuery("limit", limit);
        return this;
    }

    public ObjectLoadRequestBuilder getCount() {
        urlBuilder.addQuery("count", "true");
        return this;
    }

    public ObjectLoadRequestBuilder getShared() {
        urlBuilder.addQuery("shared", "true");
        return this;
    }

    public ObjectLoadRequestBuilder getSharedOnly() {
        urlBuilder.addQuery("shared_only", "true");
        return this;
    }
}
