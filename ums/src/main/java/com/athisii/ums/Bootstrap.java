package com.athisii.ums;

import com.athisii.ums.model.Module;
import com.athisii.ums.model.*;
import com.athisii.ums.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author athisii
 * @version 1.0
 * @since 12/10/21
 */

@Component
public class Bootstrap implements ApplicationRunner {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRoleMappingRepo userRoleMappingRepo;
    @Autowired
    private PermissionRepo permissionRepo;
    @Autowired
    private ActionRepo actionRepo;
    @Autowired
    private ModuleRepo moduleRepo;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) {
        User user = new User();
        user.setUserId("athisii");
        user.setFirstName("athisii");
        user.setLastName("ekhe");
        user.setGender('M');
        user.setPassword(passwordEncoder.encode("password"));
        user.setEmail("email@athisii.com");
        userRepo.save(user);

        List<Action> actions = List.of(
                new Action("CREATE"),
                new Action("CREATE_ALL"),
                new Action("UPDATE"),
                new Action("UPDATE_ALL"),
                new Action("DELETE"),
                new Action("DELETE_ALL"),
                new Action("VIEW"),
                new Action("VIEW_ALL")
        );
        actionRepo.saveAll(actions);

        List<Module> modules = List.of(
                new Module("USER_MANAGEMENT"),
                new Module("CONTENT_MANAGEMENT")
        );
        moduleRepo.saveAll(modules);

        List<Permission> permissions = List.of(
                new Permission(1L, 1L),
                new Permission(2L, 1L),
                new Permission(3L, 1L),
                new Permission(4L, 1L),
                new Permission(5L, 1L),
                new Permission(6L, 1L)
        );


        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        adminRole.setDescription("All permissions");
        adminRole.setPermissions(permissions);

        Role userRole = new Role();
        userRole.setName("USER");
        userRole.setDescription("User Role");
        userRole.setPermissions(List.of(permissions.get(4), permissions.get(5)));


        roleRepo.saveAll(List.of(adminRole, userRole));

        permissionRepo.saveAll(permissions);

        userRoleMappingRepo.save(new UserRoleMapping(user.getId(), adminRole.getId()));

    }
}
