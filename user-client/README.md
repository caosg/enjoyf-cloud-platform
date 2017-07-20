# user-java-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>com.enjoyf.platform</groupId>
    <artifactId>user-java-client</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.enjoyf.platform:user-java-client:1.0.0-SNAPSHOT"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/user-java-client-1.0.0-SNAPSHOT.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import com.enjoyf.platform.userservice.client.*;
import com.enjoyf.platform.userservice.client.auth.*;
import com.enjoyf.platform.userservice.client.model.*;
import com.enjoyf.platform.userservice.client.api.PlayerLevelResourceApi;

import java.io.File;
import java.util.*;

public class PlayerLevelResourceApiExample {

    public static void main(String[] args) {
        
        PlayerLevelResourceApi apiInstance = new PlayerLevelResourceApi();
        PlayerLevel playerLevel = new PlayerLevel(); // PlayerLevel | playerLevel
        try {
            PlayerLevel result = apiInstance.createPlayerLevelUsingPOST(playerLevel);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling PlayerLevelResourceApi#createPlayerLevelUsingPOST");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://localhost:8080/user-service*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*PlayerLevelResourceApi* | [**createPlayerLevelUsingPOST**](docs/PlayerLevelResourceApi.md#createPlayerLevelUsingPOST) | **POST** /api/player-levels | createPlayerLevel
*PlayerLevelResourceApi* | [**deletePlayerLevelUsingDELETE**](docs/PlayerLevelResourceApi.md#deletePlayerLevelUsingDELETE) | **DELETE** /api/player-levels/{id} | deletePlayerLevel
*PlayerLevelResourceApi* | [**getAllPlayerLevelsUsingGET**](docs/PlayerLevelResourceApi.md#getAllPlayerLevelsUsingGET) | **GET** /api/player-levels | getAllPlayerLevels
*PlayerLevelResourceApi* | [**getPlayerLevelUsingGET**](docs/PlayerLevelResourceApi.md#getPlayerLevelUsingGET) | **GET** /api/player-levels/{id} | getPlayerLevel
*PlayerLevelResourceApi* | [**updatePlayerLevelUsingPUT**](docs/PlayerLevelResourceApi.md#updatePlayerLevelUsingPUT) | **PUT** /api/player-levels | updatePlayerLevel
*PlayerResourceApi* | [**createPlayerUsingPOST**](docs/PlayerResourceApi.md#createPlayerUsingPOST) | **POST** /api/players | createPlayer
*PlayerResourceApi* | [**deletePlayerUsingDELETE**](docs/PlayerResourceApi.md#deletePlayerUsingDELETE) | **DELETE** /api/players/{id} | deletePlayer
*PlayerResourceApi* | [**getAllPlayersByProfileNosUsingGET**](docs/PlayerResourceApi.md#getAllPlayersByProfileNosUsingGET) | **GET** /api/players/profiles | getAllPlayersByProfileNos
*PlayerResourceApi* | [**getAllPlayersUsingGET**](docs/PlayerResourceApi.md#getAllPlayersUsingGET) | **GET** /api/players | getAllPlayers
*PlayerResourceApi* | [**getPlayerByProfileNoUsingGET**](docs/PlayerResourceApi.md#getPlayerByProfileNoUsingGET) | **GET** /api/players/profiles/{profileNo} | getPlayerByProfileNo
*PlayerResourceApi* | [**getPlayerUsingGET**](docs/PlayerResourceApi.md#getPlayerUsingGET) | **GET** /api/players/{id} | getPlayer
*PlayerResourceApi* | [**updatePlayerUsingPUT**](docs/PlayerResourceApi.md#updatePlayerUsingPUT) | **PUT** /api/players | updatePlayer
*ProfileInfoResourceApi* | [**getActiveProfilesUsingGET**](docs/ProfileInfoResourceApi.md#getActiveProfilesUsingGET) | **GET** /api/profile-info | getActiveProfiles
*ProfileSummaryResourceApi* | [**createProfileSummaryUsingPOST**](docs/ProfileSummaryResourceApi.md#createProfileSummaryUsingPOST) | **POST** /api/profile-summaries | createProfileSummary
*ProfileSummaryResourceApi* | [**deleteProfileSummaryUsingDELETE**](docs/ProfileSummaryResourceApi.md#deleteProfileSummaryUsingDELETE) | **DELETE** /api/profile-summaries/{id} | deleteProfileSummary
*ProfileSummaryResourceApi* | [**getAllProfileSummariesUsingGET**](docs/ProfileSummaryResourceApi.md#getAllProfileSummariesUsingGET) | **GET** /api/profile-summaries | getAllProfileSummaries
*ProfileSummaryResourceApi* | [**getProfileSummaryUsingGET**](docs/ProfileSummaryResourceApi.md#getProfileSummaryUsingGET) | **GET** /api/profile-summaries/{id} | getProfileSummary
*ProfileSummaryResourceApi* | [**updateProfileSummaryUsingPUT**](docs/ProfileSummaryResourceApi.md#updateProfileSummaryUsingPUT) | **PUT** /api/profile-summaries | updateProfileSummary
*UserAccountResourceApi* | [**bindUsingPUT**](docs/UserAccountResourceApi.md#bindUsingPUT) | **PUT** /api/accounts/bind | bind
*UserAccountResourceApi* | [**changePasswordUsingPOST**](docs/UserAccountResourceApi.md#changePasswordUsingPOST) | **POST** /api/accounts/change_password | changePassword
*UserAccountResourceApi* | [**createUserAccountUsingPOST**](docs/UserAccountResourceApi.md#createUserAccountUsingPOST) | **POST** /api/accounts | createUserAccount
*UserAccountResourceApi* | [**deleteUserAccountUsingDELETE**](docs/UserAccountResourceApi.md#deleteUserAccountUsingDELETE) | **DELETE** /api/accounts/{id} | deleteUserAccount
*UserAccountResourceApi* | [**getAccountByAccountNoKeyUsingGET**](docs/UserAccountResourceApi.md#getAccountByAccountNoKeyUsingGET) | **GET** /api/accounts/by | getAccountByAccountNoKey
*UserAccountResourceApi* | [**getAccountByProfileIdUsingGET**](docs/UserAccountResourceApi.md#getAccountByProfileIdUsingGET) | **GET** /api/accounts/profile-id/{profileId} | getAccountByProfileId
*UserAccountResourceApi* | [**getAccountByProfileNoUsingGET**](docs/UserAccountResourceApi.md#getAccountByProfileNoUsingGET) | **GET** /api/accounts/profile-no/{profileNo} | getAccountByProfileNo
*UserAccountResourceApi* | [**getAllUserAccountsUsingGET**](docs/UserAccountResourceApi.md#getAllUserAccountsUsingGET) | **GET** /api/accounts | getAllUserAccounts
*UserAccountResourceApi* | [**getCurrentAccountUsingGET**](docs/UserAccountResourceApi.md#getCurrentAccountUsingGET) | **GET** /api/accounts/current | getCurrentAccount
*UserAccountResourceApi* | [**getUserAccountUsingGET**](docs/UserAccountResourceApi.md#getUserAccountUsingGET) | **GET** /api/accounts/{id} | getUserAccount
*UserAccountResourceApi* | [**getUserLoginsByAccountNoAndLogindomainsUsingGET**](docs/UserAccountResourceApi.md#getUserLoginsByAccountNoAndLogindomainsUsingGET) | **GET** /api/accounts/logins/{accountNo} | getUserLoginsByAccountNoAndLogindomains
*UserAccountResourceApi* | [**loginUsingPOST**](docs/UserAccountResourceApi.md#loginUsingPOST) | **POST** /api/login | login
*UserAccountResourceApi* | [**registerUsingPOST**](docs/UserAccountResourceApi.md#registerUsingPOST) | **POST** /api/register | register
*UserAccountResourceApi* | [**socialLoginUsingPOST**](docs/UserAccountResourceApi.md#socialLoginUsingPOST) | **POST** /api/accounts/auth | socialLogin
*UserAccountResourceApi* | [**unBindUsingPUT**](docs/UserAccountResourceApi.md#unBindUsingPUT) | **PUT** /api/accounts/unbind | unBind
*UserAccountResourceApi* | [**updateUserAccountUsingPUT**](docs/UserAccountResourceApi.md#updateUserAccountUsingPUT) | **PUT** /api/accounts | updateUserAccount
*UserLoginResourceApi* | [**createUserLoginUsingPOST**](docs/UserLoginResourceApi.md#createUserLoginUsingPOST) | **POST** /api/user-logins | createUserLogin
*UserLoginResourceApi* | [**deleteUserLoginUsingDELETE**](docs/UserLoginResourceApi.md#deleteUserLoginUsingDELETE) | **DELETE** /api/user-logins/{id} | deleteUserLogin
*UserLoginResourceApi* | [**getAllUserLoginsUsingGET**](docs/UserLoginResourceApi.md#getAllUserLoginsUsingGET) | **GET** /api/user-logins | getAllUserLogins
*UserLoginResourceApi* | [**getUserLoginByLoginUsingGET**](docs/UserLoginResourceApi.md#getUserLoginByLoginUsingGET) | **GET** /api/logins/{login} | getUserLoginByLogin
*UserLoginResourceApi* | [**getUserLoginUsingGET**](docs/UserLoginResourceApi.md#getUserLoginUsingGET) | **GET** /api/user-logins/{id} | getUserLogin
*UserLoginResourceApi* | [**updateUserLoginUsingPUT**](docs/UserLoginResourceApi.md#updateUserLoginUsingPUT) | **PUT** /api/user-logins | updateUserLogin
*UserMobileResourceApi* | [**bindCodeUsingPOST**](docs/UserMobileResourceApi.md#bindCodeUsingPOST) | **POST** /api/user-mobiles/{mobile}/bind | bindCode
*UserMobileResourceApi* | [**mobileExistsUsingGET**](docs/UserMobileResourceApi.md#mobileExistsUsingGET) | **GET** /api/user-mobiles/{mobile}/exists | mobileExists
*UserMobileResourceApi* | [**sendBindCodeUsingPUT**](docs/UserMobileResourceApi.md#sendBindCodeUsingPUT) | **PUT** /api/user-mobiles/{mobile}/code | sendBindCode
*UserMobileResourceApi* | [**sendRegisterCodeUsingPUT**](docs/UserMobileResourceApi.md#sendRegisterCodeUsingPUT) | **PUT** /api/user-mobiles/{mobile}/register/code | sendRegisterCode
*UserMobileResourceApi* | [**unbindUsingPOST**](docs/UserMobileResourceApi.md#unbindUsingPOST) | **POST** /api/user-mobiles/{mobile}/unbind | unbind
*UserMobileResourceApi* | [**verifyBindCodeUsingGET**](docs/UserMobileResourceApi.md#verifyBindCodeUsingGET) | **GET** /api/user-mobiles/{mobile}/register/verfiy | verifyBindCode
*UserProfileResourceApi* | [**createUserProfileUsingPOST**](docs/UserProfileResourceApi.md#createUserProfileUsingPOST) | **POST** /api/user-profiles | createUserProfile
*UserProfileResourceApi* | [**deleteUserProfileUsingDELETE**](docs/UserProfileResourceApi.md#deleteUserProfileUsingDELETE) | **DELETE** /api/user-profiles/{id} | deleteUserProfile
*UserProfileResourceApi* | [**getAllUserProfilesUsingGET**](docs/UserProfileResourceApi.md#getAllUserProfilesUsingGET) | **GET** /api/user-profiles | getAllUserProfiles
*UserProfileResourceApi* | [**getProfileByLikeNickUsingGET**](docs/UserProfileResourceApi.md#getProfileByLikeNickUsingGET) | **GET** /api/user-profiles/by | getProfileByLikeNick
*UserProfileResourceApi* | [**getProfilesByIdsUsingGET**](docs/UserProfileResourceApi.md#getProfilesByIdsUsingGET) | **GET** /api/user-profiles/ids | getProfilesByIds
*UserProfileResourceApi* | [**getProfilesByProfileNosUsingGET**](docs/UserProfileResourceApi.md#getProfilesByProfileNosUsingGET) | **GET** /api/user-profiles/profileno | getProfilesByProfileNos
*UserProfileResourceApi* | [**getUserProfileByProfileNoUsingGET**](docs/UserProfileResourceApi.md#getUserProfileByProfileNoUsingGET) | **GET** /api/user-profiles/profileno/{profileNo} | getUserProfileByProfileNo
*UserProfileResourceApi* | [**getUserProfileUsingGET**](docs/UserProfileResourceApi.md#getUserProfileUsingGET) | **GET** /api/user-profiles/{id} | getUserProfile
*UserProfileResourceApi* | [**updateUserProfileUsingPUT**](docs/UserProfileResourceApi.md#updateUserProfileUsingPUT) | **PUT** /api/user-profiles | updateUserProfile
*UserSettingsResourceApi* | [**createUserSettingsUsingPOST**](docs/UserSettingsResourceApi.md#createUserSettingsUsingPOST) | **POST** /api/user-settings | createUserSettings
*UserSettingsResourceApi* | [**deleteUserSettingsUsingDELETE**](docs/UserSettingsResourceApi.md#deleteUserSettingsUsingDELETE) | **DELETE** /api/user-settings/{id} | deleteUserSettings
*UserSettingsResourceApi* | [**getAllUserSettingsUsingGET**](docs/UserSettingsResourceApi.md#getAllUserSettingsUsingGET) | **GET** /api/user-settings | getAllUserSettings
*UserSettingsResourceApi* | [**getUserSettingsByProfileNoUsingGET**](docs/UserSettingsResourceApi.md#getUserSettingsByProfileNoUsingGET) | **GET** /api/user-settings/by | getUserSettingsByProfileNo
*UserSettingsResourceApi* | [**getUserSettingsUsingGET**](docs/UserSettingsResourceApi.md#getUserSettingsUsingGET) | **GET** /api/user-settings/{id} | getUserSettings
*UserSettingsResourceApi* | [**updateUserSettingsUsingPUT**](docs/UserSettingsResourceApi.md#updateUserSettingsUsingPUT) | **PUT** /api/user-settings | updateUserSettings
*UserTokenResourceApi* | [**createUserTokenUsingPOST**](docs/UserTokenResourceApi.md#createUserTokenUsingPOST) | **POST** /api/user-tokens | createUserToken
*UserTokenResourceApi* | [**deleteUserTokenUsingDELETE**](docs/UserTokenResourceApi.md#deleteUserTokenUsingDELETE) | **DELETE** /api/user-tokens/{id} | deleteUserToken
*UserTokenResourceApi* | [**getAllUserTokensUsingGET**](docs/UserTokenResourceApi.md#getAllUserTokensUsingGET) | **GET** /api/user-tokens | getAllUserTokens
*UserTokenResourceApi* | [**getUserTokenUsingGET**](docs/UserTokenResourceApi.md#getUserTokenUsingGET) | **GET** /api/user-tokens/{id} | getUserToken
*UserTokenResourceApi* | [**updateUserTokenUsingPUT**](docs/UserTokenResourceApi.md#updateUserTokenUsingPUT) | **PUT** /api/user-tokens | updateUserToken


## Documentation for Models

 - [AccountDTO](docs/AccountDTO.md)
 - [BindDTO](docs/BindDTO.md)
 - [LoginVM](docs/LoginVM.md)
 - [Player](docs/Player.md)
 - [PlayerLevel](docs/PlayerLevel.md)
 - [ProfileInfoVM](docs/ProfileInfoVM.md)
 - [ProfileSummary](docs/ProfileSummary.md)
 - [RegisterReqDTO](docs/RegisterReqDTO.md)
 - [ResponseEntity](docs/ResponseEntity.md)
 - [UserAccount](docs/UserAccount.md)
 - [UserLogin](docs/UserLogin.md)
 - [UserProfile](docs/UserProfile.md)
 - [UserSettings](docs/UserSettings.md)
 - [UserToken](docs/UserToken.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author



