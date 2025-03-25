package com.lugui14.simplified_transactions.transaction.controllers;

import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.dtos.ShopkeeperUserPresenterDto;
import com.lugui14.simplified_transactions.transaction.services.ShopkeeperUserService;
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
@RequestMapping("/shopkeeper-user")
public class ShopkeeperUserController {

    private final ShopkeeperUserService shopkeeperUserService;

    public ShopkeeperUserController(ShopkeeperUserService shopkeeperUserService) {
        this.shopkeeperUserService = shopkeeperUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopkeeperUserPresenterDto> getShopkeeperUser(@PathVariable Integer id) {
        return ResponseEntity.ok(this.shopkeeperUserService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ShopkeeperUserPresenterDto> createShopkeeperUser(@RequestBody @Valid User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.shopkeeperUserService.create(user));
    };
}
