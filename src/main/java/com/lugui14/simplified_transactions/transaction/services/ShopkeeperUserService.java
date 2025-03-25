package com.lugui14.simplified_transactions.transaction.services;

import com.lugui14.simplified_transactions.transaction.domain.ShopkeeperUser;
import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.dtos.ShopkeeperUserPresenterDto;
import com.lugui14.simplified_transactions.transaction.domain.repositories.ShopkeeperRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ShopkeeperUserService {

    private final ShopkeeperRepository shopkeeperUserRepository;

    public ShopkeeperUserService(ShopkeeperRepository shopkeeperUserRepository) {
        this.shopkeeperUserRepository = shopkeeperUserRepository;
    }

    public ShopkeeperUserPresenterDto findById(Integer id) {
        Optional<ShopkeeperUser> optionalShopkeeperUser = shopkeeperUserRepository.findById(id);
        if (optionalShopkeeperUser.isPresent()) {
            return new ShopkeeperUserPresenterDto(optionalShopkeeperUser.get());
        }

        throw new RuntimeException("Could not find shopkeeper user with id " + id);
    }

    @Transactional
    public ShopkeeperUserPresenterDto create(User user) {
        ShopkeeperUser shopkeeperUser = new ShopkeeperUser(user);
        ShopkeeperUser savedShopkeeperUser = shopkeeperUserRepository.save(shopkeeperUser);
        return new ShopkeeperUserPresenterDto(savedShopkeeperUser);
    }

}
