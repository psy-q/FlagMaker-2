package flagmaker.Overlays.OverlayTypes;

import flagmaker.Extensions.ColorExtensions;
import flagmaker.Overlays.Attributes.*;
import flagmaker.Overlays.Overlay;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

public class OverlayTriangle extends Overlay
{
	public OverlayTriangle(int maximumX, int maximumY)
	{
		super("triangle", new Attribute[]
		{
			new ColorAttribute("Color", Color.BLACK),
			new DoubleAttribute("X1", 1, maximumX, true),
			new DoubleAttribute("Y1", 1, maximumY, false),
			new DoubleAttribute("X2", 1, maximumX, true),
			new DoubleAttribute("Y2", 2, maximumY, false),
			new DoubleAttribute("X3", 2, maximumX, true),
			new DoubleAttribute("Y3", 1, maximumY, false)
		}, maximumX, maximumY);
	}

	public OverlayTriangle(Color color, double x1, double y1, double x2,
			double y2, double x3, double y3, int maximumX, int maximumY)
	{
		super("triangle", new Attribute[]
		{
			new ColorAttribute("Color", color),
			new DoubleAttribute("X1", x1, maximumX, true),
			new DoubleAttribute("Y1", y1, maximumY, false),
			new DoubleAttribute("X2", x2, maximumX, true),
			new DoubleAttribute("Y2", y2, maximumY, false),
			new DoubleAttribute("X3", x3, maximumX, true),
			new DoubleAttribute("Y3", y3, maximumY, false)
		}, maximumX, maximumY);
	}

	@Override
	protected Shape[] Thumbnail()
	{
		SVGPath p = new SVGPath();
		p.setContent("M 0,0 15,15 0,30 0,0");
		return new Shape[] { p };
	}

	@Override
	public void Draw(Pane canvas)
	{
		double x1 = canvas.getWidth() * (GetDoubleAttribute("X1") / MaximumX);
		double y1 = canvas.getHeight() * (GetDoubleAttribute("Y1") / MaximumY);
		double x2 = canvas.getWidth() * (GetDoubleAttribute("X2") / MaximumX);
		double y2 = canvas.getHeight() * (GetDoubleAttribute("Y2") / MaximumY);
		double x3 = canvas.getWidth() * (GetDoubleAttribute("X3") / MaximumX);
		double y3 = canvas.getHeight() * (GetDoubleAttribute("Y3") / MaximumY);

		SVGPath path = new SVGPath();
		path.setContent(String.format("M %.3f,%.3f %.3f,%.3f %.3f,%.3f",
				x1, y1, x2, y2, x3, y3));
		path.setFill(GetColorAttribute("Color"));
		canvas.getChildren().add(path);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double x1 = width * (GetDoubleAttribute("X1") / MaximumX);
		double y1 = height * (GetDoubleAttribute("Y1") / MaximumY);
		double x2 = width * (GetDoubleAttribute("X2") / MaximumX);
		double y2 = height * (GetDoubleAttribute("Y2") / MaximumY);
		double x3 = width * (GetDoubleAttribute("X3") / MaximumX);
		double y3 = height * (GetDoubleAttribute("Y3") / MaximumY);

		return String.format("<polygon points=\"%.3f,%.3f %.3f,%.3f %.3f,%.3f\" %s />",
				x1, y1, x2, y2, x3, y3, ColorExtensions.ToSvgFillWithOpacity(GetColorAttribute("Color")));
	}
}
