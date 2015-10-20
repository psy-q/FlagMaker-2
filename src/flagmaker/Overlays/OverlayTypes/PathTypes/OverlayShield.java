package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Vector;

public class OverlayShield extends OverlayPath
{
	private final String Path = "m -99.913278,-130.6551 201.180888,-0.54666 c 0,0 0.41114,60.801139 -1.06377,127.2427789 C 98.280472,82.686289 23.081262,132.47878 0.6771725,131.19807 -25.446228,129.72547 -97.373478,77.864309 -100.42739,-2.9874312 -102.67845,-62.584321 -99.913278,-130.6551 -99.913278,-130.6551 z";
	private final Vector PathSize = new Vector(207, 267);

	public OverlayShield(int maximumX, int maximumY)
	{
		super("shield", maximumX, maximumY);
		Constructor(Path, PathSize);
	}
}
