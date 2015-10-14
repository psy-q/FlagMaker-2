package flagmaker.Overlays.OverlayTypes.ShapeTypes;

import flagmaker.Overlays.Attribute;
import flagmaker.Overlays.Overlay;
import javafx.scene.paint.Color;

public abstract class OverlayShape extends Overlay
{
	protected OverlayShape(int maximumX, int maximumY)
	{
		super(new Attribute[]
		{
			new Attribute("X", true, 1, true),
			new Attribute("Y", true, 1, false),
			new Attribute("Width", true, 1, true),
			new Attribute("Height", true, 1, false)
		}, maximumX, maximumY);
	}
	
	protected OverlayShape(Color color, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super(color, new Attribute[]
		{
			new Attribute("X", true, x, true),
			new Attribute("Y", true, y, false),
			new Attribute("Width", true, width, true),
			new Attribute("Height", true, height, false)
		}, maximumX, maximumY);
	}
	
	protected OverlayShape(double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super(new Attribute[]
		{
			new Attribute("X", true, x, true),
			new Attribute("Y", true, y, false),
			new Attribute("Width", true, width, true),
			new Attribute("Height", true, height, false)
		}, maximumX, maximumY);
	}

	@Override
	public void SetValues(double[] values)
	{
		SetAttribute("X", values[0]);
		SetAttribute("Y", values[1]);
		SetAttribute("Width", values[2]);
		SetAttribute("Height", values[3]);
	}
}
