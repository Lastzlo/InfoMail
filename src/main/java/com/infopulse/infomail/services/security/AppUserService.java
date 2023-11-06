package com.infopulse.infomail.services.security;

import com.infopulse.infomail.models.users.AppUser;
import com.infopulse.infomail.models.confirmation.ConfirmationToken;
import com.infopulse.infomail.models.users.roles.AppUserRole;
import com.infopulse.infomail.repositories.security.AppUserRepository;
import com.infopulse.infomail.services.registration.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AppUserService {

	private static final int CONFIRMATION_TOKEN_LIVE_TIME = 30;

	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final ConfirmationTokenService confirmationTokenService;

	public static Long ADMIN_ID = 0L; // redundant
	public static final String ADMIN_EMAIL = "admin@infomail.com"; // redundant
	public static final String ADMIN_PASSWORD = "password"; // redundant
	public static final String DEMO_EMAIL = "demo@mail.com"; // redundant
	public static final String DEMO_PASSWORD = "demo"; // redundant


	@PostConstruct // redundant methode
	private void init() {
		AppUser admin = appUserRepository.findAppUserByEmail(ADMIN_EMAIL).orElse(
				new AppUser(ADMIN_EMAIL, passwordEncoder.encode(ADMIN_PASSWORD), AppUserRole.USER,
						true, true, true));
		appUserRepository.save(admin);
		ADMIN_ID = admin.getUserId();
		log.info("\n==============================================\n" +
				"Admin: " + admin +
				"\n==============================================\n");

		AppUser demo = appUserRepository.findAppUserByEmail(ADMIN_EMAIL).orElse(
				new AppUser(DEMO_EMAIL, passwordEncoder.encode(DEMO_PASSWORD), AppUserRole.USER,
						true, true, true));
		appUserRepository.save(demo);
		log.info("\n==============================================\n" +
				"Demo account: " + demo +
				"\n==============================================\n");
	}

	@Transactional
	public String singUp(AppUser appUser) throws IllegalStateException {
		Optional<AppUser> optOldUser = appUserRepository
				.findAppUserByEmail(appUser.getEmail());

		// One token per CONFIRMATION_TOKEN_LIVE_TIME minutes
		String token = getValidConfirmTokenOrNewOne(appUser, optOldUser) // UUID.randomUUID().toString();
				.orElseThrow(() -> new IllegalStateException("User already has valid token"));

		log.info("User {} signed up", appUser.getEmail());
		return token;
	}

	public void enableAppUser(String email) {
		appUserRepository.enableAppUserByEmail(email);
		log.info("User {}'s is no enabled", email);
	}

	public void deleteUnConfirmedAppUser(Long appUserId) {
		appUserRepository.deleteAppUserByUserId(appUserId);
	}


	//Next methods are utils ------------------------------------------------------------------------------------------

	private Optional<String> getValidConfirmTokenOrNewOne(AppUser appUser,
	                                                      Optional<AppUser> optOldUser) throws IllegalStateException {
		if (optOldUser.isPresent()) {
			AppUser oldUser = optOldUser.get();

			if (oldUser.isEnabled()) // appUser's entered email already confirmed
				throw new IllegalStateException(String.format("Email %s already exists!", appUser.getEmail()));

			Optional<ConfirmationToken> confirmationToken = confirmationTokenService
					.getConfirmationTokenByAppUserId(oldUser.getUserId());

			if (confirmationToken.isPresent()) {
				if (tokenIsExpired(confirmationToken.get())) {

					log.info("Deleting {}'s expired confirmation token", oldUser.getEmail());

					confirmationTokenService.deleteConfirmationTokenById(confirmationToken.get().getTokenId());
					confirmationTokenService.flushRepository();
				} else
					return Optional.empty();

				log.info("Generating new confirmation token for {}", oldUser.getEmail());
				return getNewSavedToken(oldUser);
			}
		}

		String encodedPassword = passwordEncoder.encode(appUser.getPassword()); // encoding user password
		appUser.setPassword(encodedPassword);

		log.info("Saving new user {} to database", appUser.getEmail());
		AppUser newUser = appUserRepository.save(appUser); // saving new user and acquiring it with new ID

		return getNewSavedToken(newUser);
	}

	private Optional<String> getNewSavedToken(AppUser appUser) {
		String token = UUID.randomUUID().toString();
		saveNewConfirmationToken(appUser, token); // saving new token

		return Optional.of(token);
	}

	private boolean tokenIsExpired(ConfirmationToken token) {
		return LocalDateTime.now().isAfter(token.getExpiredAt());
	}

	private void saveNewConfirmationToken(AppUser appUser, String token) {
		ConfirmationToken confirmationToken = new ConfirmationToken(
				token,
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(CONFIRMATION_TOKEN_LIVE_TIME),
				appUser
		);

		log.info("Saving {}'s new confirmation token to database", appUser.getEmail());
		confirmationTokenService.saveConfirmationToken(confirmationToken);
	}
}
