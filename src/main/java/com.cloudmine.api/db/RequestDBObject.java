package com.cloudmine.api.db;

import android.content.ContentValues;
import com.cloudmine.api.LibrarySpecificClassCreator;
import com.cloudmine.api.Strings;
import com.cloudmine.api.CMApiCredentials;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.cloudmine.api.db.RequestDBOpenHelper.*;


/**
 * Encapsulates all of the information needed to perform a request.
 *
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class RequestDBObject {


    public static enum Verb {
        GET("get"), PUT("put");
        private final String representation;

        private Verb(String representation) {
            this.representation = representation;
        }

        public String getRepresentation() {
            return representation;
        }

        public boolean is(String representation) {
            return getRepresentation().equalsIgnoreCase(representation);
        }

        public static Verb getVerb(String name) {
            for (Verb verb : values()) {
                if (verb.is(name)) {
                    return verb;
                }
            }
            return GET;
        }
    }

    public static enum SyncStatus {
        UNSYNCED, IN_PROGRESS, SYNCED;

        public static SyncStatus getSyncStatus(int value) {
            switch (value) {
                case 0:
                    return UNSYNCED;
                case 1:
                    return IN_PROGRESS;
                case 2:
                    return SYNCED;
                default:
                    return UNSYNCED;
            }
        }
    }

    public static RequestDBObject createApplicationObjectRequest(String objectId) {
        RequestDBObject request = new RequestDBObject(RequestConstants.APP_SAVE_URL, Verb.PUT, (String)null, objectId, -1, SyncStatus.UNSYNCED, new ArrayList<Header>(LibrarySpecificClassCreator.getCreator().getHeaderFactory().getCloudMineHeaders(CMApiCredentials.getApplicationApiKey())));
        return request;
    }

    public static RequestDBObject createApplicationFileRequest(String fileId) {
        RequestDBObject request = new RequestDBObject(RequestConstants.APP_SAVE_FILE_URL.copy().binary(fileId).asUrlString(), Verb.PUT, null, null, fileId, -1, SyncStatus.UNSYNCED, new ArrayList<Header>(LibrarySpecificClassCreator.getCreator().getHeaderFactory().getCloudMineHeaders(CMApiCredentials.getApplicationApiKey())));
        return request;
    }

    private final String requestUrl;
    private final Verb requestType;
    private String jsonBody;
    private byte[] body;
    private final String objectId;
    private final String fileId;
    private final int id;
    private final SyncStatus syncStatus;
    private final List<Header> headers;


    public RequestDBObject(String requestUrl, Verb requestType, String jsonBody) {
        this(requestUrl, requestType, jsonBody, new ArrayList<Header>());
    }

    /**
     * Create a new request that is unsynced
     * @param requestUrl
     * @param requestType
     * @param jsonBody
     * @param headers
     */
    public RequestDBObject(String requestUrl, Verb requestType, String jsonBody, List<Header> headers) {
        this(requestUrl, requestType, jsonBody, null, -1, SyncStatus.UNSYNCED, headers);
    }

    public RequestDBObject(String requestUrl, Verb requestType, String jsonBody, String objectId, int id, SyncStatus syncStatus, List<Header> headers) {
        this(requestUrl, requestType, jsonBody, objectId, null, id, syncStatus, headers);
    }

    public RequestDBObject(String requestUrl, Verb requestType, String jsonBody, String objectId, String fileId, int id, SyncStatus syncStatus, List<Header> headers) {
        this.requestUrl = requestUrl;
        this.requestType = requestType;
        this.jsonBody = jsonBody;
        this.id = id;
        this.syncStatus = syncStatus;
        if(headers == null)
            headers = new ArrayList<Header>();
        this.headers = headers;
        this.objectId = objectId;
        this.fileId = fileId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public Verb getRequestType() {
        return requestType;
    }

    public String getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(String jsonBody) {
        this.jsonBody = jsonBody;
    }

    public int getId() {
        return id;
    }

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void addHeader(Header toAdd) {
        headers.add(toAdd);
    }

    public String getObjectId() {
        return objectId;
    }

    public String getFileId() {
        return fileId;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public ContentValues[] toContentValues() {
        int numberOfValues = headers == null ?
                1 :
                headers.size() + 1;
        ContentValues[] values = new ContentValues[numberOfValues];
        values[0] = toRequestContentValues();
        int i = 1;
        for(ContentValues contentValues : toHeaderContentValues()) {
            values[i] = contentValues;
            i++;
        }
        return values;
    }

    public ContentValues toRequestContentValues() {
        ContentValues requestContentValues = new ContentValues();
        requestContentValues.put(KEY_REQUEST_JSON_BODY, jsonBody);
        requestContentValues.put(KEY_REQUEST_SYNCHRONIZED, syncStatus.ordinal());
        requestContentValues.put(KEY_REQUEST_TARGET_URL, requestUrl);
        requestContentValues.put(KEY_REQUEST_VERB, requestType.name());
        requestContentValues.put(KEY_REQUEST_OBJECT_ID, objectId);
        requestContentValues.put(KEY_REQUEST_FILE_ID, fileId);
        return requestContentValues;
    }

    public ContentValues[] toHeaderContentValues() {
        if(headers == null || headers.size() == 0) {
            return new ContentValues[0];
        }
        ContentValues[] headerContentValues = new ContentValues[headers.size()];
        int i = 0;
        for(Header header : headers) {
            headerContentValues[i] = headerToContentValues(header);
            i++;
        }
        return headerContentValues;
    }

    public HttpUriRequest toHttpRequest() {
        HttpUriRequest request = null;
        switch(requestType) {
            case GET:
                request = new HttpGet(requestUrl);
                break;
            case PUT:
                request = new HttpPut(requestUrl);
                try {
                    if(Strings.isNotEmpty(jsonBody)) {
                        ((HttpPut)request).setEntity(new StringEntity(jsonBody));
                        request.addHeader("Content-Type", "application/json");
                    }
                } catch (UnsupportedEncodingException e) {
                }
                if(body != null && body.length < 0) {
                    ((HttpPut)request).setEntity(new ByteArrayEntity(body));
                }
        }
        for(Header header : headers) {
            request.addHeader(header);
        }
        return request;
    }

    private static ContentValues headerToContentValues(Header header) {
        ContentValues headerValues = new ContentValues();
        if(header == null) {
            return headerValues;
        }
        headerValues.put(KEY_HEADER_NAME, header.getName());
        headerValues.put(KEY_HEADER_VALUE, header.getValue());
        return headerValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestDBObject that = (RequestDBObject) o;

        if (id != that.id) return false;
        if (fileId != null ? !fileId.equals(that.fileId) : that.fileId != null) return false;
        if (headers != null ? !headers.equals(that.headers) : that.headers != null) return false;
        if (jsonBody != null ? !jsonBody.equals(that.jsonBody) : that.jsonBody != null) return false;
        if (objectId != null ? !objectId.equals(that.objectId) : that.objectId != null) return false;
        if (requestType != that.requestType) return false;
        if (requestUrl != null ? !requestUrl.equals(that.requestUrl) : that.requestUrl != null) return false;
        if (syncStatus != that.syncStatus) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = requestUrl != null ? requestUrl.hashCode() : 0;
        result = 31 * result + (requestType != null ? requestType.hashCode() : 0);
        result = 31 * result + (jsonBody != null ? jsonBody.hashCode() : 0);
        result = 31 * result + (objectId != null ? objectId.hashCode() : 0);
        result = 31 * result + (fileId != null ? fileId.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + (syncStatus != null ? syncStatus.hashCode() : 0);
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RequestDBObject{" +
                "requestUrl='" + requestUrl + '\'' +
                ", requestType=" + requestType +
                ", jsonBody='" + jsonBody + '\'' +
                ", objectId='" + objectId + '\'' +
                ", fileId='" + fileId + '\'' +
                ", id=" + id +
                ", syncStatus=" + syncStatus +
                ", headers=" + headers +
                '}';
    }
}
