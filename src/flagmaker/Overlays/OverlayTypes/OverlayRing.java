package flagmaker.Overlays.OverlayTypes;

import flagmaker.Extensions.ColorExtensions;
import flagmaker.Overlays.Attribute;
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
			new Attribute("X", true, 1, true),
			new Attribute("Y", true, 1, false),
			new Attribute("Width", true, 1, true),
			new Attribute("Height", true, 1, false),
			new Attribute("Size", true, 1, true)
		}, maximumX, maximumY);
	}

	public OverlayRing(Color color, double x, double y, double width, double height, double size, int maximumX, int maximumY)
	{
		super("ring", color, new Attribute[]
		{
			new Attribute("X", true, x, true),
			new Attribute("Y", true, y, false),
			new Attribute("Width", true, width, true),
			new Attribute("Height", true, height, false),
			new Attribute("Size", true, size, true)
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
		double outerDiamX = canvas.getWidth() * (GetAttribute("Width").Value / MaximumX);
		double outerDiamY = GetAttribute("Height").Value == 0
			? outerDiamX
			: canvas.getHeight() * (GetAttribute("Height").Value / MaximumY);

		double proportion = GetAttribute("Size").Value / MaximumX;
		double innerDiamX = outerDiamX * proportion;
		double innerDiamY = outerDiamY * proportion;

		double locX = (canvas.getWidth() * (GetAttribute("X").Value / MaximumX));
		double locY = (canvas.getHeight() * (GetAttribute("Y").Value / MaximumY));

		Ellipse outer = new Ellipse(locX, locY, outerDiamX / 2, outerDiamY / 2);
		Ellipse inner = new Ellipse(locX, locY, innerDiamX / 2, innerDiamY / 2);
		Shape ring = Path.subtract(outer, inner);
		ring.setFill(Color);
		canvas.getChildren().add(ring);
	}

	@Override
	public void SetValues(double[] values)
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
		double x = width * GetAttribute("X").Value / MaximumX;
		double y = height * GetAttribute("Y").Value / MaximumY;

		double outerRadX = width * GetAttribute("Width").Value / MaximumX / 2;
		double outerRadY = GetAttribute("Height").Value == 0
			? outerRadX
			: height * GetAttribute("Height").Value / MaximumY / 2;

		double proportion = GetAttribute("Size").Value / MaximumX;
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
