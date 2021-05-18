package my.buckpal.application.service;

import my.buckpal.application.port.in.SendMoneyCommand;
import my.buckpal.application.port.in.SondMoneyUseCase;
import my.buckpal.application.port.out.AccountLock;
import my.buckpal.application.port.out.LoadAccountPort;
import my.buckpal.application.port.out.UpdateAccountStatePort;

public class SendMoneyService implements SondMoneyUseCase {

	private final UpdateAccountStatePort updateAccountStatePort;
	private final AccountLock accountLock;
	private final LoadAccountPort loadAccountPort;
	
	public SendMoneyService(UpdateAccountStatePort updateAccountStatePort, AccountLock accountLock,
			LoadAccountPort loadAccountPort) {
		super();
		this.updateAccountStatePort = updateAccountStatePort;
		this.accountLock = accountLock;
		this.loadAccountPort = loadAccountPort;
	}


	@Override
	public boolean sendMoney(SendMoneyCommand command) {
		// TODO Auto-generated method stub
		return false;
	}

}
