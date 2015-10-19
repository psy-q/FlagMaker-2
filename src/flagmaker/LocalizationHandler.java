package flagmaker;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Node;

public class LocalizationHandler
{
	private Locale _currentLocale;
	private ResourceBundle _bundle;
	private ResourceBundle _defaultBundle;
	
	public LocalizationHandler()
	{
		_currentLocale = Locale.getDefault();
		if (_currentLocale == null) _currentLocale = Locale.US;
		_bundle = ResourceBundle.getBundle("bundles.strings", _currentLocale);
		_defaultBundle = ResourceBundle.getBundle("bundles.strings", Locale.US);
	}
	
	public String Get(String key)
	{
		String s = _bundle.getString(key);
		return s == null
			? _defaultBundle.getString(key)
			: s;
	}
}
