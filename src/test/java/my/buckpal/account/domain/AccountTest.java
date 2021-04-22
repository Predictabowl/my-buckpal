package my.buckpal.account.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class AccountTest {
	
	@Mock
	private AccountId id;
	
	@Mock
	private Money baselineBalance;
	
	@Mock
	private ActivityWindow activityWindow;
	
	private Account account;
	
	@BeforeEach
	void setUp() {
		openMocks(this);
		account = new Account(id,baselineBalance,activityWindow);
	}

	@Test
	void test_calculateBalance() {
//		when(baselineBalance.add(any(Money.class),any(Money.class)))
		MockedStatic<Money> moneyMock = Mockito.mockStatic(Money.class);
		moneyMock.when(() -> Money.add(any(Money.class), any(Money.class)))
			.thenReturn(moneyMock);
		
		Money money = account.calculateBalance();
		
//		verify
	}

}
