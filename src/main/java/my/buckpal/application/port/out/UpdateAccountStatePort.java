package my.buckpal.application.port.out;

import my.buckpal.account.domain.Account;

public interface UpdateAccountStatePort {
	
	void updateActivities(Account account);
}
