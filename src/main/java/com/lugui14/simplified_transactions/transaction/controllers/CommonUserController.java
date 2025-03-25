package com.lugui14.simplified_transactions.transaction.controllers;

import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.dtos.CommonUserPresenterDto;
import com.lugui14.simplified_transactions.transaction.services.CommonUserService;
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
@RequestMapping("/common-user")
public class CommonUserController {

    private final CommonUserService commonUserService;

    public CommonUserController(CommonUserService commonUserService) {
        this.commonUserService = commonUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonUserPresenterDto> getCommonUser(@PathVariable Integer id) {
        return ResponseEntity.ok(this.commonUserService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CommonUserPresenterDto> createCommonUser(@RequestBody @Valid User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.commonUserService.create(user));
    };
}
