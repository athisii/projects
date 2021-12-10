package com.athisii.ums.repository;

import com.athisii.ums.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author athisii
 * @version 1.0
 * @since 08/10/21
 */

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserId(String userId);
}
