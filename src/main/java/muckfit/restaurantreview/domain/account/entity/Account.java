package muckfit.restaurantreview.domain.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "acocunt_id")
    private Long id;

    private String email;

    private String password;
}
