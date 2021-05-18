package my.buckpal.application.port.out;

import java.time.LocalDateTime;

import my.buckpal.account.domain.Account;
import my.buckpal.account.domain.AccountId;

public interface LoadAccountPort {

	Account loadAccount(AccountId accountId, LocalDateTime now);

}
