package com.athisii.authentication.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author athisii
 * @version 1.0
 * @since 12/10/21
 */

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessDto {
    String access_token;
    String refresh_token;
}
