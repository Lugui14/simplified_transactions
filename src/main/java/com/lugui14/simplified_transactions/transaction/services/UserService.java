package com.lugui14.simplified_transactions.transaction.services;

import com.lugui14.simplified_transactions.transaction.domain.User;
import com.lugui14.simplified_transactions.transaction.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        throw new RuntimeException("Could not find user with id " + id);
    }

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User rechargeValue(Integer id, BigDecimal value) {
        this.addBalance(id, value);
        return this.findById(id);
    }

    public void addBalance(Integer id, BigDecimal amount) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find common user with id " + id));

        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);
    }

    public void subtractBalance(Integer id, BigDecimal amount) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find common user with id " + id));

        BigDecimal subtractedBalance = user.getBalance().subtract(amount);

        if(subtractedBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("User balance cannot be negative");
        }

        user.setBalance(subtractedBalance);
        userRepository.save(user);
    }
}
