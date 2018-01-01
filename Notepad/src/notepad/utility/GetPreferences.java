package notepad.utility;

import java.util.Locale;
import java.util.ResourceBundle;

public final class GetPreferences {

	public static String getPreference(String key, String bundleName, Locale locale) {

		String keyValue = null;
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
		try {
			
			keyValue = bundle.getString(key);
		} catch (Exception e) {

			keyValue = key;
		}
		return keyValue;
	}
	
	private GetPreferences(){}
}
