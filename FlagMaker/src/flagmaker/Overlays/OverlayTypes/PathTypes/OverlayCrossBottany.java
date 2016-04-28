package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayCrossBottany extends OverlayPath
{
	private final String Path = "m -54.098927,-10.64914 a 13.950377,13.950377 0 1 0 -24.46449,-1.12242 13.950377,13.950377 0 1 0 0,23.543125 13.950377,13.950377 0 1 0 24.46449,-1.12242 h 43.44978 v 43.449782 a 13.950377,13.950377 0 1 0 -1.12242,24.46449 13.950377,13.950377 0 1 0 23.54313,0 13.950377,13.950377 0 1 0 -1.12242,-24.46449 V 10.649145 h 43.44978 a 13.950377,13.950377 0 1 0 24.464486,1.12242 13.950377,13.950377 0 1 0 0,-23.543125 13.950377,13.950377 0 1 0 -24.464486,1.12242 h -43.44978 v -43.44978 a 13.950377,13.950377 0 1 0 1.12242,-24.46449 13.950377,13.950377 0 1 0 -23.54313,0 13.950377,13.950377 0 1 0 1.12242,24.46449 v 43.44978 z";
	private final Vector PathSize = new Vector(204, 204);
	
	public OverlayCrossBottany(int maximumX, int maximumY)
	{
		super("cross bottany", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
