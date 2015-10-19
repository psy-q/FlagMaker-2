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
		SetBundle(_currentLocale);
		_defaultBundle = ResourceBundle.getBundle("bundles.strings", Locale.US);
	}
	
	public static void SetLanguage(Locale locale)
	{
		_currentLocale = locale;
		SetBundle(locale);
	}
	
	public static String Get(String key)
	{
		try
		{
			return _bundle.getString(key);
		}
		catch (Exception e)
		{
			return _defaultBundle.getString(key);
		}
	}
	
	private static void SetBundle(Locale locale)
	{
		_bundle = ResourceBundle.getBundle("bundles.strings", locale);
	}
}
