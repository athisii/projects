package com.athisii.authorization.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author athisii
 * @version 1.0
 * @since 13/10/21
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseDTO<T> {
    Boolean status = false;
    String message;
    T data;

    public ResponseDTO(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
