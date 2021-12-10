package com.athisii.ums.repository;

import com.athisii.ums.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author athisii
 * @version 1.0
 * @since 09/10/21
 */

@Repository
public interface ActionRepo extends JpaRepository<Action, Long> {
}
