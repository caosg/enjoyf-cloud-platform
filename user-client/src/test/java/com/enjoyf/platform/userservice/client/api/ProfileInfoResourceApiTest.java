/*
 * userservice API
 * userservice API documentation
 *
 * OpenAPI spec version: 0.0.1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.enjoyf.platform.userservice.client.api;

import com.enjoyf.platform.userservice.client.ApiException;
import com.enjoyf.platform.userservice.client.model.ProfileInfoVM;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ProfileInfoResourceApi
 */
@Ignore
public class ProfileInfoResourceApiTest {

    private final ProfileInfoResourceApi api = new ProfileInfoResourceApi();

    
    /**
     * getActiveProfiles
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getActiveProfilesUsingGETTest() throws ApiException {
        ProfileInfoVM response = api.getActiveProfilesUsingGET();

        // TODO: test validations
    }
    
}
