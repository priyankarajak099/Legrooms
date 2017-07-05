package com.legrooms.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
	/**if valid than gives true otherwise false	 */
	public static boolean isValid(String email)
	{
		Pattern pattern;
		Matcher matcher;
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();

	}
}
