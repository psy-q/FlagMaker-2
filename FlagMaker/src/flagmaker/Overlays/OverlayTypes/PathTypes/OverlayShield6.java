package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayShield6 extends OverlayPath
{
	private final String Path = "m -80.25101 -95.438479 c 7.804 0 14.063 10.51 14.063 18.313 V 67.312519 c 0 7.803 6.259 14.063 14.063 14.063 h 38 c 7.803 0 14.125 6.258 14.125 14.063 0 -7.805 6.321 -14.063 14.125 -14.063 h 38 c 7.803 0 14.062 -6.26 14.063 -14.063 V -77.124479 c 0 -7.804 6.259 -18.313 14.063 -18.313 h -80.25 -80.252 z";
	private final Vector PathSize = new Vector(200, 6);
	
	public OverlayShield6(int maximumX, int maximumY)
	{
		super("shield 6", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
