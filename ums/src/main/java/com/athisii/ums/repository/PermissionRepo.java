package com.athisii.ums.repository;

import com.athisii.ums.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author athisii
 * @version 1.0
 * @since 13/10/21
 */

public interface PermissionRepo extends JpaRepository<Permission, Long> {
}
