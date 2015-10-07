package flagmaker;

public class StringExtensions
{
	public static String GetFilenameWithoutExtension(String fname)
	{
		int period = fname.lastIndexOf(".");
		int slash = fname.lastIndexOf("/");
		if (period > 0)
		{
			fname = fname.substring(0, period);
		}
		if (slash > 0)
		{
			fname = fname.substring(slash + 1, fname.length() - 1);
		}
		return fname;
	}

	public static boolean IsNullOrWhitespace(String s)
	{
		return s == null || IsWhitespace(s);
	}

	private static boolean IsWhitespace(String s)
	{
		int length = s.length();
		if (length > 0)
		{
			for (int i = 0; i < length; i++)
			{
				if (!Character.isWhitespace(s.charAt(i)))
				{
					return false;
				}
			}
		}
		return true;
	}
}