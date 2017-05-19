package uk.andrewhook.validation;

public class MinlengthValidator extends ValidationRule {

	@Override
	public boolean passes() {
		if (argument.length() > 0 && this.testValue.length() > 0
				&& this.testValue.length() >= Integer.parseInt(argument)) {
			return true;
		}
		this.errorMessage = "Minimum value of " + argument + " is required.";
		return false;
	}

}
