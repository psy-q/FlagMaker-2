package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Vector;

public class OverlayStarSix extends OverlayPath
{
	private final String Path = "M 2.9e-7,-57.5 -16.66667,-28.758199 -50,-28.758199 -33.33333,-5.0999996e-6 -50,28.758199 l 33.33333,0 L 2.9e-7,57.5 16.66667,28.758199 50,28.758199 33.33333,-5.0999996e-6 50,-28.758199 l -33.33333,0 L 2.9e-7,-57.5 z";
	private final Vector PathSize = new Vector(100, 115);

	public OverlayStarSix(int maximumX, int maximumY)
	{
		super("star six", maximumX, maximumY);
		Constructor(Path, PathSize);
	}
}
