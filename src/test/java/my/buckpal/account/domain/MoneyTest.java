package my.buckpal.account.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

class MoneyTest {

	@Test
	void test_Money_add() {
		Money money1 = new Money(new BigInteger("300"));
		Money money2 = new Money(new BigInteger("1001"));
		
		Money result = Money.add(money1, money2);
		
		assertThat(result.getAmount()).isEqualByComparingTo(new BigInteger("1301"));
	}

}
