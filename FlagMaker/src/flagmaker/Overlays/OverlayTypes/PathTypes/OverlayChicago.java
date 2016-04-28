package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayChicago extends OverlayPath
{
	private final String Path = "M 0.0,1.0 L 0.183,0.317 0.866,0.500 0.366,0 0.866,-0.500 0.183,-0.317 0,-1.000 -0.183,-0.317 -0.866,-0.500 -0.366,0 -0.866,0.500 -0.183,0.317 Z";
	private final Vector PathSize = new Vector(2, 2);
	
	public OverlayChicago(int maximumX, int maximumY)
	{
		super("chicago", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
