package com.lugui14.simplified_transactions.notification.domain;

import jakarta.validation.constraints.NotNull;

public record NotificationDto(@NotNull(message = "Notification UserId cannot be null") Integer userId, @NotNull(message = "Notification message cannot be null") String message) {}
