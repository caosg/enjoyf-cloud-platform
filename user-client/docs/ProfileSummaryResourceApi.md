# ProfileSummaryResourceApi

All URIs are relative to *https://localhost:8888/user-service*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createProfileSummaryUsingPOST**](ProfileSummaryResourceApi.md#createProfileSummaryUsingPOST) | **POST** /api/profile-summaries | createProfileSummary
[**deleteProfileSummaryUsingDELETE**](ProfileSummaryResourceApi.md#deleteProfileSummaryUsingDELETE) | **DELETE** /api/profile-summaries/{id} | deleteProfileSummary
[**getAllProfileSummariesUsingGET**](ProfileSummaryResourceApi.md#getAllProfileSummariesUsingGET) | **GET** /api/profile-summaries | getAllProfileSummaries
[**getProfileSummaryByProfileNoUsingGET**](ProfileSummaryResourceApi.md#getProfileSummaryByProfileNoUsingGET) | **GET** /api/profile-summaries/by | getProfileSummaryByProfileNo
[**getProfileSummaryUsingGET**](ProfileSummaryResourceApi.md#getProfileSummaryUsingGET) | **GET** /api/profile-summaries/{id} | getProfileSummary
[**updateProfileSummaryUsingPUT**](ProfileSummaryResourceApi.md#updateProfileSummaryUsingPUT) | **PUT** /api/profile-summaries | updateProfileSummary


<a name="createProfileSummaryUsingPOST"></a>
# **createProfileSummaryUsingPOST**
> ProfileSummary createProfileSummaryUsingPOST(profileSummary)

createProfileSummary

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.ProfileSummaryResourceApi;


ProfileSummaryResourceApi apiInstance = new ProfileSummaryResourceApi();
ProfileSummary profileSummary = new ProfileSummary(); // ProfileSummary | profileSummary
try {
    ProfileSummary result = apiInstance.createProfileSummaryUsingPOST(profileSummary);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProfileSummaryResourceApi#createProfileSummaryUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profileSummary** | [**ProfileSummary**](ProfileSummary.md)| profileSummary |

### Return type

[**ProfileSummary**](ProfileSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="deleteProfileSummaryUsingDELETE"></a>
# **deleteProfileSummaryUsingDELETE**
> deleteProfileSummaryUsingDELETE(id)

deleteProfileSummary

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.ProfileSummaryResourceApi;


ProfileSummaryResourceApi apiInstance = new ProfileSummaryResourceApi();
Long id = 789L; // Long | id
try {
    apiInstance.deleteProfileSummaryUsingDELETE(id);
} catch (ApiException e) {
    System.err.println("Exception when calling ProfileSummaryResourceApi#deleteProfileSummaryUsingDELETE");
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

<a name="getAllProfileSummariesUsingGET"></a>
# **getAllProfileSummariesUsingGET**
> List&lt;ProfileSummary&gt; getAllProfileSummariesUsingGET(page, size, sort)

getAllProfileSummaries

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.ProfileSummaryResourceApi;


ProfileSummaryResourceApi apiInstance = new ProfileSummaryResourceApi();
Integer page = 56; // Integer | Page number of the requested page
Integer size = 56; // Integer | Size of a page
List<String> sort = Arrays.asList("sort_example"); // List<String> | Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
try {
    List<ProfileSummary> result = apiInstance.getAllProfileSummariesUsingGET(page, size, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProfileSummaryResourceApi#getAllProfileSummariesUsingGET");
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

[**List&lt;ProfileSummary&gt;**](ProfileSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getProfileSummaryByProfileNoUsingGET"></a>
# **getProfileSummaryByProfileNoUsingGET**
> ProfileSummary getProfileSummaryByProfileNoUsingGET(profileNo)

getProfileSummaryByProfileNo

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.ProfileSummaryResourceApi;


ProfileSummaryResourceApi apiInstance = new ProfileSummaryResourceApi();
String profileNo = "profileNo_example"; // String | profileNo
try {
    ProfileSummary result = apiInstance.getProfileSummaryByProfileNoUsingGET(profileNo);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProfileSummaryResourceApi#getProfileSummaryByProfileNoUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profileNo** | **String**| profileNo |

### Return type

[**ProfileSummary**](ProfileSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getProfileSummaryUsingGET"></a>
# **getProfileSummaryUsingGET**
> ProfileSummary getProfileSummaryUsingGET(id)

getProfileSummary

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.ProfileSummaryResourceApi;


ProfileSummaryResourceApi apiInstance = new ProfileSummaryResourceApi();
Long id = 789L; // Long | id
try {
    ProfileSummary result = apiInstance.getProfileSummaryUsingGET(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProfileSummaryResourceApi#getProfileSummaryUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| id |

### Return type

[**ProfileSummary**](ProfileSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="updateProfileSummaryUsingPUT"></a>
# **updateProfileSummaryUsingPUT**
> ProfileSummary updateProfileSummaryUsingPUT(profileSummary)

updateProfileSummary

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.ProfileSummaryResourceApi;


ProfileSummaryResourceApi apiInstance = new ProfileSummaryResourceApi();
ProfileSummary profileSummary = new ProfileSummary(); // ProfileSummary | profileSummary
try {
    ProfileSummary result = apiInstance.updateProfileSummaryUsingPUT(profileSummary);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ProfileSummaryResourceApi#updateProfileSummaryUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profileSummary** | [**ProfileSummary**](ProfileSummary.md)| profileSummary |

### Return type

[**ProfileSummary**](ProfileSummary.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

