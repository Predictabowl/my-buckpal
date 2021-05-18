package my.buckpal.application.port.in;

import my.buckpal.account.domain.AccountId;
import my.buckpal.account.domain.Money;

public interface GetAccountBalanceQuery {
	
	public Money getAccountBalance(AccountId accountId); 
}
