package com.athisii.authentication.model;

import com.athisii.base.model.AuditModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;

/**
 * @author athisii
 * @version 1.0
 * @since 09/10/21
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleMapping extends AuditModel {
    Long userId;
    Long roleId;
}
