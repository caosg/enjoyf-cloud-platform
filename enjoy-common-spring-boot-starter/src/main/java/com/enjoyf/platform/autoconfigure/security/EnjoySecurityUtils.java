package com.enjoyf.platform.autoconfigure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * Enjoy Utility class for Spring Security.
 */
public final class EnjoySecurityUtils {

    public static final String PROFILE_NO = "profileNo";
    public static final String LOGIN_DOMAIN = "loginDomain";
    public static final String UID = "uid";
    public static final String JWT_TOKEN = "jwt_token";

    private EnjoySecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    /**
     * Get the profileId of the current user.
     *
     * @return the profileId of the current user
     */
    public static String getCurrentProfileNo() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String profileNo = null;
        if (authentication != null && authentication.getCredentials() instanceof Map)
            profileNo = (String) ((Map) authentication.getCredentials()).get(PROFILE_NO);
        return profileNo;
    }

    /**
     * Get the uid of the current user.
     *
     * @return the profileId of the current user
     */
    public static Long getCurrentUid() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Long uid = null;
        if (authentication != null && authentication.getCredentials() instanceof Map)
            uid = Long.valueOf((String) ((Map) authentication.getCredentials()).get(UID));
        return uid;
    }

    /**
     * get the userName
     *
     * @return
     */
    public static String getCurrentUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = "";
        if (authentication != null && authentication.getPrincipal() != null) {
            User user = (User) authentication.getPrincipal();
            userName = user.getUsername();
        }
        return userName;
    }

    /**
     * Get the loginDomain of the current login user.
     *
     * @return the profileId of the current user
     */
    public static String getCurrentLoginDomain() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof Map)
            return (String) ((Map) authentication.getCredentials()).get(LOGIN_DOMAIN);
        return null;
    }

    /**
     * Get the JWT token of the current user.
     *
     * @return the JWT of the current user
     */
    public static String getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof Map) {
            return (String) ((Map) authentication.getCredentials()).get(JWT_TOKEN);
        }
        return null;
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        return false;
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * <p>The name of this method comes from the isUserInRole() method in the Servlet API</p>
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
        }
        return false;
    }

}
