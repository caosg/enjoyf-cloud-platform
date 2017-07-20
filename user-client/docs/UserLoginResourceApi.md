# UserLoginResourceApi

All URIs are relative to *https://localhost:8888/user-service*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createUserLoginUsingPOST**](UserLoginResourceApi.md#createUserLoginUsingPOST) | **POST** /api/user-logins | createUserLogin
[**deleteUserLoginUsingDELETE**](UserLoginResourceApi.md#deleteUserLoginUsingDELETE) | **DELETE** /api/user-logins/{id} | deleteUserLogin
[**getAllUserLoginsUsingGET**](UserLoginResourceApi.md#getAllUserLoginsUsingGET) | **GET** /api/user-logins | getAllUserLogins
[**getUserLoginByLoginUsingGET**](UserLoginResourceApi.md#getUserLoginByLoginUsingGET) | **GET** /api/logins/{login} | getUserLoginByLogin
[**getUserLoginUsingGET**](UserLoginResourceApi.md#getUserLoginUsingGET) | **GET** /api/user-logins/{id} | getUserLogin
[**updateUserLoginUsingPUT**](UserLoginResourceApi.md#updateUserLoginUsingPUT) | **PUT** /api/user-logins | updateUserLogin


<a name="createUserLoginUsingPOST"></a>
# **createUserLoginUsingPOST**
> UserLogin createUserLoginUsingPOST(userLogin)

createUserLogin

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserLoginResourceApi;


UserLoginResourceApi apiInstance = new UserLoginResourceApi();
UserLogin userLogin = new UserLogin(); // UserLogin | userLogin
try {
    UserLogin result = apiInstance.createUserLoginUsingPOST(userLogin);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserLoginResourceApi#createUserLoginUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userLogin** | [**UserLogin**](UserLogin.md)| userLogin |

### Return type

[**UserLogin**](UserLogin.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="deleteUserLoginUsingDELETE"></a>
# **deleteUserLoginUsingDELETE**
> deleteUserLoginUsingDELETE(id)

deleteUserLogin

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserLoginResourceApi;


UserLoginResourceApi apiInstance = new UserLoginResourceApi();
Long id = 789L; // Long | id
try {
    apiInstance.deleteUserLoginUsingDELETE(id);
} catch (ApiException e) {
    System.err.println("Exception when calling UserLoginResourceApi#deleteUserLoginUsingDELETE");
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

<a name="getAllUserLoginsUsingGET"></a>
# **getAllUserLoginsUsingGET**
> List&lt;UserLogin&gt; getAllUserLoginsUsingGET(page, size, sort)

getAllUserLogins

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserLoginResourceApi;


UserLoginResourceApi apiInstance = new UserLoginResourceApi();
Integer page = 56; // Integer | Page number of the requested page
Integer size = 56; // Integer | Size of a page
List<String> sort = Arrays.asList("sort_example"); // List<String> | Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
try {
    List<UserLogin> result = apiInstance.getAllUserLoginsUsingGET(page, size, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserLoginResourceApi#getAllUserLoginsUsingGET");
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

[**List&lt;UserLogin&gt;**](UserLogin.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getUserLoginByLoginUsingGET"></a>
# **getUserLoginByLoginUsingGET**
> UserLogin getUserLoginByLoginUsingGET(login)

getUserLoginByLogin

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserLoginResourceApi;


UserLoginResourceApi apiInstance = new UserLoginResourceApi();
String login = "login_example"; // String | login
try {
    UserLogin result = apiInstance.getUserLoginByLoginUsingGET(login);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserLoginResourceApi#getUserLoginByLoginUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**| login |

### Return type

[**UserLogin**](UserLogin.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getUserLoginUsingGET"></a>
# **getUserLoginUsingGET**
> UserLogin getUserLoginUsingGET(id)

getUserLogin

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserLoginResourceApi;


UserLoginResourceApi apiInstance = new UserLoginResourceApi();
Long id = 789L; // Long | id
try {
    UserLogin result = apiInstance.getUserLoginUsingGET(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserLoginResourceApi#getUserLoginUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| id |

### Return type

[**UserLogin**](UserLogin.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="updateUserLoginUsingPUT"></a>
# **updateUserLoginUsingPUT**
> UserLogin updateUserLoginUsingPUT(userLogin)

updateUserLogin

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserLoginResourceApi;


UserLoginResourceApi apiInstance = new UserLoginResourceApi();
UserLogin userLogin = new UserLogin(); // UserLogin | userLogin
try {
    UserLogin result = apiInstance.updateUserLoginUsingPUT(userLogin);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserLoginResourceApi#updateUserLoginUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userLogin** | [**UserLogin**](UserLogin.md)| userLogin |

### Return type

[**UserLogin**](UserLogin.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

