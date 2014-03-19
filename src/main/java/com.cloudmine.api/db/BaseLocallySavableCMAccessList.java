package com.cloudmine.api.db;

import android.content.Context;
import android.os.Handler;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cloudmine.api.CMAccessPermission;
import com.cloudmine.api.JavaCMUser;
import com.cloudmine.api.rest.BaseAccessListModificationRequest;
import com.cloudmine.api.rest.CloudMineRequest;
import com.cloudmine.api.rest.JsonUtilities;
import com.cloudmine.api.rest.response.CreationResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static com.cloudmine.api.rest.SharedRequestQueueHolders.getRequestQueue;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class BaseLocallySavableCMAccessList extends BaseLocallySavableCMObject {

    public static class Segments {
        private boolean isLoggedIn = false;
        private boolean isPublic = false;

        public Segments() {

        }

        @JsonProperty("logged_in")
        public boolean isLoggedIn() {
            return isLoggedIn;
        }

        @JsonProperty("logged_in")
        public void setLoggedIn(boolean isLoggedIn) {
            this.isLoggedIn = isLoggedIn;
        }

        @JsonProperty("public")
        public boolean isPublic() {
            return isPublic;
        }

        @JsonProperty("public")
        public void setPublic(boolean isPublic) {
            this.isPublic = isPublic;
        }
    }

    public static final String CLASS_NAME = "acl";
    private Set<String> userObjectIds = new HashSet<String>();
    private Set<CMAccessPermission> accessPermissions = EnumSet.noneOf(CMAccessPermission.class);
    private Segments segments = new Segments();

    /**
     * Create a new CMAccessList that grants no privileges and contains no users. It grants permissions to
     * objects owned by the given user
     */
    public BaseLocallySavableCMAccessList(JavaCMUser owner) {
        super();
        if(owner == null)
            throw new NullPointerException("Cannot instantiate a new CMAccessList from a null BaseCMUser");
        setSaveWith(owner);
    }

    /**
     * Instantiate a new CMAccessList owned by the specified user that grants the specified permissions
     * @param owner
     * @param permissions permissions
     */
    public BaseLocallySavableCMAccessList(JavaCMUser owner, CMAccessPermission... permissions) {
        this(owner);
        for(CMAccessPermission permission : permissions) {
            this.accessPermissions.add(permission);
        }
    }

    protected BaseLocallySavableCMAccessList() {
        //for jackson
    }

    /**
     * Add this user to this access list, giving them the specified permissions of this list. The user's object id must
     * be set
     * @param user
     */
    public void grantAccessTo(JavaCMUser user) {
        grantAccessTo(user.getObjectId());
    }

    /**
     * Add this user to this access list, giving them the specified permissions of this list
     * @param userObjectId
     */
    public void grantAccessTo(String userObjectId) {
        userObjectIds.add(userObjectId);
    }

    /**
     * Add all the given user object ids to this list, giving them the specified permissions of this list
     * @param userObjectIds the object ids of the users to grant permissions to
     */
    public void grantAccessTo(Collection<String> userObjectIds) {
        this.userObjectIds.addAll(userObjectIds);
    }

    /**
     * Add all the given permissions to this access list, allowing the users associated with this list the enumerated permissions
     * @param permissions the permissions to grant
     */
    public void grantPermissions(CMAccessPermission... permissions) {
        for(CMAccessPermission permission : permissions) {
            this.accessPermissions.add(permission);
        }
    }

    /**
     * Returns a String representation of the permissions; this is used for serialization and should probably be ignored
     * in favor of {@link #doesGrantPermissions(CMAccessPermission...)}
     * @return
     */
    public Set<String> getPermissions() {
        Set<String> permissions = new HashSet<String>();
        for(CMAccessPermission permission : accessPermissions) {
            permissions.add(permission.serverRepresentation());
        }
        return permissions;
    }

    /**
     * Grant the specified permissions; any previous permissions are overwritten. Used for deserialization and should
     * probably be ignored in favor of {@link #grantPermissions(CMAccessPermission...)}
     * @param permissions
     */
    public void setPermissions(Set<String> permissions) {
        if(permissions == null)
            permissions = new HashSet<String>();
        accessPermissions = new HashSet<CMAccessPermission>();
        for(String permissionAsString : permissions) {
            accessPermissions.add(CMAccessPermission.fromServerRepresentation(permissionAsString));
        }
    }

    public Segments getSegments() {
        return segments;
    }

    public void setSegments(Segments segments) {
        this.segments = segments;
    }

    @JsonIgnore
    public void setIsPublic(boolean isPublic) {
        segments.isPublic = isPublic;
    }

    @JsonIgnore
    public void setLoggedIn(boolean loggedIn) {
        segments.isLoggedIn = loggedIn;
    }

    /**
     * Get the users this list grants permissions to
     * @return
     */
    @JsonProperty("members")
    public Set<String> getUserObjectIdsWithAccess() {
        return userObjectIds;
    }

    /**
     * Grant the specified user ids access; any previously granted users will be overwritten
     * @param userObjectIds
     */
    public void setUserObjectsWithAccess(Set<String> userObjectIds) {
        if(userObjectIds == null)
            userObjectIds = new HashSet<String>();
        this.userObjectIds = userObjectIds;
    }

    /**
     * Check whether this access list grants access for the given user
     * @param user the user to check
     * @return true if the specified user has access, false otherwise
     */
    public boolean doesAllowAccessTo(JavaCMUser user) {
        if(user == null)
            return false;
        return doesAllowAccessTo(user.getObjectId());
    }

    /**
     * Check whether this access list grants access for the given user
     * @param userId the userId of the user to check
     * @return true if the specified user has access, false otherwise
     */
    public boolean doesAllowAccessTo(String userId) {
        return userObjectIds.contains(userId);
    }

    /**
     * Checks whether this list provides access for the given {@link CMAccessPermission}s.
     * @param permissions the permissions to check
     * @return true if this list provides access for all of the given permissions, false otherwise
     */
    public boolean doesGrantPermissions(CMAccessPermission... permissions) {
        for(CMAccessPermission permission : permissions) {
            if(accessPermissions.contains(permission) == false) {
                return false;
            }
        }
        return true;
    }

    @Expand
    public CloudMineRequest save(Context context, @Optional Response.Listener successListener, @Optional Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue(context);

        CloudMineRequest request = getRequest(successListener, errorListener);
        queue.add(request);
        return request;
    }

    private CloudMineRequest getRequest(Response.Listener<CreationResponse> successListener, Response.ErrorListener errorListener) {
        CloudMineRequest request;
        JavaCMUser user = getUser();
        if(user != null && user.getSessionToken() != null) {
            request = new BaseAccessListModificationRequest(this, null, successListener, errorListener);
        } else {
            if(errorListener != null) errorListener.onErrorResponse(new VolleyError("Can't save user level object when the associated user is not logged in"));
            request = CloudMineRequest.FAKE_REQUEST;
        }
        return request;
    }

    public CloudMineRequest save(Context context, Handler handler) {
        RequestQueue queue = getRequestQueue(context);

        JavaCMUser user = getUser();
        CloudMineRequest request = new BaseAccessListModificationRequest(this, null, handler);
        if(user.getSessionToken() == null) {
           request.deliverError(new VolleyError("Can't save user level object when the associated user is not logged in"));
        } else {
            queue.add(request);
        }
        return request;
    }

    /**
     * Check whether the given user owns this access list
     * @param user
     * @return true if this access list was created attached to the given user
     */
    public boolean isOwnedBy(JavaCMUser user) {
        if(user == null)
            return false;
        return user.equals(getUser());
    }

    @Override
    public String getClassName() {
        if(getClass() == BaseLocallySavableCMAccessList.class) //this way if someone extends this, it will not say this is a CMAccessList, but whatever their subclass is
            return CLASS_NAME;
        return super.getClassName();
    }

    @Override
    public String transportableRepresentation() {
        return JsonUtilities.objectToJson(this);
    }

}
