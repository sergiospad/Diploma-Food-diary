package org.kane.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.auth.security.TokenService;
import org.kane.domain.DTO.entityDTO.user.ChangeRoleDTO;
import org.kane.domain.DTO.entityDTO.user.UserEditDTO;
import org.kane.domain.DTO.entityDTO.user.UserProfileDTO;
import org.kane.domain.DTO.request.UpdatePasswordRequest;
import org.kane.domain.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserProfileDTO> getUserProfile(Principal principal) {
        return ResponseEntity.ok(userService.getUserProfile(principal));
    }

    @PatchMapping("/update")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Object> updateUser(Principal principal, @RequestBody UserEditDTO userEditDTO){
        if(userService.updateUser(userEditDTO)) {
            var jwt = tokenService.updateToken(principal);
            return ResponseEntity.ok().body(jwt);
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/password")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Object> updatePassword(Principal principal,  @RequestBody UpdatePasswordRequest updatePasswordRequest){
        if(userService.updatePassword(principal, updatePasswordRequest)) {
            var jwt = tokenService.updateToken(principal);
            return ResponseEntity.ok().body(jwt);
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> changeRole(@RequestBody ChangeRoleDTO changeRoleDTO){
        try{
            userService.changeRole(changeRoleDTO);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/all")
    public ResponseEntity<UserEditDTO> getUserEditDTO(Principal principal){
        return ResponseEntity.ok(userService.getEditIndo(principal));
    }

}
