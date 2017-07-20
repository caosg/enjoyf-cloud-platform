# PlayerResourceApi

All URIs are relative to *https://localhost:8888/user-service*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPlayerUsingPOST**](PlayerResourceApi.md#createPlayerUsingPOST) | **POST** /api/players | createPlayer
[**deletePlayerUsingDELETE**](PlayerResourceApi.md#deletePlayerUsingDELETE) | **DELETE** /api/players/{id} | deletePlayer
[**getAllPlayersByProfileNosUsingGET**](PlayerResourceApi.md#getAllPlayersByProfileNosUsingGET) | **GET** /api/players/profiles | getAllPlayersByProfileNos
[**getAllPlayersUsingGET**](PlayerResourceApi.md#getAllPlayersUsingGET) | **GET** /api/players | getAllPlayers
[**getPlayerByProfileNoUsingGET**](PlayerResourceApi.md#getPlayerByProfileNoUsingGET) | **GET** /api/players/profiles/{profileNo} | getPlayerByProfileNo
[**getPlayerUsingGET**](PlayerResourceApi.md#getPlayerUsingGET) | **GET** /api/players/{id} | getPlayer
[**updatePlayerUsingPUT**](PlayerResourceApi.md#updatePlayerUsingPUT) | **PUT** /api/players | updatePlayer


<a name="createPlayerUsingPOST"></a>
# **createPlayerUsingPOST**
> Player createPlayerUsingPOST(player)

createPlayer

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.PlayerResourceApi;


PlayerResourceApi apiInstance = new PlayerResourceApi();
Player player = new Player(); // Player | player
try {
    Player result = apiInstance.createPlayerUsingPOST(player);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlayerResourceApi#createPlayerUsingPOST");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **player** | [**Player**](Player.md)| player |

### Return type

[**Player**](Player.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="deletePlayerUsingDELETE"></a>
# **deletePlayerUsingDELETE**
> deletePlayerUsingDELETE(id)

deletePlayer

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.PlayerResourceApi;


PlayerResourceApi apiInstance = new PlayerResourceApi();
Long id = 789L; // Long | id
try {
    apiInstance.deletePlayerUsingDELETE(id);
} catch (ApiException e) {
    System.err.println("Exception when calling PlayerResourceApi#deletePlayerUsingDELETE");
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

<a name="getAllPlayersByProfileNosUsingGET"></a>
# **getAllPlayersByProfileNosUsingGET**
> List&lt;Player&gt; getAllPlayersByProfileNosUsingGET(profileNos)

getAllPlayersByProfileNos

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.PlayerResourceApi;


PlayerResourceApi apiInstance = new PlayerResourceApi();
List<String> profileNos = Arrays.asList("profileNos_example"); // List<String> | profileNos
try {
    List<Player> result = apiInstance.getAllPlayersByProfileNosUsingGET(profileNos);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlayerResourceApi#getAllPlayersByProfileNosUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profileNos** | [**List&lt;String&gt;**](String.md)| profileNos |

### Return type

[**List&lt;Player&gt;**](Player.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getAllPlayersUsingGET"></a>
# **getAllPlayersUsingGET**
> List&lt;Player&gt; getAllPlayersUsingGET(page, size, sort)

getAllPlayers

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.PlayerResourceApi;


PlayerResourceApi apiInstance = new PlayerResourceApi();
Integer page = 56; // Integer | Page number of the requested page
Integer size = 56; // Integer | Size of a page
List<String> sort = Arrays.asList("sort_example"); // List<String> | Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
try {
    List<Player> result = apiInstance.getAllPlayersUsingGET(page, size, sort);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlayerResourceApi#getAllPlayersUsingGET");
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

[**List&lt;Player&gt;**](Player.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getPlayerByProfileNoUsingGET"></a>
# **getPlayerByProfileNoUsingGET**
> Player getPlayerByProfileNoUsingGET(profileNo)

getPlayerByProfileNo

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.PlayerResourceApi;


PlayerResourceApi apiInstance = new PlayerResourceApi();
String profileNo = "profileNo_example"; // String | profileNo
try {
    Player result = apiInstance.getPlayerByProfileNoUsingGET(profileNo);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlayerResourceApi#getPlayerByProfileNoUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **profileNo** | **String**| profileNo |

### Return type

[**Player**](Player.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="getPlayerUsingGET"></a>
# **getPlayerUsingGET**
> Player getPlayerUsingGET(id)

getPlayer

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.PlayerResourceApi;


PlayerResourceApi apiInstance = new PlayerResourceApi();
Long id = 789L; // Long | id
try {
    Player result = apiInstance.getPlayerUsingGET(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlayerResourceApi#getPlayerUsingGET");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| id |

### Return type

[**Player**](Player.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="updatePlayerUsingPUT"></a>
# **updatePlayerUsingPUT**
> Player updatePlayerUsingPUT(player)

updatePlayer

### Example
```java
// Import classes:
//import com.enjoyf.platform.userservice.client.ApiException;
//import com.enjoyf.platform.userservice.client.api.PlayerResourceApi;


PlayerResourceApi apiInstance = new PlayerResourceApi();
Player player = new Player(); // Player | player
try {
    Player result = apiInstance.updatePlayerUsingPUT(player);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PlayerResourceApi#updatePlayerUsingPUT");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **player** | [**Player**](Player.md)| player |

### Return type

[**Player**](Player.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

