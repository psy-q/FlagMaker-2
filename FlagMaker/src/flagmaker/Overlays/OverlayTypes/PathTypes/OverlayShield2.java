package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayShield2 extends OverlayPath
{
	private final String Path = "M -6.4673962e-6 -92.216243 C 27.734207 -92.216243 55.468417 -87.501422 83.202633 -78.349133 83.202633 -13.451076 83.757317 55.329776 -6.4673961e-6 92.216283 -83.757337 55.329776 -83.202653 -13.451076 -83.202653 -78.349133 c 27.734216 -9.152289 55.468429 -13.86711 83.2026465326039 -13.86711 z";
	private final Vector PathSize = new Vector(200, 6);
	
	public OverlayShield2(int maximumX, int maximumY)
	{
		super("shield 2", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
