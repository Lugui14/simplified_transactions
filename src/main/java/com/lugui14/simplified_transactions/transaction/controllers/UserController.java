package com.lugui14.simplified_transactions.transaction.controllers;

import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.dtos.AddBalanceDto;
import com.lugui14.simplified_transactions.transaction.domain.dtos.UserPresenterDto;
import com.lugui14.simplified_transactions.transaction.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "User management operations")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", description = "This endpoint retrieves a user by its id")
    public ResponseEntity<UserPresenterDto> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(new UserPresenterDto(userService.findById(id)));
    }

    @PostMapping
    @Operation(summary = "Create user", description = "This endpoint creates a new user")
    public ResponseEntity<UserPresenterDto> createUser(@RequestBody @Valid User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserPresenterDto(this.userService.create(user)));
    }

    @PostMapping("/add-balance")
    @Operation(summary = "Add balance to user", description = "This endpoint adds a balance to a user (just for experimental purposes)")
    public ResponseEntity<UserPresenterDto> addBalance(@RequestBody AddBalanceDto addBalanceDto) {
        return ResponseEntity.ok(new UserPresenterDto(this.userService.rechargeValue(addBalanceDto.userId(), addBalanceDto.value())));
    }
}
