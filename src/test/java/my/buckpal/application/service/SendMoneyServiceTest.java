package my.buckpal.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.MockitoAnnotations.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import my.buckpal.account.domain.Account;
import my.buckpal.account.domain.AccountId;
import my.buckpal.account.domain.Money;
import my.buckpal.application.port.in.SendMoneyCommand;
import my.buckpal.application.port.out.AccountLock;
import my.buckpal.application.port.out.LoadAccountPort;
import my.buckpal.application.port.out.UpdateAccountStatePort;
import my.buckpal.application.service.exceptions.ThresholdExceededException;

class SendMoneyServiceTest {

	private static final long FIXTURE_MAX_TRESHOLD = 1000L;
	
	@Mock
	private UpdateAccountStatePort updateAccountStatePort;
	@Mock
	private AccountLock accountLock;
	@Mock
	private LoadAccountPort loadAccountPort;
	@Mock
	private Account sourceAccount;
	@Mock
	private Account targetAccount;
	
	private MoneyTransferProperties moneyTransferProperties;
	
	private SendMoneyService sendMoneyService;
	private AccountId sourceAccountId;
	private AccountId targetAccountId;
	
	@BeforeEach
	void setUp() {
		openMocks(this);
		
		moneyTransferProperties = new MoneyTransferProperties(Money.of(FIXTURE_MAX_TRESHOLD));
		sendMoneyService = new SendMoneyService(updateAccountStatePort, accountLock, loadAccountPort,moneyTransferProperties);
		
		sourceAccountId = new AccountId(41L);
		when(loadAccountPort.loadAccount(eq(sourceAccountId), any(LocalDateTime.class)))
			.thenReturn(sourceAccount);
		
		targetAccountId = new AccountId(78L);
		when(loadAccountPort.loadAccount(eq(targetAccountId), any(LocalDateTime.class)))
			.thenReturn(targetAccount);
	}

	private void setupAccountIdMocks() {
		when(sourceAccount.getId()).thenReturn(Optional.of(sourceAccountId));
		when(targetAccount.getId()).thenReturn(Optional.of(targetAccountId));
	}
	
	@Test
	void test_withdrawal_source_failure() {
		setupAccountIdMocks();
		when(sourceAccount.withdraw(any(Money.class), any(AccountId.class)))
			.thenReturn(false);
		SendMoneyCommand command = new SendMoneyCommand(sourceAccountId, targetAccountId,  Money.of(300L));
		
		boolean sendResult = sendMoneyService.sendMoney(command);
		
		assertThat(sendResult).isFalse();
		verify(accountLock).lockAccount(sourceAccountId);
		verify(accountLock).releaseAccount(sourceAccountId);
		verifyNoMoreInteractions(accountLock);
		verify(sourceAccount).withdraw(Money.of(300L), sourceAccountId);
		verify(targetAccount,times(0)).deposit(any(Money.class), any(AccountId.class));
		verifyNoInteractions(updateAccountStatePort);
	}
	
	@Test
	void test_withdrawal_target_failure() {
		setupAccountIdMocks();
		when(sourceAccount.withdraw(any(Money.class), any(AccountId.class)))
			.thenReturn(true);
		when(targetAccount.deposit(any(Money.class), any(AccountId.class)))
			.thenReturn(false);
		
		SendMoneyCommand command = new SendMoneyCommand(sourceAccountId, targetAccountId, Money.of(300L));
		
		boolean sendResult = sendMoneyService.sendMoney(command);
		
		assertThat(sendResult).isFalse();
		verify(accountLock).lockAccount(sourceAccountId);
		verify(accountLock).releaseAccount(sourceAccountId);
		verify(accountLock).lockAccount(targetAccountId);
		verify(accountLock).releaseAccount(targetAccountId);
		verifyNoMoreInteractions(accountLock);
		verifyNoInteractions(updateAccountStatePort);
	}
	
	@Test
	void test_withdrawal_success() {
		setupAccountIdMocks();
		when(sourceAccount.withdraw(any(Money.class), any(AccountId.class)))
			.thenReturn(true);
		when(targetAccount.deposit(any(Money.class), any(AccountId.class)))
			.thenReturn(true);
		
		SendMoneyCommand command = new SendMoneyCommand(sourceAccountId, targetAccountId, Money.of(300L));
		
		boolean sendResult = sendMoneyService.sendMoney(command);
		
		assertThat(sendResult).isTrue();
		verify(accountLock).lockAccount(sourceAccountId);
		verify(accountLock).releaseAccount(sourceAccountId);
		verify(accountLock).lockAccount(targetAccountId);
		verify(accountLock).releaseAccount(targetAccountId);
		verifyNoMoreInteractions(accountLock);
		verify(updateAccountStatePort).updateActivities(sourceAccount);
		verify(updateAccountStatePort).updateActivities(targetAccount);
	}
	
	@Test
	void test_withdrawal_sourceId_missing() {
		when(sourceAccount.getId()).thenReturn(Optional.empty());
		when(targetAccount.getId()).thenReturn(Optional.of(targetAccountId));
		
		SendMoneyCommand command = new SendMoneyCommand(sourceAccountId, targetAccountId, Money.of(300L));
		
		assertThatThrownBy(() -> sendMoneyService.sendMoney(command))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("expected source account ID not to be empty");
	}
	
	@Test
	void test_withdrawal_targetId_missing() {
		when(sourceAccount.getId()).thenReturn(Optional.of(sourceAccountId));
		when(targetAccount.getId()).thenReturn(Optional.empty());
		
		SendMoneyCommand command = new SendMoneyCommand(sourceAccountId, targetAccountId, Money.of(300L));
		
		assertThatThrownBy(() -> sendMoneyService.sendMoney(command))
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("expected target account ID not to be empty");
	}
	
	@Test
	void test_money_treshold_exceded_should_throw() {
		SendMoneyCommand command = new SendMoneyCommand(sourceAccountId, targetAccountId,
				Money.of(FIXTURE_MAX_TRESHOLD+1));
		
		assertThatThrownBy(() -> sendMoneyService.sendMoney(command))
			.isInstanceOf(ThresholdExceededException.class);
	}

}
