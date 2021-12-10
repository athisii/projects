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
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission extends AuditModel {
    Long actionId;
    Long moduleId;
}
