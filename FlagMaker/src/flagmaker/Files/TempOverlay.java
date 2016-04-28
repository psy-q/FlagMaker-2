package flagmaker.Files;

import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayFactory;
import java.io.File;
import java.util.HashMap;

public class TempOverlay
{
	public String Type;
	public final HashMap<String, String> Values;

	public TempOverlay()
	{
		Values = new HashMap<>();
	}

	public Overlay ToOverlay(int maximumX, int maximumY, String directory) throws Exception
	{
		Overlay overlay = null;

		if (Values.containsKey("path"))
		{
			File path = FileHandler.GetFilePossiblyRelative(new File(Values.get("path")), directory);
			if (path != null)
			{
				overlay = Type.equals("flag")
					? OverlayFactory.GetFlagInstance(path, maximumX, maximumY)
					: OverlayFactory.GetImageInstance(path, maximumX, maximumY);
			}
		}
		else
		{
			overlay = OverlayFactory.GetInstanceByShortName(Type, maximumX, maximumY);
		}

		if (overlay == null) return null;

		overlay.SetValuesFromStrings(Values);
		return overlay;
	}
}
