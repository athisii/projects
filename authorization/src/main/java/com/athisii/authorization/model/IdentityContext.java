package com.athisii.authorization.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

/**
 * @author athisii
 * @version 1.0
 * @since 13/10/21
 */

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@RequestScope
public class IdentityContext {
    Long id;
    String userId;
    String fullName;
    Boolean availableStatus = false;
    List<Long> permissions;
}
