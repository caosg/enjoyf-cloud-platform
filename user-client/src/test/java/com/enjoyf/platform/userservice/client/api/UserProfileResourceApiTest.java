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
import com.enjoyf.platform.userservice.client.model.UserProfile;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for UserProfileResourceApi
 */
@Ignore
public class UserProfileResourceApiTest {

    private final UserProfileResourceApi api = new UserProfileResourceApi();

    
    /**
     * createUserProfile
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createUserProfileUsingPOSTTest() throws ApiException {
        UserProfile userProfile = null;
        UserProfile response = api.createUserProfileUsingPOST(userProfile);

        // TODO: test validations
    }
    
    /**
     * deleteUserProfile
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteUserProfileUsingDELETETest() throws ApiException {
        Long id = null;
        api.deleteUserProfileUsingDELETE(id);

        // TODO: test validations
    }
    
    /**
     * getAllUserProfiles
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAllUserProfilesUsingGETTest() throws ApiException {
        Integer page = null;
        Integer size = null;
        List<String> sort = null;
        List<UserProfile> response = api.getAllUserProfilesUsingGET(page, size, sort);

        // TODO: test validations
    }
    
    /**
     * getProfileByLikeNick
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getProfileByLikeNickUsingGETTest() throws ApiException {
        String nick = null;
        List<UserProfile> response = api.getProfileByLikeNickUsingGET(nick);

        // TODO: test validations
    }
    
    /**
     * getProfilesByIds
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getProfilesByIdsUsingGETTest() throws ApiException {
        List<Long> ids = null;
        List<UserProfile> response = api.getProfilesByIdsUsingGET(ids);

        // TODO: test validations
    }
    
    /**
     * getProfilesByProfileNos
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getProfilesByProfileNosUsingGETTest() throws ApiException {
        List<String> profilenos = null;
        List<UserProfile> response = api.getProfilesByProfileNosUsingGET(profilenos);

        // TODO: test validations
    }
    
    /**
     * getUserProfileByProfileNo
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getUserProfileByProfileNoUsingGETTest() throws ApiException {
        String profileNo = null;
        UserProfile response = api.getUserProfileByProfileNoUsingGET(profileNo);

        // TODO: test validations
    }
    
    /**
     * getUserProfile
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getUserProfileUsingGETTest() throws ApiException {
        Long id = null;
        UserProfile response = api.getUserProfileUsingGET(id);

        // TODO: test validations
    }
    
    /**
     * updateUserProfile
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void updateUserProfileUsingPUTTest() throws ApiException {
        UserProfile userProfile = null;
        UserProfile response = api.updateUserProfileUsingPUT(userProfile);

        // TODO: test validations
    }
    
}
