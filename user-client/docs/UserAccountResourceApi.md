# UserAccountResourceApi

All URIs are relative to *https://localhost:8888/user-service*

Method | HTTP request | Description
------------- | ------------- | -------------
[**bindUsingPUT**](UserAccountResourceApi.md#bindUsingPUT) | **PUT** /api/accounts/bind | bind
[**changeMobileNoUsingPUT**](UserAccountResourceApi.md#changeMobileNoUsingPUT) | **PUT** /api/accounts/change_mobile | changeMobileNo
[**changePasswordUsingPUT**](UserAccountResourceApi.md#changePasswordUsingPUT) | **PUT** /api/accounts/change_password | changePassword
[**createUserAccountUsingPOST**](UserAccountResourceApi.md#createUserAccountUsingPOST) | **POST** /api/accounts | createUserAccount
[**deleteUserAccountUsingDELETE**](UserAccountResourceApi.md#deleteUserAccountUsingDELETE) | **DELETE** /api/accounts/{id} | deleteUserAccount
[**getAccountByAccountNoKeyUsingGET**](UserAccountResourceApi.md#getAccountByAccountNoKeyUsingGET) | **GET** /api/accounts/by | getAccountByAccountNoKey
[**getAccountByProfileIdUsingGET**](UserAccountResourceApi.md#getAccountByProfileIdUsingGET) | **GET** /api/accounts/profile-id/{profileId} | getAccountByProfileId
[**getAccountByProfileNoUsingGET**](UserAccountResourceApi.md#getAccountByProfileNoUsingGET) | **GET** /api/accounts/profile-no/{profileNo} | getAccountByProfileNo
[**getAllUserAccountsUsingGET**](UserAccountResourceApi.md#getAllUserAccountsUsingGET) | **GET** /api/accounts | getAllUserAccounts
[**getCurrentAccountUsingGET**](UserAccountResourceApi.md#getCurrentAccountUsingGET) | **GET** /api/accounts/current | getCurrentAccount
[**getUserAccountUsingGET**](UserAccountResourceApi.md#getUserAccountUsingGET) | **GET** /api/accounts/{id} | getUserAccount
[**getUserLoginsByAccountNoAndLogindomainsUsingGET**](UserAccountResourceApi.md#getUserLoginsByAccountNoAndLogindomainsUsingGET) | **GET** /api/accounts/logins/{accountNo} | getUserLoginsByAccountNoAndLogindomains
[**loginUsingPOST**](UserAccountResourceApi.md#loginUsingPOST) | **POST** /api/login | login
[**registerUsingPOST**](UserAccountResourceApi.md#registerUsingPOST) | **POST** /api/register | register
[**socialLoginUsingPOST**](UserAccountResourceApi.md#socialLoginUsingPOST) | **POST** /api/accounts/auth | socialLogin
[**unBindUsingPUT**](UserAccountResourceApi.md#unBindUsingPUT) | **PUT** /api/accounts/unbind | unBind
[**updateUserAccountUsingPUT**](UserAccountResourceApi.md#updateUserAccountUsingPUT) | **PUT** /api/accounts | updateUserAccount


<a name="bindUsingPUT"></a>
# **bindUsingPUT**
> AccountDTO bindUsingPUT(bindDTO)

bind

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
BindDTO bindDTO = new BindDTO(); // BindDTO | bindDTO
try {
    AccountDTO result = apiInstance.bindUsingPUT(bindDTO);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#bindUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **bindDTO** | [**BindDTO**](BindDTO.md)| bindDTO |

### Return type

[**AccountDTO**](AccountDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="changeMobileNoUsingPUT"></a>
# **changeMobileNoUsingPUT**
> UserLogin changeMobileNoUsingPUT(newMobileNo)

changeMobileNo

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
String newMobileNo = "newMobileNo_example"; // String | newMobileNo
try {
    UserLogin result = apiInstance.changeMobileNoUsingPUT(newMobileNo);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#changeMobileNoUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **newMobileNo** | **String**| newMobileNo |

### Return type

[**UserLogin**](UserLogin.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="changePasswordUsingPUT"></a>
# **changePasswordUsingPUT**
> ResponseEntity changePasswordUsingPUT(password)

changePassword

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
String password = "password_example"; // String | password
try {
    ResponseEntity result = apiInstance.changePasswordUsingPUT(password);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#changePasswordUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **password** | **String**| password |

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="createUserAccountUsingPOST"></a>
# **createUserAccountUsingPOST**
> UserAccount createUserAccountUsingPOST(userAccount)

createUserAccount

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
UserAccount userAccount = new UserAccount(); // UserAccount | userAccount
try {
    UserAccount result = apiInstance.createUserAccountUsingPOST(userAccount);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#createUserAccountUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userAccount** | [**UserAccount**](UserAccount.md)| userAccount |

### Return type

[**UserAccount**](UserAccount.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="deleteUserAccountUsingDELETE"></a>
# **deleteUserAccountUsingDELETE**
> deleteUserAccountUsingDELETE(id)

deleteUserAccount

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
Long id = 789L; // Long | id
try {
    apiInstance.deleteUserAccountUsingDELETE(id);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#deleteUserAccountUsingDELETE");
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

<a name="getAccountByAccountNoKeyUsingGET"></a>
# **getAccountByAccountNoKeyUsingGET**
> AccountDTO getAccountByAccountNoKeyUsingGET(accountNo, profileKey)

getAccountByAccountNoKey

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
String accountNo = "accountNo_example"; // String | accountNo
String profileKey = "profileKey_example"; // String | profileKey
try {
    AccountDTO result = apiInstance.getAccountByAccountNoKeyUsingGET(accountNo, profileKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#getAccountByAccountNoKeyUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **accountNo** | **String**| accountNo |
 **profileKey** | **String**| profileKey |

### Return type

[**AccountDTO**](AccountDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getAccountByProfileIdUsingGET"></a>
# **getAccountByProfileIdUsingGET**
> AccountDTO getAccountByProfileIdUsingGET(profileId)

getAccountByProfileId

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
Long profileId = 789L; // Long | profileId
try {
    AccountDTO result = apiInstance.getAccountByProfileIdUsingGET(profileId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#getAccountByProfileIdUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profileId** | **Long**| profileId |

### Return type

[**AccountDTO**](AccountDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getAccountByProfileNoUsingGET"></a>
# **getAccountByProfileNoUsingGET**
> AccountDTO getAccountByProfileNoUsingGET(profileNo)

getAccountByProfileNo

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
String profileNo = "profileNo_example"; // String | profileNo
try {
    AccountDTO result = apiInstance.getAccountByProfileNoUsingGET(profileNo);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#getAccountByProfileNoUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profileNo** | **String**| profileNo |

### Return type

[**AccountDTO**](AccountDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getAllUserAccountsUsingGET"></a>
# **getAllUserAccountsUsingGET**
> List&lt;UserAccount&gt; getAllUserAccountsUsingGET(page, size, sort)

getAllUserAccounts

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
Integer page = 56; // Integer | Page number of the requested page
Integer size = 56; // Integer | Size of a page
List<String> sort = Arrays.asList("sort_example"); // List<String> | Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
try {
    List<UserAccount> result = apiInstance.getAllUserAccountsUsingGET(page, size, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#getAllUserAccountsUsingGET");
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

[**List&lt;UserAccount&gt;**](UserAccount.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getCurrentAccountUsingGET"></a>
# **getCurrentAccountUsingGET**
> AccountDTO getCurrentAccountUsingGET()

getCurrentAccount

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
try {
    AccountDTO result = apiInstance.getCurrentAccountUsingGET();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#getCurrentAccountUsingGET");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AccountDTO**](AccountDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getUserAccountUsingGET"></a>
# **getUserAccountUsingGET**
> UserAccount getUserAccountUsingGET(id)

getUserAccount

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
Long id = 789L; // Long | id
try {
    UserAccount result = apiInstance.getUserAccountUsingGET(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#getUserAccountUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| id |

### Return type

[**UserAccount**](UserAccount.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getUserLoginsByAccountNoAndLogindomainsUsingGET"></a>
# **getUserLoginsByAccountNoAndLogindomainsUsingGET**
> List&lt;UserLogin&gt; getUserLoginsByAccountNoAndLogindomainsUsingGET(accountNo, domains)

getUserLoginsByAccountNoAndLogindomains

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
String accountNo = "accountNo_example"; // String | accountNo
List<String> domains = Arrays.asList("domains_example"); // List<String> | domains
try {
    List<UserLogin> result = apiInstance.getUserLoginsByAccountNoAndLogindomainsUsingGET(accountNo, domains);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#getUserLoginsByAccountNoAndLogindomainsUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **accountNo** | **String**| accountNo |
 **domains** | [**List&lt;String&gt;**](String.md)| domains |

### Return type

[**List&lt;UserLogin&gt;**](UserLogin.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="loginUsingPOST"></a>
# **loginUsingPOST**
> AccountDTO loginUsingPOST(loginVM)

login

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
LoginVM loginVM = new LoginVM(); // LoginVM | loginVM
try {
    AccountDTO result = apiInstance.loginUsingPOST(loginVM);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#loginUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **loginVM** | [**LoginVM**](LoginVM.md)| loginVM |

### Return type

[**AccountDTO**](AccountDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="registerUsingPOST"></a>
# **registerUsingPOST**
> AccountDTO registerUsingPOST(registerReqDTO)

register

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
RegisterReqDTO registerReqDTO = new RegisterReqDTO(); // RegisterReqDTO | registerReqDTO
try {
    AccountDTO result = apiInstance.registerUsingPOST(registerReqDTO);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#registerUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **registerReqDTO** | [**RegisterReqDTO**](RegisterReqDTO.md)| registerReqDTO |

### Return type

[**AccountDTO**](AccountDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="socialLoginUsingPOST"></a>
# **socialLoginUsingPOST**
> AccountDTO socialLoginUsingPOST(registerReqDTO)

socialLogin

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
RegisterReqDTO registerReqDTO = new RegisterReqDTO(); // RegisterReqDTO | registerReqDTO
try {
    AccountDTO result = apiInstance.socialLoginUsingPOST(registerReqDTO);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#socialLoginUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **registerReqDTO** | [**RegisterReqDTO**](RegisterReqDTO.md)| registerReqDTO |

### Return type

[**AccountDTO**](AccountDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="unBindUsingPUT"></a>
# **unBindUsingPUT**
> Boolean unBindUsingPUT(accountNo, domain, profileKey)

unBind

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
String accountNo = "accountNo_example"; // String | accountNo
String domain = "domain_example"; // String | domain
String profileKey = "profileKey_example"; // String | profileKey
try {
    Boolean result = apiInstance.unBindUsingPUT(accountNo, domain, profileKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#unBindUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **accountNo** | **String**| accountNo |
 **domain** | **String**| domain |
 **profileKey** | **String**| profileKey |

### Return type

**Boolean**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="updateUserAccountUsingPUT"></a>
# **updateUserAccountUsingPUT**
> UserAccount updateUserAccountUsingPUT(userAccount)

updateUserAccount

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.UserAccountResourceApi;


UserAccountResourceApi apiInstance = new UserAccountResourceApi();
UserAccount userAccount = new UserAccount(); // UserAccount | userAccount
try {
    UserAccount result = apiInstance.updateUserAccountUsingPUT(userAccount);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UserAccountResourceApi#updateUserAccountUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userAccount** | [**UserAccount**](UserAccount.md)| userAccount |

### Return type

[**UserAccount**](UserAccount.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

