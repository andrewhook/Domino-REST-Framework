package uk.andrewhook.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator extends ValidationRule {

	@Override
	public boolean passes() {
		if (this.testValue.length() > 0
				&& this.isValidEmailAddress(this.testValue) == true) {
			return true;
		}
		this.errorMessage = "A valid email address is required.";
		return false;
	}

	private boolean isValidEmailAddress(String email) {
		Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
