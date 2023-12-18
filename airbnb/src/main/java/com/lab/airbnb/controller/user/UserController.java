package com.lab.airbnb.controller.user;

import com.lab.airbnb.domain.EasyPasswordResetBody;
import com.lab.airbnb.domain.PasswordResetBody;
import com.lab.airbnb.exception.PasswordResetTokenInvalidException;
import com.lab.airbnb.model.House;
import com.lab.airbnb.model.User;
import com.lab.airbnb.model.dao.HouseDAO;
import com.lab.airbnb.service.HouseService;
import com.lab.airbnb.service.JWTService;
import com.lab.airbnb.service.PermissionService;
import com.lab.airbnb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private HouseDAO houseDAO;

    private HouseService houseService;

    private UserService userService;

    private JWTService jwtService;

    private PermissionService permissionService;

    public UserController(HouseDAO houseDAO, HouseService houseService, UserService userService, JWTService jwtService, PermissionService permissionService) {
        this.houseDAO = houseDAO;
        this.houseService = houseService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.permissionService = permissionService;
    }

    @GetMapping("/{userId}/houses/")
    public ResponseEntity<List<House>> getUserHouses(
            @AuthenticationPrincipal User user, @PathVariable String userId) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<House> houses = houseDAO.findByUser_UserId(userId);
        return ResponseEntity.ok(houses);
    }

    @PutMapping("/{userId}/house")
    public ResponseEntity<House> addHouse(@AuthenticationPrincipal User user,
                                          @PathVariable String userId,
                                          @RequestBody House house) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        house.setOwner(userId);
        User refUser = new User();
        refUser.setUserId(userId);
        house.setUser(refUser);
        return ResponseEntity.ok(houseDAO.save(house));
    }

    //duplicated with houseController..
    @PatchMapping("/{userId}/houses/{houseId}")
    public ResponseEntity<House> updateHouse(@AuthenticationPrincipal User user,
                                             @PathVariable String userId,
                                             @PathVariable String houseId,
                                             @RequestBody House house) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (house.getHouseId().equals(houseId)) {

            Optional<House> opHouse = houseService.saveOrMode(house);
            return opHouse.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    //bypass the email verification
    @PostMapping("/{userId}/password")
    public ResponseEntity<User> changePassword(@AuthenticationPrincipal User user,
                                               @PathVariable("userId") String userId,
                                               @Valid @RequestBody EasyPasswordResetBody password) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User curUser = userService.findByUserId(userId);
        if (curUser == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String token = jwtService.generatePasswordResetToken(curUser);

        PasswordResetBody body = new PasswordResetBody();
        body.setPassword(password.getPassword());
        body.setToken(token);
        try {
            userService.resetPassword(body);
        } catch (PasswordResetTokenInvalidException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

    //no redis 无为而治
    @GetMapping("/logout")
    public ResponseEntity logout(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok().build();
    }

    //upgrade
    @PatchMapping("/{userId}/landlord")
    public ResponseEntity<User> upgradeToLandlord(@AuthenticationPrincipal User user,
                                                  @PathVariable("userId") String userId) {
        if (permissionService.userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User curUser = userService.findByUserId(userId);
        if (curUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        curUser.setRole("1");
        User ans=userService.save(curUser);
        if (ans==null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

    //TODO: remain this temporarily
    private boolean userHasPermission(User user, String userId) {
        // ||user.getRoles().contains(Role.ADMIN)
        return user.getUserId().equals(userId);
    }


}
