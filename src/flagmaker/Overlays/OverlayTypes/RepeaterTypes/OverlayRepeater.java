package flagmaker.Overlays.OverlayTypes.RepeaterTypes;

import flagmaker.Overlays.Attributes.*;
import flagmaker.Overlays.Overlay;

public abstract class OverlayRepeater extends Overlay
{	
	protected Overlay Overlay;
	
	public OverlayRepeater(String name, Attribute[] attributes, int maximumX, int maximumY)
	{
		super(name, attributes, maximumX, maximumY);
	}
	
	public void SetOverlay(Overlay overlay)
	{
		Overlay = overlay;
	}
}
