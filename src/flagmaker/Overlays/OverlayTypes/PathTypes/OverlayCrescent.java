package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Vector;

public class OverlayCrescent extends OverlayPath
{
	private final String Path = "M -5 -150 C -87.842712 -150 -155 -82.842712 -155 0 C -155 82.842712 -87.842712 150 -5 150 C 44.839604 150 88.972932 125.68744 116.25 88.28125 C 94.874916 107.96283 66.346865 120 35 120 C -31.27417 120 -85 66.27417 -85 0 C -85 -66.27417 -31.27417 -120 35 -120 C 66.346865 -120 94.874916 -107.96283 116.25 -88.28125 C 88.972932 -125.68744 44.839604 -150 -5 -150 z ";
	private final Vector PathSize = new Vector(309, 341);
	
	public OverlayCrescent(int maximumX, int maximumY)
	{
		super("crescent", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
