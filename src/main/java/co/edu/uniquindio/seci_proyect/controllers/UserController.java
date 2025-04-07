package co.edu.uniquindio.seci_proyect.controllers;


import co.edu.uniquindio.seci_proyect.dtos.user.UserRegistrationRequest;
import co.edu.uniquindio.seci_proyect.dtos.user.UserResponse;
import co.edu.uniquindio.seci_proyect.dtos.user.UserSearchRequest;
import co.edu.uniquindio.seci_proyect.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRegistrationRequest request) {

        var response = userService.createUser(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String id) {
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }
    @GetMapping()
    public Page<UserResponse> searchUsers(UserSearchRequest request) {
        return userService.searchUsers(request);
    }



}
