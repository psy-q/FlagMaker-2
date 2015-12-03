package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayShield5 extends OverlayPath
{
	private final String Path = "m -68.93751 -92.18798 v 41.626 c 0 51.26 32.682 119.348 68.938 142.75 38.097 -22.48 68.937 -90.592 68.937 -142.75 v -41.626 c -11.27 7.461 -22.543 10.544 -33.813 10.313 -11.71 -0.241 -23.415 -4.063 -35.125 -10.313 -11.71 6.25 -23.415 10.071 -35.125 10.313 -11.269 0.231 -22.543 -2.852 -33.812 -10.313 z";
	private final Vector PathSize = new Vector(200, 6);
	
	public OverlayShield5(int maximumX, int maximumY)
	{
		super("shield 5", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
