# UserTokenResourceApi

All URIs are relative to *https://localhost:8888/user-service*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createUserTokenUsingPOST**](UserTokenResourceApi.md#createUserTokenUsingPOST) | **POST** /api/user-tokens | createUserToken
[**deleteUserTokenUsingDELETE**](UserTokenResourceApi.md#deleteUserTokenUsingDELETE) | **DELETE** /api/user-tokens/{id} | deleteUserToken
[**getAllUserTokensUsingGET**](UserTokenResourceApi.md#getAllUserTokensUsingGET) | **GET** /api/user-tokens | getAllUserTokens
[**getUserTokenUsingGET**](UserTokenResourceApi.md#getUserTokenUsingGET) | **GET** /api/user-tokens/{id} | getUserToken
[**updateUserTokenUsingPUT**](UserTokenResourceApi.md#updateUserTokenUsingPUT) | **PUT** /api/user-tokens | updateUserToken


<a name="createUserTokenUsingPOST"></a>
# **createUserTokenUsingPOST**
> UserToken createUserTokenUsingPOST(userToken)

createUserToken

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserTokenResourceApi;


UserTokenResourceApi apiInstance = new UserTokenResourceApi();
UserToken userToken = new UserToken(); // UserToken | userToken
try {
    UserToken result = apiInstance.createUserTokenUsingPOST(userToken);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserTokenResourceApi#createUserTokenUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userToken** | [**UserToken**](UserToken.md)| userToken |

### Return type

[**UserToken**](UserToken.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="deleteUserTokenUsingDELETE"></a>
# **deleteUserTokenUsingDELETE**
> deleteUserTokenUsingDELETE(id)

deleteUserToken

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserTokenResourceApi;


UserTokenResourceApi apiInstance = new UserTokenResourceApi();
Long id = 789L; // Long | id
try {
    apiInstance.deleteUserTokenUsingDELETE(id);
} catch (ApiException e) {
    System.err.println("Exception when calling UserTokenResourceApi#deleteUserTokenUsingDELETE");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| id |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getAllUserTokensUsingGET"></a>
# **getAllUserTokensUsingGET**
> List&lt;UserToken&gt; getAllUserTokensUsingGET()

getAllUserTokens

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserTokenResourceApi;


UserTokenResourceApi apiInstance = new UserTokenResourceApi();
try {
    List<UserToken> result = apiInstance.getAllUserTokensUsingGET();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserTokenResourceApi#getAllUserTokensUsingGET");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;UserToken&gt;**](UserToken.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getUserTokenUsingGET"></a>
# **getUserTokenUsingGET**
> UserToken getUserTokenUsingGET(id)

getUserToken

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserTokenResourceApi;


UserTokenResourceApi apiInstance = new UserTokenResourceApi();
Long id = 789L; // Long | id
try {
    UserToken result = apiInstance.getUserTokenUsingGET(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserTokenResourceApi#getUserTokenUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| id |

### Return type

[**UserToken**](UserToken.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="updateUserTokenUsingPUT"></a>
# **updateUserTokenUsingPUT**
> UserToken updateUserTokenUsingPUT(userToken)

updateUserToken

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserTokenResourceApi;


UserTokenResourceApi apiInstance = new UserTokenResourceApi();
UserToken userToken = new UserToken(); // UserToken | userToken
try {
    UserToken result = apiInstance.updateUserTokenUsingPUT(userToken);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserTokenResourceApi#updateUserTokenUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userToken** | [**UserToken**](UserToken.md)| userToken |

### Return type

[**UserToken**](UserToken.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

