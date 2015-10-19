package flagmaker;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationHandler
{
	private static Locale _currentLocale;
	private static ResourceBundle _bundle;
	private static ResourceBundle _defaultBundle;
	
	public static void Initialize()
	{
		_currentLocale = Locale.getDefault();
		if (_currentLocale == null) _currentLocale = Locale.US;
		_bundle = ResourceBundle.getBundle("bundles.strings", _currentLocale);
		_defaultBundle = ResourceBundle.getBundle("bundles.strings", Locale.US);
	}
	
	public static String Get(String key)
	{
		String s = _bundle.getString(key);
		return s == null
			? _defaultBundle.getString(key)
			: s;
	}
}
