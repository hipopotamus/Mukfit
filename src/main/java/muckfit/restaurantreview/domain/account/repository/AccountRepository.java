package muckfit.restaurantreview.domain.account.repository;

import muckfit.restaurantreview.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select account from Account account where account.email = :email and account.deleted = false")
    Optional<Account> findByEmail(@Param("email") String email);
}
