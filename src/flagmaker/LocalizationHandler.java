package flagmaker;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationHandler
{
	private Locale _currentLocale;
	private ResourceBundle _bundle;
	
	public LocalizationHandler()
	{
		_currentLocale = Locale.getDefault();
		if (_currentLocale == null) _currentLocale = Locale.US;
		_bundle = ResourceBundle.getBundle("bundles.strings", _currentLocale);
	}
	
	public String Get(String key)
	{
		return _bundle.getString(key);
	}
}
