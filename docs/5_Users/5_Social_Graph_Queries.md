# Social Graph Queries

{{note 'You will need to configure each social network you plan on using in your app using each network\'s website.'}}

In addition to logging in through social networks, queries can also be run on the networks through the API. Any query you can run on a social network directly can be made through the CloudMine API. This allows for a single point of access for networking calls, as well as letting the CloudMine SDK handle the creation, delivery, and response of the call. These calls are made through the [UserCMWebService](/docs/javadocs/com/cloudmine/api/rest/UserCMWebService.html) class, and require a user to be logged in through any social network you want to send queries to.

Let's say we have a user logged in through Twitter, and we want to get the home timeline of the user. The [Twitter Docs](https://dev.twitter.com/docs/api/1.1/get/statuses/home_timeline) for the timeline explain the call that needs to be made.

{{{{raw-helper}}}}
```java

UserCMWebService socialService = CMWebService.getService().setLoggedInUser(aUserLoggedIntoANetwork.getSessionToken());
 
socialService.asyncSocialGraphQueryOnNetwork(
              CMSocial.Service.TWITTER,
              HttpVerb.GET,
              "statuses/home_timeline.json",
              new HashMap<String, Object>() {{ put("include_entities", Boolean.valueOf(true)); }},
              new SocialGraphCallback() {
                  public void onCompletion(SocialGraphResponse response) {
                  }
              }
              );
```
{{{{/raw-helper}}}}

Any extra parameters you want included in the query should be passed in through the parameters, these will be encoded as JSON and passed through to the service you're targeting. The SocialGraphResponse has the body response, which can be gotten by getMessageBody(). This returns the body as a String as the framework cannot make any assumption of the type of data returning.

POST requests are also easy to make. When making requests it is a good idea to always set the "Content-Type" header fields. Some API's may work without any set, but it is better to be safe. CloudMine makes no assumption as to what type of data you are sending. For example, posting a gist to Github:

{{{{raw-helper}}}}
```java
String gist = "{\"description\":\"Testing\",\"public\":true,\"files\":{\"FileName.txt\":{\"content\":\"String file contents\"}}}";
 
socialService.asyncSocialGraphQueryOnNetwork(
        CMSocial.Service.GITHUB,
        HttpVerb.POST,
        "gists",
        null,
        new HashMap<String, Object>() {{ put("Content-Type", "application/json"); }},
        new ByteArrayEntity(gist.getBytes(Charset.forName("UTF-8"))),
        new SocialGraphCallback() {
            public void onCompletion(SocialGraphResponse response) {
                if (response.wasSuccess()) {
                  // Turn response.getMessageBody() back into Json
                } else {
                  // There was an issue!
            }
        }
        );
```
{{{{/raw-helper}}}}

{{note 'Certain services require additional permissions in order to do certain actions. Github and LinkedIn are two such services; Github requires the `gist` scope in order to posts gists as a user. These must be asked for when the user logs in. See [Social Login](#/android_and_java#user-login-with-social-network) for how to pass in these scope parameters.'}}

{{note "Headers are a tricky business. CloudMine tries hard to pass back appropriate headers from the target service, but not all are passed back. Information about the request (content-type, content-length) should always be passed back, but information about the target services (server, cache-control, x-powered-by) will reflect CloudMine, not the target service."}}

In the gist request, the data is just a string formatted into JSON, and is added to the POST request. The data for these request can be anything, so the String needs to be converted into a ByteArrayEntity object. In this case, the data could also have been created from a Map and parsed into JSON by a library.
