package com.athisii.ums.controller;

import com.athisii.authorization.model.IdentityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author athisii
 * @version 1.0
 * @since 13/10/21
 */

@Slf4j
@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private IdentityContext identityContext;

    @GetMapping
    public String test() {
        log.info("Id: " + identityContext.getId());
        log.info("userId: " + identityContext.getUserId());
        log.info("Name: " + identityContext.getFullName());
        return "Hello, " + identityContext.getFullName() + "!";
    }
}
