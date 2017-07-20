# UserSettingsResourceApi

All URIs are relative to *https://localhost:8888/user-service*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createUserSettingsUsingPOST**](UserSettingsResourceApi.md#createUserSettingsUsingPOST) | **POST** /api/user-settings | createUserSettings
[**deleteUserSettingsUsingDELETE**](UserSettingsResourceApi.md#deleteUserSettingsUsingDELETE) | **DELETE** /api/user-settings/{id} | deleteUserSettings
[**getAllUserSettingsUsingGET**](UserSettingsResourceApi.md#getAllUserSettingsUsingGET) | **GET** /api/user-settings | getAllUserSettings
[**getUserSettingsByProfileNoUsingGET**](UserSettingsResourceApi.md#getUserSettingsByProfileNoUsingGET) | **GET** /api/user-settings/by | getUserSettingsByProfileNo
[**getUserSettingsUsingGET**](UserSettingsResourceApi.md#getUserSettingsUsingGET) | **GET** /api/user-settings/{id} | getUserSettings
[**updateUserSettingsByProfileNoUsingPUT**](UserSettingsResourceApi.md#updateUserSettingsByProfileNoUsingPUT) | **PUT** /api/user-settings/by-profile | updateUserSettingsByProfileNo
[**updateUserSettingsUsingPUT**](UserSettingsResourceApi.md#updateUserSettingsUsingPUT) | **PUT** /api/user-settings | updateUserSettings


<a name="createUserSettingsUsingPOST"></a>
# **createUserSettingsUsingPOST**
> UserSettings createUserSettingsUsingPOST(userSettings)

createUserSettings

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserSettingsResourceApi;


UserSettingsResourceApi apiInstance = new UserSettingsResourceApi();
UserSettings userSettings = new UserSettings(); // UserSettings | userSettings
try {
    UserSettings result = apiInstance.createUserSettingsUsingPOST(userSettings);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSettingsResourceApi#createUserSettingsUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userSettings** | [**UserSettings**](UserSettings.md)| userSettings |

### Return type

[**UserSettings**](UserSettings.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="deleteUserSettingsUsingDELETE"></a>
# **deleteUserSettingsUsingDELETE**
> deleteUserSettingsUsingDELETE(id)

deleteUserSettings

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserSettingsResourceApi;


UserSettingsResourceApi apiInstance = new UserSettingsResourceApi();
Long id = 789L; // Long | id
try {
    apiInstance.deleteUserSettingsUsingDELETE(id);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSettingsResourceApi#deleteUserSettingsUsingDELETE");
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

<a name="getAllUserSettingsUsingGET"></a>
# **getAllUserSettingsUsingGET**
> List&lt;UserSettings&gt; getAllUserSettingsUsingGET(page, size, sort)

getAllUserSettings

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserSettingsResourceApi;


UserSettingsResourceApi apiInstance = new UserSettingsResourceApi();
Integer page = 56; // Integer | Page number of the requested page
Integer size = 56; // Integer | Size of a page
List<String> sort = Arrays.asList("sort_example"); // List<String> | Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
try {
    List<UserSettings> result = apiInstance.getAllUserSettingsUsingGET(page, size, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSettingsResourceApi#getAllUserSettingsUsingGET");
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

[**List&lt;UserSettings&gt;**](UserSettings.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getUserSettingsByProfileNoUsingGET"></a>
# **getUserSettingsByProfileNoUsingGET**
> UserSettings getUserSettingsByProfileNoUsingGET(profileNo)

getUserSettingsByProfileNo

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserSettingsResourceApi;


UserSettingsResourceApi apiInstance = new UserSettingsResourceApi();
String profileNo = "profileNo_example"; // String | profileNo
try {
    UserSettings result = apiInstance.getUserSettingsByProfileNoUsingGET(profileNo);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSettingsResourceApi#getUserSettingsByProfileNoUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profileNo** | **String**| profileNo |

### Return type

[**UserSettings**](UserSettings.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getUserSettingsUsingGET"></a>
# **getUserSettingsUsingGET**
> UserSettings getUserSettingsUsingGET(id)

getUserSettings

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserSettingsResourceApi;


UserSettingsResourceApi apiInstance = new UserSettingsResourceApi();
Long id = 789L; // Long | id
try {
    UserSettings result = apiInstance.getUserSettingsUsingGET(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSettingsResourceApi#getUserSettingsUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| id |

### Return type

[**UserSettings**](UserSettings.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="updateUserSettingsByProfileNoUsingPUT"></a>
# **updateUserSettingsByProfileNoUsingPUT**
> UserSettings updateUserSettingsByProfileNoUsingPUT(userSettings)

updateUserSettingsByProfileNo

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserSettingsResourceApi;


UserSettingsResourceApi apiInstance = new UserSettingsResourceApi();
UserSettings userSettings = new UserSettings(); // UserSettings | userSettings
try {
    UserSettings result = apiInstance.updateUserSettingsByProfileNoUsingPUT(userSettings);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSettingsResourceApi#updateUserSettingsByProfileNoUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userSettings** | [**UserSettings**](UserSettings.md)| userSettings |

### Return type

[**UserSettings**](UserSettings.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="updateUserSettingsUsingPUT"></a>
# **updateUserSettingsUsingPUT**
> UserSettings updateUserSettingsUsingPUT(userSettings)

updateUserSettings

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserSettingsResourceApi;


UserSettingsResourceApi apiInstance = new UserSettingsResourceApi();
UserSettings userSettings = new UserSettings(); // UserSettings | userSettings
try {
    UserSettings result = apiInstance.updateUserSettingsUsingPUT(userSettings);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserSettingsResourceApi#updateUserSettingsUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userSettings** | [**UserSettings**](UserSettings.md)| userSettings |

### Return type

[**UserSettings**](UserSettings.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

