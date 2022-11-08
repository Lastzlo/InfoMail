package com.infopulse.infomail.services.triggerStop;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
public class TriggerStopTest {

	private final String DISABLE_REASON = "Disabled because jobName was obtained from an existing database, " +
			"if you specify the actual 'jobName' and 'jobGroup' values, then you can remove the annotation";
	@Autowired
	private Scheduler scheduler;

	@Disabled(DISABLE_REASON)
	@Test
	public void testTriggerStopping() throws SchedulerException {
		CronTrigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(UUID.randomUUID().toString())
				.startAt(Timestamp.valueOf(LocalDateTime.now().plusDays(2)))
				.endAt(Timestamp.valueOf(LocalDateTime.now().plusDays(4)))
				.forJob("f53ae432-14f9-4e20-9f37-561cad1a2adc", "admin@infomail.com")
				.withSchedule(CronScheduleBuilder.cronSchedule("* * 4 * * ? *"))
				.build();
		scheduler.scheduleJob(trigger);
		scheduler.pauseJob(new JobKey(
				"f53ae432-14f9-4e20-9f37-561cad1a2adc",
				"admin@infomail.com"));

	}

	public static void main(String[] args) {
		System.out.println(Timestamp.from(Instant.ofEpochMilli(1638636882324L)));
	}
}
