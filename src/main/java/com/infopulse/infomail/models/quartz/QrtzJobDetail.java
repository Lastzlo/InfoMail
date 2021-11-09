package com.infopulse.infomail.models.quartz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "qrtz_job_details", indexes = {
		@Index(name = "idx_qrtz_j_req_recovery", columnList = "sched_name, requests_recovery"),
		@Index(name = "idx_qrtz_j_grp", columnList = "sched_name, job_group")
})
public class QrtzJobDetail {

	@EmbeddedId
	private QrtzJobDetailId id;

	@Column(name = "description", length = 250)
	private String description;

	@Column(name = "job_class_name", nullable = false, length = 250)
	private String jobClassName;

	@Column(name = "is_durable", nullable = false)
	private Boolean isDurable = false;

	@Column(name = "is_nonconcurrent", nullable = false)
	private Boolean isNonconcurrent = false;

	@Column(name = "is_update_data", nullable = false)
	private Boolean isUpdateData = false;

	@Column(name = "requests_recovery", nullable = false)
	private Boolean requestsRecovery = false;

	@Column(name = "job_data")
	private byte[] jobData;

	public QrtzJobDetail(QrtzJobDetailId id) {
		this.id = id;
	}

	public QrtzJobDetail(QrtzJobDetailId id,
	                     String description,
	                     String jobClassName,
	                     Boolean isDurable,
	                     Boolean isNonconcurrent,
	                     Boolean isUpdateData,
	                     Boolean requestsRecovery,
	                     byte[] jobData) {
		this.id = id;
		this.description = description;
		this.jobClassName = jobClassName;
		this.isDurable = isDurable;
		this.isNonconcurrent = isNonconcurrent;
		this.isUpdateData = isUpdateData;
		this.requestsRecovery = requestsRecovery;
		this.jobData = jobData;
	}
}