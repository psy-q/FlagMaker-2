package flagmaker.Overlays.OverlayTypes.SpecialTypes;

import flagmaker.Overlays.Attributes.*;
import flagmaker.Overlays.Overlay;

public abstract class OverlaySpecial extends Overlay
{
	protected OverlaySpecial(String name, int maximumX, int maximumY)
	{
		super(name, new Attribute[]
		{
			new DoubleAttribute("X", 1, maximumX, true),
			new DoubleAttribute("Y", 1, maximumY, false),
			new DoubleAttribute("Width", 1, maximumX, true),
			new DoubleAttribute("Height", 1, maximumY, false)
		}, maximumX, maximumY);
	}
	
	protected OverlaySpecial(String name, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super(name, new Attribute[]
		{
			new DoubleAttribute("X", x, maximumX, true),
			new DoubleAttribute("Y", y, maximumY, false),
			new DoubleAttribute("Width", width, maximumX, true),
			new DoubleAttribute("Height", height, maximumY, false)
		}, maximumX, maximumY);
	}
}
