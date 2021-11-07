package com.infopulse.infomail.models.mail;

import com.infopulse.infomail.models.mail.enums.EmailStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmailLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String message; // for causes of fails

	@Column(nullable = false)
	private LocalDateTime logDateTime; // when it was inserted in database

	@Enumerated(EnumType.STRING)
	private EmailStatus emailStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	private AppUserEmailsInfo userInfo;

	public EmailLog(String message, LocalDateTime logDateTime, EmailStatus emailStatus, AppUserEmailsInfo userInfo) {
		this.message = message;
		this.logDateTime = logDateTime;
		this.emailStatus = emailStatus;
		this.userInfo = userInfo;
	}

}
