package com.infopulse.infomail.services.scheduler;

import com.infopulse.infomail.dto.mail.EmailSchedule;
import com.infopulse.infomail.models.mail.Schedule;
import org.quartz.*;

import java.text.ParseException;

public interface SchedulerService<T extends Trigger, P extends Schedule> {

	ScheduleBuilder<T> buildSchedule(CronExpression cronExpression); // for cron expressions

	ScheduleBuilder<T> buildSchedule(boolean alwaysRepeat,
	                                 int repeatCount,
	                                 long interval) throws ParseException; // for simple triggers

	Trigger buildTrigger(JobDetail jobDetail,
	                     ScheduleBuilder<T> scheduleBuilder,
	                     P schedule);

	JobDetail buildJobDetail(String userEmail,
	                         long messageTemplateId,
	                         String description,
	                         Class<? extends Job> jobClass);




}
