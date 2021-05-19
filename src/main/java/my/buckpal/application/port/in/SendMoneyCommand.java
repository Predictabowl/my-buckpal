package my.buckpal.application.port.in;


import jakarta.validation.constraints.NotNull;
import my.buckpal.account.domain.AccountId;
import my.buckpal.account.domain.Money;
import my.buckpal.shared.SelfValidating;

public class SendMoneyCommand extends SelfValidating<SendMoneyCommand>{
	
	@NotNull
	private final AccountId sourceAccountId;
	@NotNull
	private final AccountId targetAccountId;
	@NotNull
	private final Money money;
	
	public SendMoneyCommand(AccountId sourceAccountId, AccountId targetAccountId, Money money) {
		super();
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.money = money;
		this.validateSelf();
		//Should validate if money is greater than 0?
	}

	public AccountId getSourceAccountId() {
		return sourceAccountId;
	}

	public AccountId getTargetAccountId() {
		return targetAccountId;
	}

	public Money getMoney() {
		return money;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result + ((sourceAccountId == null) ? 0 : sourceAccountId.hashCode());
		result = prime * result + ((targetAccountId == null) ? 0 : targetAccountId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SendMoneyCommand other = (SendMoneyCommand) obj;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (sourceAccountId == null) {
			if (other.sourceAccountId != null)
				return false;
		} else if (!sourceAccountId.equals(other.sourceAccountId))
			return false;
		if (targetAccountId == null) {
			if (other.targetAccountId != null)
				return false;
		} else if (!targetAccountId.equals(other.targetAccountId))
			return false;
		return true;
	}
	
	
}
