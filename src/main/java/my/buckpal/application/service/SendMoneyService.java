package my.buckpal.application.service;

import java.time.LocalDateTime;

import my.buckpal.account.domain.Account;
import my.buckpal.account.domain.AccountId;
import my.buckpal.application.port.in.SendMoneyCommand;
import my.buckpal.application.port.in.SondMoneyUseCase;
import my.buckpal.application.port.out.AccountLock;
import my.buckpal.application.port.out.LoadAccountPort;
import my.buckpal.application.port.out.UpdateAccountStatePort;
import my.buckpal.application.service.exceptions.ThresholdExceededException;

public class SendMoneyService implements SondMoneyUseCase {

	private final UpdateAccountStatePort updateAccountStatePort;
	private final AccountLock accountLock;
	private final LoadAccountPort loadAccountPort;
	private final MoneyTransferProperties moneyTransferProperties;
	
	public SendMoneyService(UpdateAccountStatePort updateAccountStatePort, AccountLock accountLock,
			LoadAccountPort loadAccountPort, MoneyTransferProperties moneyTransferProperties) {
		super();
		this.updateAccountStatePort = updateAccountStatePort;
		this.accountLock = accountLock;
		this.loadAccountPort = loadAccountPort;
		this.moneyTransferProperties = moneyTransferProperties;
	}


	@Override
	public boolean sendMoney(SendMoneyCommand command) {
		if (command.getMoney().isGreaterThan(moneyTransferProperties.getMaximumTransferTreshold()))
			throw new ThresholdExceededException(
					moneyTransferProperties.getMaximumTransferTreshold(),
					command.getMoney());
		
		LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);
		Account sourceAccount = loadAccountPort.loadAccount(command.getSourceAccountId(), baselineDate);
		Account targetAccount = loadAccountPort.loadAccount(command.getTargetAccountId(), baselineDate);
		AccountId sourceAccountId = sourceAccount.getId()
				.orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
		AccountId targetAccountId = targetAccount.getId()
				.orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));	
		
		accountLock.lockAccount(sourceAccountId);
		if (!sourceAccount.withdraw(command.getMoney(), sourceAccountId))
		{
			accountLock.releaseAccount(sourceAccountId);
			return false;
		}
		
		accountLock.lockAccount(targetAccountId);
		if(!targetAccount.deposit(command.getMoney(), targetAccountId)) {
			accountLock.releaseAccount(targetAccountId);
			accountLock.releaseAccount(sourceAccountId);
			return false;
		}
		
		accountLock.releaseAccount(targetAccountId);
		accountLock.releaseAccount(sourceAccountId);
		
		updateAccountStatePort.updateActivities(sourceAccount);
		updateAccountStatePort.updateActivities(targetAccount);
		return true;
	}

}
