package com.athisii.ums.model;

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
public class Module extends AuditModel {
    String name;
}

