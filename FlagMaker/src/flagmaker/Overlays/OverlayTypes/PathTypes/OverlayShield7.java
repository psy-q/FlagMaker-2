package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayShield7 extends OverlayPath
{
	private final String Path = "m -69.03151 -91.96898 v 114.938 c 0 0 0.062 0 0.063 0 0 38.088 30.911 69 69 69 38.088 0 69 -30.912 69 -69 h -0.125 v -114.938 h -137.938 z";
	private final Vector PathSize = new Vector(200, 6);
	
	public OverlayShield7(int maximumX, int maximumY)
	{
		super("shield 7", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
