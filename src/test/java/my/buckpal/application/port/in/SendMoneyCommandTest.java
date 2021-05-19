package my.buckpal.application.port.in;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolationException;
import my.buckpal.account.domain.AccountId;
import my.buckpal.account.domain.Money;

class SendMoneyCommandTest {

	
	
	@Test
	void test_creation_success() {
		AccountId sourceAccountId = new AccountId(13L);
		AccountId targetAccountId = new AccountId(32L);
		assertThatCode(() -> {
			SendMoneyCommand sendMoneyCommand = new SendMoneyCommand(sourceAccountId, targetAccountId, Money.of(100L));
		}).doesNotThrowAnyException();
	}
	
	@Test
	void test_creation_failure() {
		AccountId sourceAccountId = new AccountId(13L);
		AccountId targetAccountId = new AccountId(32L);
		assertThatThrownBy(() -> {
			SendMoneyCommand sendMoneyCommand = new SendMoneyCommand(sourceAccountId, targetAccountId, null);
		}).isInstanceOf(ConstraintViolationException.class);
	}

}
