package com.cloudmine.api.rest;

/**
 * Copyright CloudMine LLC
 * User: johnmccarthy
 * Date: 6/13/12, 3:50 PM
 */
public class CMStore {

    private final CMWebService applicationService;

    public CMStore() {
        applicationService = AndroidCMWebService.service();
    }
}
