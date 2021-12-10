package com.athisii.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author athisii
 * @version 1.0
 * @since 13/10/21
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {
    private Boolean status = true;
    private String message;
    private T data;

    public ResponseDTO(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
