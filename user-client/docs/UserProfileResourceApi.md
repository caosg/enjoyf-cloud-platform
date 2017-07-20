# UserProfileResourceApi

All URIs are relative to *https://localhost:8888/user-service*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createUserProfileUsingPOST**](UserProfileResourceApi.md#createUserProfileUsingPOST) | **POST** /api/user-profiles | createUserProfile
[**deleteUserProfileUsingDELETE**](UserProfileResourceApi.md#deleteUserProfileUsingDELETE) | **DELETE** /api/user-profiles/{id} | deleteUserProfile
[**getAllUserProfilesUsingGET**](UserProfileResourceApi.md#getAllUserProfilesUsingGET) | **GET** /api/user-profiles | getAllUserProfiles
[**getProfileByLikeNickUsingGET**](UserProfileResourceApi.md#getProfileByLikeNickUsingGET) | **GET** /api/user-profiles/by | getProfileByLikeNick
[**getProfilesByIdsUsingGET**](UserProfileResourceApi.md#getProfilesByIdsUsingGET) | **GET** /api/user-profiles/ids | getProfilesByIds
[**getProfilesByProfileNosUsingGET**](UserProfileResourceApi.md#getProfilesByProfileNosUsingGET) | **GET** /api/user-profiles/profileno | getProfilesByProfileNos
[**getUserProfileByProfileNoUsingGET**](UserProfileResourceApi.md#getUserProfileByProfileNoUsingGET) | **GET** /api/user-profiles/profileno/{profileNo} | getUserProfileByProfileNo
[**getUserProfileUsingGET**](UserProfileResourceApi.md#getUserProfileUsingGET) | **GET** /api/user-profiles/{id} | getUserProfile
[**updateUserProfileUsingPUT**](UserProfileResourceApi.md#updateUserProfileUsingPUT) | **PUT** /api/user-profiles | updateUserProfile


<a name="createUserProfileUsingPOST"></a>
# **createUserProfileUsingPOST**
> UserProfile createUserProfileUsingPOST(userProfile)

createUserProfile

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserProfileResourceApi;


UserProfileResourceApi apiInstance = new UserProfileResourceApi();
UserProfile userProfile = new UserProfile(); // UserProfile | userProfile
try {
    UserProfile result = apiInstance.createUserProfileUsingPOST(userProfile);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserProfileResourceApi#createUserProfileUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userProfile** | [**UserProfile**](UserProfile.md)| userProfile |

### Return type

[**UserProfile**](UserProfile.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="deleteUserProfileUsingDELETE"></a>
# **deleteUserProfileUsingDELETE**
> deleteUserProfileUsingDELETE(id)

deleteUserProfile

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserProfileResourceApi;


UserProfileResourceApi apiInstance = new UserProfileResourceApi();
Long id = 789L; // Long | id
try {
    apiInstance.deleteUserProfileUsingDELETE(id);
} catch (ApiException e) {
    System.err.println("Exception when calling UserProfileResourceApi#deleteUserProfileUsingDELETE");
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

<a name="getAllUserProfilesUsingGET"></a>
# **getAllUserProfilesUsingGET**
> List&lt;UserProfile&gt; getAllUserProfilesUsingGET(page, size, sort)

getAllUserProfiles

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserProfileResourceApi;


UserProfileResourceApi apiInstance = new UserProfileResourceApi();
Integer page = 56; // Integer | Page number of the requested page
Integer size = 56; // Integer | Size of a page
List<String> sort = Arrays.asList("sort_example"); // List<String> | Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
try {
    List<UserProfile> result = apiInstance.getAllUserProfilesUsingGET(page, size, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserProfileResourceApi#getAllUserProfilesUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Integer**| Page number of the requested page | [optional]
 **size** | **Integer**| Size of a page | [optional]
 **sort** | [**List&lt;String&gt;**](String.md)| Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported. | [optional]

### Return type

[**List&lt;UserProfile&gt;**](UserProfile.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getProfileByLikeNickUsingGET"></a>
# **getProfileByLikeNickUsingGET**
> List&lt;UserProfile&gt; getProfileByLikeNickUsingGET(title)

getProfileByLikeNick

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserProfileResourceApi;


UserProfileResourceApi apiInstance = new UserProfileResourceApi();
String title = "nick_example"; // String | title
try {
    List<UserProfile> result = apiInstance.getProfileByLikeNickUsingGET(title);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserProfileResourceApi#getProfileByLikeNickUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **title** | **String**| title |

### Return type

[**List&lt;UserProfile&gt;**](UserProfile.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getProfilesByIdsUsingGET"></a>
# **getProfilesByIdsUsingGET**
> List&lt;UserProfile&gt; getProfilesByIdsUsingGET(ids)

getProfilesByIds

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserProfileResourceApi;


UserProfileResourceApi apiInstance = new UserProfileResourceApi();
List<Long> ids = Arrays.asList(56L); // List<Long> | ids
try {
    List<UserProfile> result = apiInstance.getProfilesByIdsUsingGET(ids);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserProfileResourceApi#getProfilesByIdsUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ids** | [**List&lt;Long&gt;**](Long.md)| ids |

### Return type

[**List&lt;UserProfile&gt;**](UserProfile.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getProfilesByProfileNosUsingGET"></a>
# **getProfilesByProfileNosUsingGET**
> List&lt;UserProfile&gt; getProfilesByProfileNosUsingGET(profilenos)

getProfilesByProfileNos

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserProfileResourceApi;


UserProfileResourceApi apiInstance = new UserProfileResourceApi();
List<String> profilenos = Arrays.asList("profilenos_example"); // List<String> | profilenos
try {
    List<UserProfile> result = apiInstance.getProfilesByProfileNosUsingGET(profilenos);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserProfileResourceApi#getProfilesByProfileNosUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profilenos** | [**List&lt;String&gt;**](String.md)| profilenos |

### Return type

[**List&lt;UserProfile&gt;**](UserProfile.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getUserProfileByProfileNoUsingGET"></a>
# **getUserProfileByProfileNoUsingGET**
> UserProfile getUserProfileByProfileNoUsingGET(profileNo)

getUserProfileByProfileNo

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserProfileResourceApi;


UserProfileResourceApi apiInstance = new UserProfileResourceApi();
String profileNo = "profileNo_example"; // String | profileNo
try {
    UserProfile result = apiInstance.getUserProfileByProfileNoUsingGET(profileNo);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserProfileResourceApi#getUserProfileByProfileNoUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profileNo** | **String**| profileNo |

### Return type

[**UserProfile**](UserProfile.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getUserProfileUsingGET"></a>
# **getUserProfileUsingGET**
> UserProfile getUserProfileUsingGET(id)

getUserProfile

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserProfileResourceApi;


UserProfileResourceApi apiInstance = new UserProfileResourceApi();
Long id = 789L; // Long | id
try {
    UserProfile result = apiInstance.getUserProfileUsingGET(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserProfileResourceApi#getUserProfileUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| id |

### Return type

[**UserProfile**](UserProfile.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="updateUserProfileUsingPUT"></a>
# **updateUserProfileUsingPUT**
> UserProfile updateUserProfileUsingPUT(userProfile)

updateUserProfile

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserProfileResourceApi;


UserProfileResourceApi apiInstance = new UserProfileResourceApi();
UserProfile userProfile = new UserProfile(); // UserProfile | userProfile
try {
    UserProfile result = apiInstance.updateUserProfileUsingPUT(userProfile);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserProfileResourceApi#updateUserProfileUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userProfile** | [**UserProfile**](UserProfile.md)| userProfile |

### Return type

[**UserProfile**](UserProfile.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

