package com.enjoyf.platform.userservice.security;

import com.enjoyf.platform.userservice.domain.UserLogin;
import com.enjoyf.platform.userservice.repository.UserLoginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by shuguangcao on 2017/3/23.
 */
@Component
public class DomainUserDetailService implements UserDetailsService{
    private final Logger log = LoggerFactory.getLogger(DomainUserDetailService.class);

    private final UserLoginRepository userLoginRepository;

    public DomainUserDetailService(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        if(log.isDebugEnabled())
            log.debug("Login Authenticating {}", login);
        String[] loginParams = login.split(",");
        Optional<UserLogin> userLogin  = userLoginRepository.findOneByLoginAndLoginDomain(loginParams[0],loginParams[1]);

        return userLogin.map(user -> {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(AuthoritiesConstants.USER);
            grantedAuthorities.add(grantedAuthority);
            return new org.springframework.security.core.userdetails.User(loginParams[0],
                user.getPassword()+","+user.getPasswordTime(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + login + " was not found in the " +
            "database"));
    }
}
