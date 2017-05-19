package uk.andrewhook.validation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.xml.internal.ws.util.StringUtils;

public class Validator {

	private boolean passed = false;
	private final ArrayList<String> errors = new ArrayList();

	public Validator(HashMap<String, String> fieldRules,
			HashMap<String, String> values) {
		for (String key : fieldRules.keySet()) {
			String rulesLine = fieldRules.get(key);
			String[] rules = rulesLine.split("\\|");
			for (String rule : rules) {
				String[] ruleParts = rule.split(":");
				String validatorName = StringUtils.capitalize(ruleParts[0])
						+ "Validator";

				// Create a new ValidationRule instance of the correct type
				// and pass in the value from the values hashmap
				String value = values.get(key);
				this.check(key, validatorName, ruleParts, value);
			}
		}

		// Right at the end, if the errors arraylist.length == 0,
		// set passed to true.
		if (errors.size() == 0) {
			passed = true;
		}
	}

	private void check(String fieldName, String validatorName,
			String[] ruleParts, String value) {
		try {
			Class<?> clazz = Class.forName("uk.andrewhook.validation."
					+ validatorName);
			Constructor<?> constructor = clazz.getConstructor();
			ValidationRule vrule = (ValidationRule) constructor.newInstance();

			vrule.setRuleParts(ruleParts);
			vrule.setTestValue(value);

			if (ruleParts.length > 1) {
				vrule.setArgument(ruleParts[1]);
			}

			if (!vrule.passes()) {
				errors.add(fieldName + ": " + vrule.getErrorMessage());
			}

		} catch (ClassNotFoundException e) {
			errors.add("Class not found: " + e.getMessage());
		} catch (SecurityException e) {
			errors.add(e.getMessage());
		} catch (NoSuchMethodException e) {
			errors.add(e.getMessage());
		} catch (IllegalArgumentException e) {
			errors.add(e.getMessage());
		} catch (InstantiationException e) {
			errors.add(e.getMessage());
		} catch (IllegalAccessException e) {
			errors.add(e.getMessage());
		} catch (InvocationTargetException e) {
			errors.add(e.getMessage());
		}
	}

	public boolean passed() {
		return passed;
	}

	public ArrayList<String> getErrors() {
		return errors;
	}
}
