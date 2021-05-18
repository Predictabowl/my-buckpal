package my.buckpal.account.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class AccountTest {
	
	private static final int ACTIVITY_MONEY = 256;
	private static final int BASELINE_BALANCE = 999;
	private AccountId id;
	private Money baselineBalance;
	
	@Mock
	private ActivityWindow activityWindow;
	
	private Account account;
	
	@BeforeEach
	void setUp() {
		openMocks(this);
		id = new AccountId(342L);
		baselineBalance = new Money(BigInteger.valueOf(BASELINE_BALANCE));
		account = new Account(id,baselineBalance,activityWindow);
		
		Money activityMoney = new Money(BigInteger.valueOf(ACTIVITY_MONEY));
		when(activityWindow.calculateBalance(id)).thenReturn(activityMoney);
	}

	/*
	 * This test was made to try the mocking of static methods. 
	 */
	
	@Test
	void test_calculateBalance() {
		Money baselineBalance = mock(Money.class);
		account = new Account(id,baselineBalance,activityWindow);
		Money moneyReturned = mock(Money.class);
		Money activityMoney = mock(Money.class);
		MockedStatic<Money> moneyMock = Mockito.mockStatic(Money.class);
		moneyMock.when(() -> Money.add(baselineBalance,activityMoney))
			.thenReturn(moneyReturned);
		when(activityWindow.calculateBalance(id)).thenReturn(activityMoney);
		
		Money money = account.calculateBalance();
		
		assertThat(money).isSameAs(moneyReturned);
		moneyMock.close();
	}
	
	@Test
	void test_mayWithdraw_up_to_zero_positive() {
		Money money = new Money(BigInteger.valueOf(BASELINE_BALANCE+ACTIVITY_MONEY));

		
		boolean result = account.mayWithdraw(money);
		
		assertThat(result).isTrue();		
	}
	
	@Test
	void test_mayWithdraw_negative() {
		Money money = new Money(BigInteger.valueOf(1685));
		
		boolean result = account.mayWithdraw(money);
		
		assertThat(result).isFalse();		
	}
	
	@Test
	void test_withdraw_success() {
		AccountId targetId = new AccountId(76l);
		Money money = new Money(BigInteger.valueOf(657));
		
		boolean result = account.withdraw(money,targetId);
		
		assertThat(result).isTrue();
		ArgumentCaptor<Activity> activityCaptor = ArgumentCaptor.forClass(Activity.class);
		verify(activityWindow).addActivity(activityCaptor.capture());
		assertThat(activityCaptor.getValue().getOwnerAccountId()).isSameAs(id);
		assertThat(activityCaptor.getValue().getSourceAccountId()).isSameAs(id);
		assertThat(activityCaptor.getValue().getTargetAccountId()).isSameAs(targetId);
		assertThat(activityCaptor.getValue().getMoney()).isEqualTo(money.negate());
	}
	
	@Test
	void test_withdraw_failure() {
		AccountId targetId = new AccountId(76l);
		Money money = new Money(BigInteger.valueOf(BASELINE_BALANCE+ACTIVITY_MONEY+1));
		
		boolean result = account.withdraw(money,targetId);
		
		assertThat(result).isFalse();
		verify(activityWindow,times(0)).addActivity(any());
	}
	
	@Test
	void test_deposit_success() {
		AccountId sourceId = new AccountId(65l);
		Money money = new Money(BigInteger.valueOf(124));
		
		boolean result = account.deposit(money,sourceId);
		
		assertThat(result).isTrue();
		
		ArgumentCaptor<Activity> activityCaptor = ArgumentCaptor.forClass(Activity.class);
		verify(activityWindow).addActivity(activityCaptor.capture());
		assertThat(activityCaptor.getValue().getOwnerAccountId()).isSameAs(id);
		assertThat(activityCaptor.getValue().getSourceAccountId()).isSameAs(sourceId);
		assertThat(activityCaptor.getValue().getTargetAccountId()).isSameAs(id);
		assertThat(activityCaptor.getValue().getMoney()).isEqualTo(money);
	}

}
