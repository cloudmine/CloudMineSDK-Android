package com.cloudmine.api.rest;

import android.content.Context;
import android.graphics.Bitmap;
import com.android.cloudmine.Response;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMFile;
import com.cloudmine.api.CMUser;
import com.cloudmine.api.CacheableCMFile;
import com.cloudmine.api.SearchQuery;
import com.cloudmine.api.integration.CMFileIntegrationTest;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.FileCreationResponse;
import com.cloudmine.api.rest.response.FileLoadResponse;
import com.cloudmine.test.CloudMineTestRunner;
import com.cloudmine.test.ExtendedCMFile;
import com.cloudmine.test.ResponseCallbackTuple;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.cloudmine.test.AsyncTestResultsCoordinator.waitThenAssertTestResults;
import static com.cloudmine.test.ResponseCallbackTuple.defaultFailureListener;
import static com.cloudmine.test.ResponseCallbackTuple.testCallback;
import static junit.framework.Assert.*;


/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidCMFileIntegrationTest extends CMFileIntegrationTest {
    private static final String fileId = "83c1b8b88c984c269f0626d955759963";
    Context applicationContext;

    @Before
    public void setUp() {
        Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
        applicationContext = Robolectric.application.getApplicationContext();
        CMApiCredentials.initialize(APP_ID, API_KEY, applicationContext);

        super.setUp();
    }

    @Test
    public void testFileLoadRequest() {
        final CMFile file = new CMFile(getObjectInputStream(), randomString(), "application/oop");
        file.save(hasSuccess);
        waitThenAssertTestResults();

        CacheableCMFile.loadFile(applicationContext, file.getFileId(), getSuccessLoadListener(file), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();

        final CMFile userFile = new CMFile(getObjectInputStream(), randomString(), "application/oop");
        final CMUser user = loggedInUser();
        userFile.setSaveWith(user);
        userFile.save(hasSuccess);
        waitThenAssertTestResults();

        CacheableCMFile.loadFile(applicationContext, userFile.getFileId(), user.getSessionToken(), getSuccessLoadListener(userFile), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();
    }

    @Test
    public void testFileInsertRequest() {
        final CacheableCMFile file = new CacheableCMFile(getObjectInputStream(), randomString(), "application/oop");
        file.save(applicationContext, getSuccessFileCreationListener(file), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();

        CacheableCMFile.loadFile(applicationContext, file.getFileId(), getSuccessLoadListener(file), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();

        final CacheableCMFile userFile = new CacheableCMFile(getObjectInputStream(), randomString(), "application/oop");
        final CMUser user = loggedInUser();
        userFile.setSaveWith(user);
        userFile.save(applicationContext, getSuccessFileCreationListener(userFile), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();

        CacheableCMFile.loadFile(applicationContext, userFile.getFileId(), user.getSessionToken(), getSuccessLoadListener(userFile), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();

        final CacheableCMFile otherUserFile = new CacheableCMFile(getObjectInputStream(), randomString(), "application/oop");
        otherUserFile.save(applicationContext, user.getSessionToken(), getSuccessFileCreationListener(otherUserFile), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();

        CacheableCMFile.loadFile(applicationContext, otherUserFile.getFileId(), user.getSessionToken(), getSuccessLoadListener(otherUserFile), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();
    }

    @Test
    public void testFileDeleteRequest() {

        final CacheableCMFile file = new CacheableCMFile(getObjectInputStream(), randomString(), "application/oop");
        file.save(applicationContext, getSuccessFileCreationListener(file), ResponseCallbackTuple.defaultFailureListener);
        waitThenAssertTestResults();

    }

    @Test
    @Ignore
    public void testLoadMetadata() {
        final String text = "This is an object stream output";
        ExtendedCMFile file = new ExtendedCMFile(getObjectInputStream(), text);
        file.save(applicationContext, ResponseCallbackTuple.<FileCreationResponse >hasSuccess(), defaultFailureListener);
        waitThenAssertTestResults();
        final String fileId = file.getFileId();
        BaseObjectLoadRequest request = new ObjectLoadRequestBuilder(testCallback(new Response.Listener<CMObjectResponse>() {
            @Override
            public void onResponse(CMObjectResponse response) {

            }
        }), ResponseCallbackTuple.defaultFailureListener).search(SearchQuery.filter(JsonUtilities.TYPE_KEY).equal(CMFile.TYPE_VALUE).searchQuery()).build();
        SharedRequestQueueHolders.getRequestQueue(applicationContext).add(request);
        waitThenAssertTestResults();
    }

    @Test
    @Ignore //can't decode bitmaps with robolectric
    public void testImageLoad() {
        SharedRequestQueueHolders.getRequestQueue(applicationContext).add(
                new ImageLoadRequest(fileId, Bitmap.Config.RGB_565, testCallback(new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                        assertNotNull(bitmap);
                    }
                }),ResponseCallbackTuple.defaultFailureListener)
        );
        waitThenAssertTestResults(2000);
    }

    private ResponseCallbackTuple<FileCreationResponse> getSuccessFileCreationListener(final CacheableCMFile file) {
        return testCallback(new Response.Listener<FileCreationResponse>() {
            @Override
            public void onResponse(FileCreationResponse fileCreationResponse) {
                assertTrue(fileCreationResponse.wasSuccess());
                assertEquals(file.getFileId(), fileCreationResponse.getfileId());
            }
        });
    }

    private ResponseCallbackTuple<FileLoadResponse> getSuccessLoadListener(final CMFile userFile) {
        return testCallback(new Response.Listener<FileLoadResponse>() {
            @Override
            public void onResponse(FileLoadResponse fileLoadResponse) {
                CMFile loadedFile = fileLoadResponse.getFile();

                assertFilesEqual(loadedFile, userFile);
            }
        });
    }

    private void assertFilesEqual(CMFile loadedFile, CMFile file) {
//        assertArrayEquals(file.getFileContents(), loadedFile.getFileContents());
        assertEquals(file.getFileId(), loadedFile.getFileId());
        assertEquals(file.getObjectId(), loadedFile.getObjectId());
        assertEquals(file.getMimeType(), loadedFile.getMimeType());
    }
}
