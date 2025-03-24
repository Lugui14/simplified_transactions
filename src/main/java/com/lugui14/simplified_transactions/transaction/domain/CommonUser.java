package com.lugui14.simplified_transactions.transaction.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("COMMON")
public class CommonUser extends User {
}
