package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayAlbatross extends OverlayPath
{
	private final String Path = "m 21.702102 14.65406 -5.16063 -5.6212406 -14.5992298 -6.6443 -10.84285 0.0132 5.11713 -3.33502996 -9.4761002 -9.62816944 -8.481 -4.10825 8.67802 0 10.6845902 13.0693494 6.03996 -0.89038 -1.01136 3.9475899 15.8394898 4.5588501 3.21198 8.6384006";
	private final Vector PathSize = new Vector(43, 29);
	
	public OverlayAlbatross(int maximumX, int maximumY)
	{
		super("albatross", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
