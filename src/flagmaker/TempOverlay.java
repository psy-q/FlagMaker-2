package flagmaker;

import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayFactory;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPath;
import java.io.File;
import java.util.HashMap;
import javafx.scene.paint.Color;

public class TempOverlay
{
	public String Type;
	public final HashMap<String, Object> Values;
	public File Path;

	public Color StrokeColor;

	public TempOverlay()
	{
		Values = new HashMap<>();
	}

	public Overlay ToOverlay(int maximumX, int maximumY, String directory) throws Exception
	{
		Overlay overlay;

		if (Path != null && Path.exists())
		{
			overlay = Type.equals("flag")
				? OverlayFactory.GetFlagInstance(Path, maximumX, maximumY)
				: OverlayFactory.GetImageInstance(Path, maximumX, maximumY);
		}
		else
		{
			overlay = OverlayFactory.GetInstanceByShortName(Type, maximumX, maximumY);
		}

		if (overlay == null) return null;

		overlay.SetValues(Values);

		if (overlay instanceof OverlayPath)
		{
			((OverlayPath)overlay).StrokeColor = StrokeColor;
		}

		return overlay;
	}
}
