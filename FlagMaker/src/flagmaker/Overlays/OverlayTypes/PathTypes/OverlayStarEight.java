package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayStarEight extends OverlayPath
{
	private final String Path = "M 35.38014,35.33055 9.5832897,23.09028 0.03507971,50 -9.55088,23.1037 -35.33053,35.38014 -23.09027,9.58329 -50,0.03508036 -23.10369,-9.5508796 -35.38014,-35.33053 -9.58328,-23.09027 -0.03507029,-50 9.5508897,-23.1037 35.33054,-35.38014 23.09028,-9.5832796 50.00001,-0.03507964 23.10371,9.55088 z";
	private final Vector PathSize = new Vector(100, 100);

	public OverlayStarEight(int maximumX, int maximumY)
	{
		super("star eight", maximumX, maximumY);
		Constructor(Path, PathSize);
	}
}
