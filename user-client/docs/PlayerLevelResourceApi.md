# PlayerLevelResourceApi

All URIs are relative to *https://localhost:8888/user-service*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPlayerLevelUsingPOST**](PlayerLevelResourceApi.md#createPlayerLevelUsingPOST) | **POST** /api/player-levels | createPlayerLevel
[**deletePlayerLevelUsingDELETE**](PlayerLevelResourceApi.md#deletePlayerLevelUsingDELETE) | **DELETE** /api/player-levels/{id} | deletePlayerLevel
[**getAllPlayerLevelsUsingGET**](PlayerLevelResourceApi.md#getAllPlayerLevelsUsingGET) | **GET** /api/player-levels | getAllPlayerLevels
[**getPlayerLevelUsingGET**](PlayerLevelResourceApi.md#getPlayerLevelUsingGET) | **GET** /api/player-levels/{id} | getPlayerLevel
[**updatePlayerLevelUsingPUT**](PlayerLevelResourceApi.md#updatePlayerLevelUsingPUT) | **PUT** /api/player-levels | updatePlayerLevel


<a name="createPlayerLevelUsingPOST"></a>
# **createPlayerLevelUsingPOST**
> PlayerLevel createPlayerLevelUsingPOST(playerLevel)

createPlayerLevel

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.PlayerLevelResourceApi;


PlayerLevelResourceApi apiInstance = new PlayerLevelResourceApi();
PlayerLevel playerLevel = new PlayerLevel(); // PlayerLevel | playerLevel
try {
    PlayerLevel result = apiInstance.createPlayerLevelUsingPOST(playerLevel);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlayerLevelResourceApi#createPlayerLevelUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **playerLevel** | [**PlayerLevel**](PlayerLevel.md)| playerLevel |

### Return type

[**PlayerLevel**](PlayerLevel.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="deletePlayerLevelUsingDELETE"></a>
# **deletePlayerLevelUsingDELETE**
> deletePlayerLevelUsingDELETE(id)

deletePlayerLevel

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.PlayerLevelResourceApi;


PlayerLevelResourceApi apiInstance = new PlayerLevelResourceApi();
Long id = 789L; // Long | id
try {
    apiInstance.deletePlayerLevelUsingDELETE(id);
} catch (ApiException e) {
    System.err.println("Exception when calling PlayerLevelResourceApi#deletePlayerLevelUsingDELETE");
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

<a name="getAllPlayerLevelsUsingGET"></a>
# **getAllPlayerLevelsUsingGET**
> List&lt;PlayerLevel&gt; getAllPlayerLevelsUsingGET()

getAllPlayerLevels

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.PlayerLevelResourceApi;


PlayerLevelResourceApi apiInstance = new PlayerLevelResourceApi();
try {
    List<PlayerLevel> result = apiInstance.getAllPlayerLevelsUsingGET();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlayerLevelResourceApi#getAllPlayerLevelsUsingGET");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;PlayerLevel&gt;**](PlayerLevel.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getPlayerLevelUsingGET"></a>
# **getPlayerLevelUsingGET**
> PlayerLevel getPlayerLevelUsingGET(id)

getPlayerLevel

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.PlayerLevelResourceApi;


PlayerLevelResourceApi apiInstance = new PlayerLevelResourceApi();
Long id = 789L; // Long | id
try {
    PlayerLevel result = apiInstance.getPlayerLevelUsingGET(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlayerLevelResourceApi#getPlayerLevelUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| id |

### Return type

[**PlayerLevel**](PlayerLevel.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="updatePlayerLevelUsingPUT"></a>
# **updatePlayerLevelUsingPUT**
> PlayerLevel updatePlayerLevelUsingPUT(playerLevel)

updatePlayerLevel

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.PlayerLevelResourceApi;


PlayerLevelResourceApi apiInstance = new PlayerLevelResourceApi();
PlayerLevel playerLevel = new PlayerLevel(); // PlayerLevel | playerLevel
try {
    PlayerLevel result = apiInstance.updatePlayerLevelUsingPUT(playerLevel);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlayerLevelResourceApi#updatePlayerLevelUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **playerLevel** | [**PlayerLevel**](PlayerLevel.md)| playerLevel |

### Return type

[**PlayerLevel**](PlayerLevel.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

