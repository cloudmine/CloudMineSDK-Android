package com.cloudmine.api.rest;

import com.cloudmine.test.CloudMineTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@RunWith(CloudMineTestRunner.class)
public class AndroidBase64EncoderTest {

    @Test
    public void justPrintStuff() {
        System.out.println("GOT: " + new AndroidBase64Encoder().encode("test@test.com:test"));
    }
}
