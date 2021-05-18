package my.buckpal.application.service;

import java.time.LocalDateTime;

import my.buckpal.account.domain.AccountId;
import my.buckpal.account.domain.Money;
import my.buckpal.application.port.in.GetAccountBalanceQuery;
import my.buckpal.application.port.out.LoadAccountPort;

public class GetAccountBalanceService implements GetAccountBalanceQuery {

	private final LoadAccountPort loadAccountPort;

	public GetAccountBalanceService(LoadAccountPort loadAccountPort) {
		this.loadAccountPort = loadAccountPort;
	}

	@Override
	public Money getAccountBalance(AccountId accountId) {
		return loadAccountPort.loadAccount(accountId, LocalDateTime.now()).calculateBalance();
	}

}
