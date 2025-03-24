package com.lugui14.simplified_transactions.transaction.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SHOPKEEPER")
public class ShopkeeperUser extends User {
}
