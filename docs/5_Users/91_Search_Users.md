# Search User Profiles

User profiles can be queried using the [query language](#/rest_api#overview) over profiles.

This will find all users where the user's age is greater than 18. This requires you to have created a user with a profile that has an age field.

```java
CMUser.searchUserProfiles(this, SearchQuery.filter("age").greaterThan(18).searchQuery(), new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse objectResponse) {
         
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
    }
});
```

