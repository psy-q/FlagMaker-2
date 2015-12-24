package flagmaker.Extensions;

public class CommonExtensions
{
	private static final String _os = System.getProperty("os.name");
	
	public static final Runtime RunTime = Runtime.getRuntime();
	
	public static boolean IsWindows()
	{
		return _os.contains("win");
	}
	
	public static boolean IsMac()
	{
		return _os.contains("mac");
	}
	
	public static boolean IsLinux()
	{
		return _os.contains("nix") || _os.contains("nux");
	}
	
	public static String TitleAndVersionString(Class c)
	{
		Package p = c.getPackage();
		String version = p.getImplementationVersion();
		String title = p.getImplementationTitle();
		return String.format("%s %s", title, version);
	}
}
