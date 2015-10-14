package flagmaker.Overlays.OverlayTypes;

import flagmaker.ColorExtensions;
import flagmaker.Overlays.Attribute;
import flagmaker.Overlays.Overlay;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

public class OverlayTriangle extends Overlay
{
	public OverlayTriangle(int maximumX, int maximumY)
	{
		super(new Attribute[]
		{
			new Attribute("X1", true, 1, true),
			new Attribute("Y1", true, 1, false),
			new Attribute("X2", true, 1, true),
			new Attribute("Y2", true, 2, false),
			new Attribute("X3", true, 2, true),
			new Attribute("Y3", true, 1, false)
		}, maximumX, maximumY);
	}

	public OverlayTriangle(Color color, double x1, double y1, double x2,
			double y2, double x3, double y3, int maximumX, int maximumY)
	{
		super(color, new Attribute[]
		{
			new Attribute("X1", true, x1, true),
			new Attribute("Y1", true, y1, false),
			new Attribute("X2", true, x2, true),
			new Attribute("Y2", true, y2, false),
			new Attribute("X3", true, x3, true),
			new Attribute("Y3", true, y3, false)
		}, maximumX, maximumY);
	}

	@Override
	public String Name()
	{
		return "triangle";
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
		double x1 = canvas.getWidth() * (GetAttribute("X1").Value / MaximumX);
		double y1 = canvas.getHeight() * (GetAttribute("Y1").Value / MaximumY);
		double x2 = canvas.getWidth() * (GetAttribute("X2").Value / MaximumX);
		double y2 = canvas.getHeight() * (GetAttribute("Y2").Value / MaximumY);
		double x3 = canvas.getWidth() * (GetAttribute("X3").Value / MaximumX);
		double y3 = canvas.getHeight() * (GetAttribute("Y3").Value / MaximumY);

		SVGPath path = new SVGPath();
		path.setContent(String.format("M %.3f,%.3f %.3f,%.3f %.3f,%.3f",
				x1, y1, x2, y2, x3, y3));
		path.setFill(Color);
		canvas.getChildren().add(path);
	}

	@Override
	public void SetValues(Double[] values)
	{
		SetAttribute("X1", values[0]);
		SetAttribute("Y1", values[1]);
		SetAttribute("X2", values[2]);
		SetAttribute("Y2", values[3]);
		SetAttribute("X3", values[4]);
		SetAttribute("Y3", values[5]);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double x1 = width * (GetAttribute("X1").Value / MaximumX);
		double y1 = height * (GetAttribute("Y1").Value / MaximumY);
		double x2 = width * (GetAttribute("X2").Value / MaximumX);
		double y2 = height * (GetAttribute("Y2").Value / MaximumY);
		double x3 = width * (GetAttribute("X3").Value / MaximumX);
		double y3 = height * (GetAttribute("Y3").Value / MaximumY);
		double x4 = width * (GetAttribute("X4").Value / MaximumX);
		double y4 = height * (GetAttribute("Y4").Value / MaximumY);

		return String.format("<polygon points=\"%.3f,%.3f %.3f,%.3f %.3f,%.3f\" %s />",
				x1, y1, x2, y2, x3, y3, ColorExtensions.ToSvgFillWithOpacity(Color));
	}
}
