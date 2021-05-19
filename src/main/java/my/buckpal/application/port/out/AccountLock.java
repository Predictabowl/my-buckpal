package my.buckpal.application.port.out;

import my.buckpal.account.domain.AccountId;

public interface AccountLock {
	
	void lockAccount(AccountId accountId);

	void releaseAccount(AccountId accountId);
}
