package uk.andrewhook.validation;

public class RequiredValidator extends ValidationRule {

	@Override
	public boolean passes() {
		if (this.testValue != null && this.testValue != ""
				&& this.testValue.length() > 0) {
			return true;
		}
		this.errorMessage = "Value is required.";
		return false;
	}

}
