package muckfit.restaurantreview.domain.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import muckfit.restaurantreview.domain.account.enums.Role;
import muckfit.restaurantreview.global.auditing.BaseField;

@Entity
@Getter
public class Account extends BaseField {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
