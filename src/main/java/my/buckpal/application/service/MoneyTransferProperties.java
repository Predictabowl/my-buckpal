package my.buckpal.application.service;

import my.buckpal.account.domain.Money;

public class MoneyTransferProperties {

	private Money maximumTransferTreshold;
	
	public MoneyTransferProperties(Money maximumTransferTreshold) {
		super();
		this.maximumTransferTreshold = maximumTransferTreshold;
	}

	public MoneyTransferProperties() {
		maximumTransferTreshold = Money.of(1000000L);
	}

	public Money getMaximumTransferTreshold() {
		return maximumTransferTreshold;
	}

	public void setMaximumTransferTreshold(Money maximumTransferTreshold) {
		this.maximumTransferTreshold = maximumTransferTreshold;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maximumTransferTreshold == null) ? 0 : maximumTransferTreshold.hashCode());
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
		MoneyTransferProperties other = (MoneyTransferProperties) obj;
		if (maximumTransferTreshold == null) {
			if (other.maximumTransferTreshold != null)
				return false;
		} else if (!maximumTransferTreshold.equals(other.maximumTransferTreshold))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MoneyTransferProperties [maximumTransferTreshold=" + maximumTransferTreshold + "]";
	}
	
	
}
