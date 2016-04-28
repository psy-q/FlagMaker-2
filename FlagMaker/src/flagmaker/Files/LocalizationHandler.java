package flagmaker.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationHandler
{
	private static Locale _currentLocale;
	private static ResourceBundle _bundle;
	private static ResourceBundle _defaultBundle;
	
	public static void Initialize()
	{
		_currentLocale = GetLocalePreference();
		if (_currentLocale == null) _currentLocale = Locale.US;
		SetBundle(_currentLocale);
		_defaultBundle = ResourceBundle.getBundle("bundles.strings", Locale.US);
	}
	
	public static void SetLanguage(Locale locale)
	{
		_currentLocale = locale;
		SetBundle(_currentLocale);
		SaveLocalePreference(_currentLocale);
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
	
	private static Locale GetLocalePreference()
	{
		File preferencesFile = new File("flagmaker.config");
		if (preferencesFile.exists())
		{
			try (FileReader fr = new FileReader(preferencesFile); BufferedReader sr = new BufferedReader(fr))
			{
				String line;
				while ((line = sr.readLine()) != null)
				{
					if (line.startsWith("locale="))
					{
						return new Locale(line.split("=")[1]);
					}
				}
			}
			catch (Exception e)
			{
				return Locale.getDefault();
			}
		}
		
		return Locale.getDefault();
	}
	
	private static void SaveLocalePreference(Locale locale)
	{
		File preferencesFile = new File("flagmaker.config");
		
		try
		{
			preferencesFile.createNewFile();
		}
		catch (Exception e)
		{
		}
		
		try (FileWriter writer = new FileWriter(preferencesFile, false); PrintWriter printLine = new PrintWriter(writer))
		{
			printLine.println(String.format("locale=%s", locale.getLanguage()));
		}
		catch (Exception e)
		{
		}
	}
}
