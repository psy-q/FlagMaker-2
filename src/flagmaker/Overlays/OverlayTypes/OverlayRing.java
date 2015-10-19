package flagmaker.Overlays.OverlayTypes;

import flagmaker.ColorExtensions;
import flagmaker.Overlays.Attributes.*;
import flagmaker.Overlays.Overlay;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

public class OverlayRing extends Overlay
{
	public OverlayRing(int maximumX, int maximumY)
	{
		super("ring", new Attribute[]
		{
			new DoubleAttribute("X", 1, maximumX, true),
			new DoubleAttribute("Y", 1, maximumY, false),
			new DoubleAttribute("Width", 1, maximumX, true),
			new DoubleAttribute("Height", 1, maximumY, false),
			new DoubleAttribute("Size", 1, maximumX, true)
		}, maximumX, maximumY);
	}

	public OverlayRing(Color color, double x, double y, double width, double height, double size, int maximumX, int maximumY)
	{
		super("ring", color, new Attribute[]
		{
			new DoubleAttribute("X", x, maximumX, true),
			new DoubleAttribute("Y", y, maximumX, false),
			new DoubleAttribute("Width", width, maximumX, true),
			new DoubleAttribute("Height", height, maximumY, false),
			new DoubleAttribute("Size", size, maximumX, true)
		}, maximumX, maximumY);
	}

	@Override
	protected Shape[] Thumbnail()
	{
		Ellipse e1 = new Ellipse(15, 15, 15, 15);
		Ellipse e2 = new Ellipse(15, 15, 7, 7);
		return new Shape[] { Path.subtract(e1, e2) };
	}

	@Override
	public void Draw(Pane canvas)
	{
		double outerDiamX = canvas.getWidth() * (GetDoubleAttribute("Width") / MaximumX);
		double outerDiamY = GetDoubleAttribute("Height") == 0
			? outerDiamX
			: canvas.getHeight() * (GetDoubleAttribute("Height") / MaximumY);

		double proportion = GetDoubleAttribute("Size") / MaximumX;
		double innerDiamX = outerDiamX * proportion;
		double innerDiamY = outerDiamY * proportion;

		double locX = (canvas.getWidth() * (GetDoubleAttribute("X") / MaximumX));
		double locY = (canvas.getHeight() * (GetDoubleAttribute("Y") / MaximumY));

		Ellipse outer = new Ellipse(locX, locY, outerDiamX / 2, outerDiamY / 2);
		Ellipse inner = new Ellipse(locX, locY, innerDiamX / 2, innerDiamY / 2);
		Shape ring = Path.subtract(outer, inner);
		ring.setFill(Color);
		canvas.getChildren().add(ring);
	}

	@Override
	public void SetValues(Object[] values)
	{
		SetAttribute("X", values[0]);
		SetAttribute("Y", values[1]);
		SetAttribute("Width", values[2]);
		SetAttribute("Height", values[3]);
		SetAttribute("Size", values[4]);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double x = width * GetDoubleAttribute("X") / MaximumX;
		double y = height * GetDoubleAttribute("Y") / MaximumY;

		double outerRadX = width * GetDoubleAttribute("Width") / MaximumX / 2;
		double outerRadY = GetDoubleAttribute("Height") == 0
			? outerRadX
			: height * GetDoubleAttribute("Height") / MaximumY / 2;

		double proportion = GetDoubleAttribute("Size") / MaximumX;
		double innerRadX = outerRadX * proportion;
		double innerRadY = outerRadY * proportion;

		return String.format("<path d=\"" +
			"M %1$.3f,%2$.3f m -%3$.3f,0 a %3$.3f,%4$.3f 0 1,0 %5$.3f,0 a %3$.3f,%4$.3f 0 1,0 -%5$.3f,0 z" +
			"M %1$.3f,%2$.3f m %6$.3f,0 a %6$.3f,%7$.3f 0 1,1 -%8$.3f,0 a %6$.3f,%7$.3f 0 1,1 %8$.3f,0 z" +
			"\" %8$s />",
			x, y, outerRadX, outerRadY, 2 * outerRadX,
			innerRadX, innerRadY, 2 * innerRadX,
			ColorExtensions.ToSvgFillWithOpacity(Color));
	}
}
