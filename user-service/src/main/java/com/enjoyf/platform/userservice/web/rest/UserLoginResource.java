package com.enjoyf.platform.userservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.autoconfigure.web.error.CustomParameterizedException;
import com.enjoyf.platform.userservice.domain.UserLogin;
import com.enjoyf.platform.userservice.repository.UserLoginRepository;
import com.enjoyf.platform.userservice.service.util.UserSeviceUtil;
import com.enjoyf.platform.userservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.userservice.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserLogin.
 */
@RestController
@RequestMapping("/api")
@Api(value = "登陆信息")
public class UserLoginResource {

    private final Logger log = LoggerFactory.getLogger(UserLoginResource.class);

    private static final String ENTITY_NAME = "userLogin";

    private final UserLoginRepository userLoginRepository;

    public UserLoginResource(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

//    /**
//     * POST  /user-logins : Create a new userLogin.
//     *
//     * @param userLogin the userLogin to create
//     * @return the ResponseEntity with status 201 (Created) and with body the new userLogin, or with status 400 (Bad Request) if the userLogin has already an ID
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PostMapping("/user-logins")
//    @Timed
//    public ResponseEntity<UserLogin> createUserLogin(@Valid @RequestBody UserLogin userLogin) throws URISyntaxException {
//        log.debug("REST request to save UserLogin : {}", userLogin);
//        if (userLogin.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userLogin cannot already have an ID")).body(null);
//        }
//        UserLogin result = userLoginRepository.save(userLogin);
//        return ResponseEntity.created(new URI("/api/user-logins/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }

    /**
     * PUT  /user-logins : Updates an existing userLogin.
     *
     * @param userLogin the userLogin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userLogin,
     * or with status 400 (Bad Request) if the userLogin is not valid,
     * or with status 500 (Internal Server Error) if the userLogin couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-logins")
    @Timed
    public ResponseEntity<UserLogin> updateUserLogin(@Valid @RequestBody UserLogin userLogin) throws URISyntaxException {
        log.debug("REST request to update UserLogin : {}", userLogin);
        if (userLogin.getId() == null) {
            throw new CustomParameterizedException("Id is null",userLogin.toString());
        }
        UserLogin existLogin = userLoginRepository.getOne(userLogin.getId());
        if(existLogin==null)
            throw new CustomParameterizedException("登陆信息不存在",userLogin.toString());
        if(!StringUtils.isEmpty(userLogin.getLogin()))
            existLogin.setLogin(userLogin.getLogin());
        if(!StringUtils.isEmpty(userLogin.getPassword())){
            String pwTimd = System.currentTimeMillis()+"";
            existLogin.setPassword(UserSeviceUtil.hashPassword(userLogin.getPassword(),pwTimd));
            existLogin.setPasswordTime(pwTimd);
        }
        existLogin = userLoginRepository.save(userLogin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userLogin.getId().toString()))
            .body(existLogin);
    }


    /**
     * GET  /user-logins : get all the userLogins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userLogins in body
     */
    @GetMapping("/user-logins")
    @Timed
    public ResponseEntity<List<UserLogin>> getAllUserLogins(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UserLogins");
        Page<UserLogin> page = userLoginRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-logins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-logins/:id : get the "id" userLogin.
     *
     * @param id the id of the userLogin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userLogin, or with status 404 (Not Found)
     */
    @GetMapping("/user-logins/{id}")
    @Timed
    public ResponseEntity<UserLogin> getUserLogin(@PathVariable Long id) {
        log.debug("REST request to get UserLogin : {}", id);
        UserLogin userLogin = userLoginRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userLogin));
    }

    /**
     * GET  /logins/by : get the "login" userLogin.
     *
     * @param login the login of the userLogin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userLogin, or with status 404 (Not Found)
     */
    @GetMapping("/user-logins/by")
    @Timed
    public ResponseEntity<UserLogin> getUserLoginByLoginAndLoginDomain(@RequestParam String login,@RequestParam String loginDomain) {
        log.debug("REST request to get UserLogin by: {}-{}", login,loginDomain);
        return userLoginRepository.findOneByLoginAndLoginDomain(login,loginDomain).map(userLogin ->  ResponseEntity.ok(userLogin))
            .orElse(ResponseEntity.ok(null));
    }



//    /**
//     * DELETE  /user-logins/:id : delete the "id" userLogin.
//     *
//     * @param id the id of the userLogin to delete
//     * @return the ResponseEntity with status 200 (OK)
//     */
//    @DeleteMapping("/user-logins/{id}")
//    @Timed
//    public ResponseEntity<Void> deleteUserLogin(@PathVariable Long id) {
//        log.debug("REST request to delete UserLogin : {}", id);
//        userLoginRepository.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }

}
