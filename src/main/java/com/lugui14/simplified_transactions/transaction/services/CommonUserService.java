package com.lugui14.simplified_transactions.transaction.services;

import com.lugui14.simplified_transactions.transaction.domain.CommonUser;
import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.dtos.CommonUserPresenterDto;
import com.lugui14.simplified_transactions.transaction.domain.repositories.CommonUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommonUserService {

    private final CommonUserRepository commonUserRepository;

    public CommonUserService(CommonUserRepository commonUserRepository) {
        this.commonUserRepository = commonUserRepository;
    }

    public CommonUserPresenterDto findById(Integer id) {
        Optional<CommonUser> optionalCommonUser = commonUserRepository.findById(id);
        if (optionalCommonUser.isPresent()) {
            return new CommonUserPresenterDto(optionalCommonUser.get());
        }

        throw new RuntimeException("Could not find common user with id " + id);
    }

    @Transactional
    public CommonUserPresenterDto create(User user) {
        CommonUser commonUser = new CommonUser(user);
        CommonUser savedCommonUser = commonUserRepository.save(commonUser);
        return new CommonUserPresenterDto(savedCommonUser);
    }
}
