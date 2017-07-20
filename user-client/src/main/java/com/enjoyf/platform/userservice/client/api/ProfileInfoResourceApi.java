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

import com.enjoyf.platform.userservice.client.ApiCallback;
import com.enjoyf.platform.userservice.client.ApiClient;
import com.enjoyf.platform.userservice.client.ApiException;
import com.enjoyf.platform.userservice.client.ApiResponse;
import com.enjoyf.platform.userservice.client.Configuration;
import com.enjoyf.platform.userservice.client.Pair;
import com.enjoyf.platform.userservice.client.ProgressRequestBody;
import com.enjoyf.platform.userservice.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import com.enjoyf.platform.userservice.client.model.ProfileInfoVM;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileInfoResourceApi {
    private ApiClient apiClient;

    public ProfileInfoResourceApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ProfileInfoResourceApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getActiveProfilesUsingGET
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getActiveProfilesUsingGETCall(final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/api/profile-info";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "*/*"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getActiveProfilesUsingGETValidateBeforeCall(final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        
        com.squareup.okhttp.Call call = getActiveProfilesUsingGETCall(progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * getActiveProfiles
     * 
     * @return ProfileInfoVM
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ProfileInfoVM getActiveProfilesUsingGET() throws ApiException {
        ApiResponse<ProfileInfoVM> resp = getActiveProfilesUsingGETWithHttpInfo();
        return resp.getData();
    }

    /**
     * getActiveProfiles
     * 
     * @return ApiResponse&lt;ProfileInfoVM&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<ProfileInfoVM> getActiveProfilesUsingGETWithHttpInfo() throws ApiException {
        com.squareup.okhttp.Call call = getActiveProfilesUsingGETValidateBeforeCall(null, null);
        Type localVarReturnType = new TypeToken<ProfileInfoVM>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * getActiveProfiles (asynchronously)
     * 
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getActiveProfilesUsingGETAsync(final ApiCallback<ProfileInfoVM> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getActiveProfilesUsingGETValidateBeforeCall(progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<ProfileInfoVM>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
