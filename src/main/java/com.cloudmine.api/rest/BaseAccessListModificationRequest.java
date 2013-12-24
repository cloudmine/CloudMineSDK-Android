package com.cloudmine.api.rest;

import android.os.Handler;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.cloudmine.api.db.BaseLocallySavableCMAccessList;
import com.cloudmine.api.rest.options.CMServerFunction;
import com.cloudmine.api.rest.response.CreationResponse;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseAccessListModificationRequest extends CloudMineRequest<CreationResponse> {

    public static final int REQUEST_TYPE = 418;
    private static final String URL = "/user/access";

    @Expand
    public BaseAccessListModificationRequest(BaseLocallySavableCMAccessList accessList, @Optional CMServerFunction serverFunction, @Optional Response.Listener<CreationResponse> successListener, @Optional Response.ErrorListener errorListener) {
        super(Method.POST, URL, accessList.transportableRepresentation(), accessList.getUser().getSessionToken(), serverFunction, successListener, errorListener);
    }

    public BaseAccessListModificationRequest(BaseLocallySavableCMAccessList accessList, @Optional CMServerFunction serverFunction, Handler handler) {
        this(accessList, serverFunction, null,null);
        setHandler(handler);
    }

    @Override
    protected Response<CreationResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        return Response.success(new CreationResponse(new String(networkResponse.data), networkResponse.statusCode), getCacheEntry(networkResponse));
    }

    @Override
    public int getRequestType() {
        return REQUEST_TYPE;
    }
}
