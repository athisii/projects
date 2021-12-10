package com.athisii.authentication.model;

import com.athisii.base.model.AuditModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author athisii
 * @version 1.0
 * @since 07/10/21
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User extends AuditModel {
    String firstName;
    String lastName;

    Character gender;

    @Column(unique = true)
    String userId;

    @JsonIgnore
    String password;

    @Column(unique = false)
    String email;
    String profileImageUrl;
}
