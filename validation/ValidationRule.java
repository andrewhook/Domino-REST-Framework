package uk.andrewhook.validation;

abstract public class ValidationRule {
	protected String[] ruleParts = null;
	protected String testValue = null;
	protected String errorMessage = null;
	protected String argument = null;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String[] getRuleParts() {
		return ruleParts;
	}

	public String getArgument() {
		return argument;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}

	public void setRuleParts(String[] ruleParts) {
		this.ruleParts = ruleParts;
	}

	public String getTestValue() {
		return testValue;
	}

	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}

	public abstract boolean passes();
}
