package com.athisii.authentication.service;

import com.athisii.authentication.model.AppUser;
import com.athisii.authentication.model.Role;
import com.athisii.authentication.model.User;
import com.athisii.authentication.model.UserRoleMapping;
import com.athisii.authentication.repository.RoleRepo;
import com.athisii.authentication.repository.UserRepo;
import com.athisii.authentication.repository.UserRoleMappingRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author athisii
 * @version 1.0
 * @since 12/10/21
 */

@Service
@Slf4j
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRoleMappingRepo userRoleMappingRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername()");

        User user = userRepo.findByUserId(username);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new AppUser(user, getGrantedAuthorities(user.getId()));
    }

    private List<GrantedAuthority> getGrantedAuthorities(Long userId) throws UsernameNotFoundException {
        log.info("Inside getGrantedAuthorities()");

        UserRoleMapping userRoleMapping = userRoleMappingRepo.findByUserId(userId);
        if (userRoleMapping == null) {
            log.error("UserRoleMapping not found");
            throw new UsernameNotFoundException("UserRoleMapping not found");
        }

        Optional<Role> roleOptional = roleRepo.findById(userRoleMapping.getRoleId());
        if (roleOptional.isEmpty()) {
            log.error("Role not found");
            throw new UsernameNotFoundException("Role not found");
        }
        Role role = roleOptional.get();

        return role.getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getId() + ""))
                .collect(Collectors.toList());
    }
}
