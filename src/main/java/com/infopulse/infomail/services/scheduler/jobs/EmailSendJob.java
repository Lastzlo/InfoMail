package com.infopulse.infomail.services.scheduler.jobs;

import com.infopulse.infomail.models.mail.AppUserEmailsInfo;
import com.infopulse.infomail.models.mail.EmailTemplate;
import com.infopulse.infomail.models.mail.enums.RecipientType;
import com.infopulse.infomail.services.mail.AppUserEmailsInfoService;
import com.infopulse.infomail.services.mail.EmailTemplateService;
import com.infopulse.infomail.services.RecipientService;
import com.infopulse.infomail.services.mail.EmailSenderService;
import com.infopulse.infomail.services.scheduler.CronSchedulerService;
import lombok.AllArgsConstructor;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class EmailSendJob extends QuartzJobBean {

	private final RecipientService recipientService;
	private final EmailSenderService emailSenderService;
	private final EmailTemplateService emailTemplateService;
	private final AppUserEmailsInfoService appUserEmailsInfoService;

//	@Override
//	public void execute(JobExecutionContext context) throws JobExecutionException {
//
//
//	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
//		JobDataMap jobDetailMap = jobDetail.getJobDataMap();
		String jobName = jobDetail.getKey().getName();

		AppUserEmailsInfo appUserEmailsInfo = appUserEmailsInfoService.getAppUserEmailsInfoByJobName(jobName);
		Map<RecipientType, List<String>> groupedRecipients = recipientService.groupRecipients(
				recipientService.getAllByUserInfoId(appUserEmailsInfo.getId()));

//		Long emailTemplateId = jobDetailMap
//				.getLongFromString(CronSchedulerService.messageTemplateIdProp);
//				emailTemplateService.getEmailTemplateById(emailTemplateId);
		EmailTemplate emailTemplate = appUserEmailsInfo.getEmailTemplate();

		emailSenderService.sendMimeEmail(emailTemplate, groupedRecipients, appUserEmailsInfo);
	}
}
