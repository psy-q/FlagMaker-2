package flagmaker.Overlays.OverlayTypes.ShapeTypes;

import flagmaker.Overlays.Attributes.*;
import flagmaker.Overlays.Overlay;
import javafx.scene.paint.Color;

public abstract class OverlayShape extends Overlay
{
	protected OverlayShape(String name, int maximumX, int maximumY)
	{
		super(name, new Attribute[]
		{
			new DoubleAttribute("X", 1, maximumX, true),
			new DoubleAttribute("Y", 1, maximumY, false),
			new DoubleAttribute("Width", 1, maximumX, true),
			new DoubleAttribute("Height", 1, maximumY, false)
		}, maximumX, maximumY);
	}
	
	protected OverlayShape(String name, Color color, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super(name, color, new Attribute[]
		{
			new DoubleAttribute("X", x, maximumX, true),
			new DoubleAttribute("Y", y, maximumY, false),
			new DoubleAttribute("Width", width, maximumX, true),
			new DoubleAttribute("Height", height, maximumY, false)
		}, maximumX, maximumY);
	}
	
	protected OverlayShape(String name, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super(name, new Attribute[]
		{
			new DoubleAttribute("X", x, maximumX, true),
			new DoubleAttribute("Y", y, maximumY, false),
			new DoubleAttribute("Width", width, maximumX, true),
			new DoubleAttribute("Height", height, maximumY, false)
		}, maximumX, maximumY);
	}

	@Override
	public void SetValues(Object[] values)
	{
		SetAttribute("X", values[0]);
		SetAttribute("Y", values[1]);
		SetAttribute("Width", values[2]);
		SetAttribute("Height", values[3]);
	}
}
