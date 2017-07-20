# UserMobileResourceApi

All URIs are relative to *https://localhost:8888/user-service*

Method | HTTP request | Description
------------- | ------------- | -------------
[**mobileExistsUsingGET**](UserMobileResourceApi.md#mobileExistsUsingGET) | **GET** /api/user-mobiles/{mobile}/exists | mobileExists
[**sendBindCodeUsingPUT**](UserMobileResourceApi.md#sendBindCodeUsingPUT) | **PUT** /api/user-mobiles/{mobile}/code | sendBindCode
[**sendRegisterCodeUsingPUT**](UserMobileResourceApi.md#sendRegisterCodeUsingPUT) | **PUT** /api/user-mobiles/{mobile}/register/code | sendRegisterCode
[**verifyBindCodeUsingPOST**](UserMobileResourceApi.md#verifyBindCodeUsingPOST) | **POST** /api/user-mobiles/{mobile}/verfiy | verifyBindCode
[**verifyRegisterBindCodeUsingGET**](UserMobileResourceApi.md#verifyRegisterBindCodeUsingGET) | **GET** /api/user-mobiles/{mobile}/register/verfiy | verifyRegisterBindCode


<a name="mobileExistsUsingGET"></a>
# **mobileExistsUsingGET**
> Boolean mobileExistsUsingGET(mobile, profilekey)

mobileExists

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserMobileResourceApi;


UserMobileResourceApi apiInstance = new UserMobileResourceApi();
String mobile = "mobile_example"; // String | mobile
String profilekey = "profilekey_example"; // String | profilekey
try {
    Boolean result = apiInstance.mobileExistsUsingGET(mobile, profilekey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserMobileResourceApi#mobileExistsUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **mobile** | **String**| mobile |
 **profilekey** | **String**| profilekey |

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="sendBindCodeUsingPUT"></a>
# **sendBindCodeUsingPUT**
> Boolean sendBindCodeUsingPUT(mobile)

sendBindCode

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserMobileResourceApi;


UserMobileResourceApi apiInstance = new UserMobileResourceApi();
String mobile = "mobile_example"; // String | mobile
try {
    Boolean result = apiInstance.sendBindCodeUsingPUT(mobile);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserMobileResourceApi#sendBindCodeUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **mobile** | **String**| mobile |

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="sendRegisterCodeUsingPUT"></a>
# **sendRegisterCodeUsingPUT**
> Boolean sendRegisterCodeUsingPUT(mobile, profilekey)

sendRegisterCode

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserMobileResourceApi;


UserMobileResourceApi apiInstance = new UserMobileResourceApi();
String mobile = "mobile_example"; // String | mobile
String profilekey = "profilekey_example"; // String | profilekey
try {
    Boolean result = apiInstance.sendRegisterCodeUsingPUT(mobile, profilekey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserMobileResourceApi#sendRegisterCodeUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **mobile** | **String**| mobile |
 **profilekey** | **String**| profilekey |

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="verifyBindCodeUsingPOST"></a>
# **verifyBindCodeUsingPOST**
> Boolean verifyBindCodeUsingPOST(mobile, code)

verifyBindCode

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserMobileResourceApi;


UserMobileResourceApi apiInstance = new UserMobileResourceApi();
String mobile = "mobile_example"; // String | mobile
String code = "code_example"; // String | code
try {
    Boolean result = apiInstance.verifyBindCodeUsingPOST(mobile, code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserMobileResourceApi#verifyBindCodeUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **mobile** | **String**| mobile |
 **code** | **String**| code |

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="verifyRegisterBindCodeUsingGET"></a>
# **verifyRegisterBindCodeUsingGET**
> Boolean verifyRegisterBindCodeUsingGET(mobile, code)

verifyRegisterBindCode

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserMobileResourceApi;


UserMobileResourceApi apiInstance = new UserMobileResourceApi();
String mobile = "mobile_example"; // String | mobile
String code = "code_example"; // String | code
try {
    Boolean result = apiInstance.verifyRegisterBindCodeUsingGET(mobile, code);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserMobileResourceApi#verifyRegisterBindCodeUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **mobile** | **String**| mobile |
 **code** | **String**| code |

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

