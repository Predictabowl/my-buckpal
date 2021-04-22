package my.buckpal.account.domain;


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

	
}
