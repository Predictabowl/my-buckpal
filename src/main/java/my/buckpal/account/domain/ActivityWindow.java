package my.buckpal.account.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActivityWindow {
	
	private List<Activity> activities;


	public ActivityWindow (List<Activity> activities) {
		this.activities = activities;
	}
	
	public ActivityWindow (Activity ...activities) {
		this.activities = new ArrayList<Activity>(Arrays.asList(activities));
	}
	
	
	public Money calculateBalance(AccountId accountId) {
		Money depositBalance = activities.stream().filter(a -> a.getTargetAccountId().equals(accountId))
				.map(Activity::getMoney).reduce(Money.ZERO, Money::add);
		Money withdrawBalance = activities.stream().filter(a -> a.getSourceAccountId().equals(accountId))
				.map(Activity::getMoney).reduce(Money.ZERO, Money::add);
		return Money.add(depositBalance, withdrawBalance.negate());
	}
	
	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}
	
	public LocalDateTime getStartTimeStamp() {
		return activities.stream()
				.min(Comparator.comparing(Activity::getTimeStamp))
				.orElseThrow(IllegalStateException::new).getTimeStamp();
	}
	
	public LocalDateTime getEndTimeStamp() {
		return activities.stream()
				.max(Comparator.comparing(Activity::getTimeStamp))
				.orElseThrow(IllegalStateException::new).getTimeStamp();
	}
	
	public List<Activity> getActivities() {
		return Collections.unmodifiableList(activities);
	}
}
