package controllers;

import models.ProviderUsers;
import models.TokenAction;
import models.TokenAction.Type;
import models.User;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import providers.MyLoginUsernamePasswordAuthUser;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyIdentity;
import providers.MyUsernamePasswordAuthUser;
import views.html.account.signup.*;
import util.Global;


import com.feth.play.module.pa.PlayAuthenticate;

import static play.data.Form.form;

public class Signup extends Controller {

	public static class PasswordReset extends Account.PasswordChange {

		public PasswordReset() {
		}

		public PasswordReset(final String token) {
			this.token = token;
		}

		public String token;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
	}

	private static final Form<PasswordReset> PASSWORD_RESET_FORM = form(PasswordReset.class);

	public static Result unverified(Long pid) {
		// com.feth.play.module.pa.controllers.Authenticate.noCache(response());

//		if (pid == 61L) {
//			System.out.println("Here");
//			return redirect(Global.SERVER_URL + "assets/docs/unverified.php");
//
//		} else {
			return ok(unverified.render());
//		}
	}

	private static final Form<MyIdentity> FORGOT_PASSWORD_FORM = form(MyIdentity.class);

	public static Result forgotPassword(Long pid, final String email) {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		Form<MyIdentity> form = FORGOT_PASSWORD_FORM;
		if (email != null && !email.trim().isEmpty()) {
			form = FORGOT_PASSWORD_FORM.fill(new MyIdentity(email));
		}

		if (pid == 61L) {

			return ok(views.html.portal.publicportal_forgotpassword
					.render(form));

		} else {
			return ok(password_forgot.render(form));

		}
	}

	public static Result doForgotPassword(Long pid) {
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final Form<MyIdentity> filledForm = FORGOT_PASSWORD_FORM
				.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not fill in his/her email
			return badRequest(password_forgot.render(filledForm));
		} else {
			// The email address given *BY AN UNKNWON PERSON* to the form - we
			// should find out if we actually have a user with this email
			// address and whether password login is enabled for him/her. Also
			// only send if the email address of the user has been verified.
			final String email = filledForm.get().email;

			// We don't want to expose whether a given email address is signed
			// up, so just say an email has been sent, even though it might not
			// be true - that's protecting our user privacy.
			flash(Application.FLASH_MESSAGE_KEY,
					Messages.get(
							"playauthenticate.reset_password.message.instructions_sent",
							email));

			final User user = User.findByEmail(email);
			if (user != null) {
				// yep, we have a user with this email that is active - we do
				// not know if the user owning that account has requested this
				// reset, though.
				final MyUsernamePasswordAuthProvider provider = MyUsernamePasswordAuthProvider
						.getProvider();
				// User exists
				if (user.emailValidated) {
					provider.sendPasswordResetMailing(user, ctx());
					// In case you actually want to let (the unknown person)
					// know whether a user was found/an email was sent, use,
					// change the flash message
				} else {
					// We need to change the message here, otherwise the user
					// does not understand whats going on - we should not verify
					// with the password reset, as a "bad" user could then sign
					// up with a fake email via OAuth and get it verified by an
					// a unsuspecting user that clicks the link.
					flash(Application.FLASH_MESSAGE_KEY,
							Messages.get("playauthenticate.reset_password.message.email_not_verified"));

					// You might want to re-send the verification email here...
					provider.sendVerifyEmailMailingAfterSignup(user, ctx());
				}
			}

			return ok(password_forgot_email_sent.render()); // redirect(routes.Application.portalindex(Application.getCurrentPid()));
		}
	}

	/**
	 * Returns a token object if valid, null if not
	 * 
	 * @param token
	 * @param type
	 * @return
	 */
	private static TokenAction tokenIsValid(final String token, final Type type) {
		TokenAction ret = null;
		if (token != null && !token.trim().isEmpty()) {
			final TokenAction ta = TokenAction.findByToken(token, type);
			if (ta != null && ta.isValid()) {
				ret = ta;
			}
		}

		return ret;
	}

	public static Result resetPassword(Long pid, final String token) {
		session("currentportal", pid.toString());

		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final TokenAction ta = tokenIsValid(token, Type.PASSWORD_RESET);
		if (ta == null) {
			return badRequest(no_token_or_invalid.render());
		}

		return ok(password_reset.render(PASSWORD_RESET_FORM
				.fill(new PasswordReset(token))));
	}

	public static Result doResetPassword(Long pid) {
		session("currentportal", pid.toString());

		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final Form<PasswordReset> filledForm = PASSWORD_RESET_FORM
				.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(password_reset.render(filledForm));
		} else {
			final String token = filledForm.get().token;
			final String newPassword = filledForm.get().password;

			final TokenAction ta = tokenIsValid(token, Type.PASSWORD_RESET);
			if (ta == null) {
				return badRequest(no_token_or_invalid.render());
			}
			final User u = ta.targetUser;
			try {
				// Pass true for the second parameter if you want to
				// automatically create a password and the exception never to
				// happen
				u.resetPassword(new MyUsernamePasswordAuthUser(newPassword),
						false);
			} catch (final RuntimeException re) {
				flash(Application.FLASH_MESSAGE_KEY,
						Messages.get("playauthenticate.reset_password.message.no_password_account"));
			}

			// send the user to the login page
			flash(Application.FLASH_MESSAGE_KEY,
					Messages.get("playauthenticate.reset_password.message.success.manual_login"));

			return redirect(routes.Application.login(pid));
		}
	}

	public static Result oAuthDenied(Long pid, final String getProviderKey) {
		session("currentportal", pid.toString());

		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		return ok(oAuthDenied.render(getProviderKey));
	}

	public static Result exists() {

		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		return ok(exists.render());
	}

	public static Result verify(Long pid, final String token) {
		session("currentportal", pid.toString());

		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final TokenAction ta = tokenIsValid(token, Type.EMAIL_VERIFICATION);
		if (ta == null) {
			return badRequest(no_token_or_invalid.render());
		}
		final String email = ta.targetUser.email;

		for (ProviderUsers au : Application.getLocalPortal().userList) {

			if (au.getRights().equals("admin")) {
				System.out.println("trying to send an email to admin: "
						+ au.getUser().getName());
				final MyUsernamePasswordAuthProvider provider = MyUsernamePasswordAuthProvider
						.getProvider();

				String linkToUserRightsTable = Global.SERVER_URL + pid
						+ "/portal/rights/" + pid;
				String text = "Auf deinem Geoquest Portal "
						+ pid
						+ " wurde ein neuer User mit dem Namen '"
						+ ta.targetUser.getName()
						+ "' und der E-Mail "
						+ ta.targetUser.getEmail()
						+ " erstellt. Solltest nur du die Berechtigung haben, diesen User freizuschalten, kümmere dich bitte darum: "
						+ linkToUserRightsTable;

				String html = "Auf deinem Geoquest Portal "
						+ pid
						+ " wurde ein neuer User mit dem Namen '"
						+ ta.targetUser.getName()
						+ "' und der E-Mail "
						+ ta.targetUser.getEmail()
						+ " erstellt.<br/><br/> Solltest nur du die Berechtigung haben, diesen User freizuschalten, kümmere dich bitte darum: "
						+ "<a href=\"" + linkToUserRightsTable + "\">"
						+ linkToUserRightsTable + "</a>";
				provider.sendEmailToUser(au.getUser(), "Neuer User auf Portal "
						+ pid + ": " + ta.targetUser.getName(), text, html);
			}

		}

		User.verify(ta.targetUser);
		flash(Application.FLASH_MESSAGE_KEY,
				Messages.get("playauthenticate.verify_email.success", email));

		if (Application.getLocalUser(session()) != null) {
			return redirect(routes.Application.index());
		} else {
			return redirect(routes.Application.login(pid));
		}
	}
}
