package my.buckpal.account.domain;

import java.time.LocalDateTime;

public class Account {
	
	private AccountId id;
	private Money baselineBalance;
	private ActivityWindow activityWindow;
	
	public Account(AccountId id, Money baselineBalance, ActivityWindow activityWindow) {
		this.id = id;
		this.baselineBalance = baselineBalance;
		this.activityWindow = activityWindow;
	}

	public AccountId getId() {
		return id;
	}

	public Money getBaselineBalance() {
		return baselineBalance;
	}

	public ActivityWindow getActivityWindow() {
		return activityWindow;
	}

	public Money calculateBalance() {
		return Money.add(baselineBalance, activityWindow.calculateBalance(id));
	}

	public boolean withdraw(Money money, AccountId targetId) {
		if(!mayWithdraw(money)) 
			return false;
		Activity withdrawal = new Activity(id, id, targetId, LocalDateTime.now(), money.negate());
		activityWindow.addActivity(withdrawal);
		return true;
	}

	public boolean mayWithdraw(Money money) {
		return Money.add(this.calculateBalance(), money.negate())
				.isPositiveOrZero();
	}

	public boolean deposit(Money money, AccountId sourceAccountId) {
		Activity deposit = new Activity(id, sourceAccountId, id, LocalDateTime.now(), money);
		activityWindow.addActivity(deposit);
		return true;
	}

	
}
