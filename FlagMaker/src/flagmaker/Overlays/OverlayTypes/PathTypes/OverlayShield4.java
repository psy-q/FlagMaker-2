package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayShield4 extends OverlayPath
{
	private final String Path = "M -68.99601 -95.365981 V 67.244022 c 0 7.802 6.257 14.062 14.061 14.062 h 40.873 c 7.803 0 14.0620000000079 6.259 14.0620000000079 14.06 0 -7.802 6.2579999999921 -14.06 14.0609999999921 -14.06 h 40.873 c 7.803 0 14.062 -6.26 14.062 -14.062 V -95.365981 h -137.992 z";
	private final Vector PathSize = new Vector(200, 6);
	
	public OverlayShield4(int maximumX, int maximumY)
	{
		super("shield 4", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
