package com.enjoyf.platform.userservice.repository;

import com.enjoyf.platform.userservice.domain.UserSettings;

import org.springframework.data.jpa.repository.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the UserSettings entity.
 */
@SuppressWarnings("unused")
public interface UserSettingsRepository extends JpaRepository<UserSettings,Long> {

    UserSettings findOneByProfileNo(String profileNo);
}
