package com.athisii.authentication.repository;

import com.athisii.authentication.model.UserRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author athisii
 * @version 1.0
 * @since 09/10/21
 */

@Repository
public interface UserRoleMappingRepo extends JpaRepository<UserRoleMapping, Long> {
    UserRoleMapping findByUserId(Long userId);
}
