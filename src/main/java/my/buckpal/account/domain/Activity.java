package my.buckpal.account.domain;

import java.time.LocalDateTime;

public class Activity {
	
	private Long id;
	private final AccountId ownerAccountId;
	private final AccountId sourceAccountId;
	private final AccountId targetAccountId;
	private final LocalDateTime timeStamp;
	private final Money money;
	
	public Activity(Long id, AccountId ownerAccountId, AccountId sourceAccountId, AccountId targetAccountId,
			LocalDateTime timeStamp, Money money) {
		super();
		this.id = id;
		this.ownerAccountId = ownerAccountId;
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.timeStamp = timeStamp;
		this.money = money;
	}
	

	public Activity(AccountId ownerAccountId, AccountId sourceAccountId, AccountId targetAccountId,
			LocalDateTime timeStamp, Money money) {
		super();
		this.ownerAccountId = ownerAccountId;
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.timeStamp = timeStamp;
		this.money = money;
		id = null;
	}



	public Long getId() {
		return id;
	}

	public AccountId getOwnerAccountId() {
		return ownerAccountId;
	}

	public AccountId getSourceAccountId() {
		return sourceAccountId;
	}

	public AccountId getTargetAccountId() {
		return targetAccountId;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public Money getMoney() {
		return money;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result + ((ownerAccountId == null) ? 0 : ownerAccountId.hashCode());
		result = prime * result + ((sourceAccountId == null) ? 0 : sourceAccountId.hashCode());
		result = prime * result + ((targetAccountId == null) ? 0 : targetAccountId.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
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
		Activity other = (Activity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (ownerAccountId == null) {
			if (other.ownerAccountId != null)
				return false;
		} else if (!ownerAccountId.equals(other.ownerAccountId))
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
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}

	
}
