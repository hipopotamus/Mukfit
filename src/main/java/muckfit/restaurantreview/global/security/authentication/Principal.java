package muckfit.restaurantreview.global.security.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Principal {

    private final Long id;

    private final String email;
}
