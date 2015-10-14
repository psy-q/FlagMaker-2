package flagmaker;

import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayFactory;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPath;
import java.io.File;
import javafx.scene.paint.Color;

public class TempOverlay
{
	public String Type;
	public final double[] Values;
	public Color Color;
	public File Path;

	public Color StrokeColor;

	public TempOverlay()
	{
		Values = new double[]
		{
			0,
			0,
			0,
			0,
			0,
			0,
			0,
			0
		};
	}

	public Overlay ToOverlay(int maximumX, int maximumY, String directory) throws Exception
	{
		Overlay overlay;

		if (Path.exists())
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

		overlay.SetColor(Color);
		overlay.SetValues(Values);

		if (overlay instanceof OverlayPath)
		{
			((OverlayPath)overlay).StrokeColor = StrokeColor;
		}

		return overlay;
	}
}
