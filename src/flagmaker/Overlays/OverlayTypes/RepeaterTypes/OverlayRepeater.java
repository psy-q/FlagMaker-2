package flagmaker.Overlays.OverlayTypes.RepeaterTypes;

import flagmaker.Overlays.Attribute;
import flagmaker.Overlays.Overlay;

public abstract class OverlayRepeater extends Overlay
{	
	protected Overlay Overlay;
	
	public OverlayRepeater(Attribute[] attributes, int maximumX, int maximumY)
	{
		super(attributes, maximumX, maximumY);
	}
	
	public void SetOverlay(Overlay overlay)
	{
		Overlay = overlay;
	}
}
