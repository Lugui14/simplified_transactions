package com.lugui14.simplified_transactions.transaction.controllers;

import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.dtos.AddBalanceDto;
import com.lugui14.simplified_transactions.transaction.domain.dtos.UserPresenterDto;
import com.lugui14.simplified_transactions.transaction.services.UserService;
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
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPresenterDto> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(new UserPresenterDto(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserPresenterDto> createUser(@RequestBody @Valid User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserPresenterDto(this.userService.create(user)));
    }

    @PostMapping("/add-balance")
    public ResponseEntity<UserPresenterDto> addBalance(@RequestBody AddBalanceDto addBalanceDto) {
        return ResponseEntity.ok(new UserPresenterDto(this.userService.rechargeValue(addBalanceDto.userId(), addBalanceDto.value())));
    }
}
