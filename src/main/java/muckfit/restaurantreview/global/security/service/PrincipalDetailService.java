package muckfit.restaurantreview.global.security.service;

import lombok.RequiredArgsConstructor;
import muckfit.restaurantreview.domain.account.entity.Account;
import muckfit.restaurantreview.domain.account.repository.AccountRepository;
import muckfit.restaurantreview.global.security.authentication.UserAccount;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        return new UserAccount(account);
    }
}
