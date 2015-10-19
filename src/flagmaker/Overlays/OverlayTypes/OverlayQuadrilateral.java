package flagmaker.Overlays.OverlayTypes;

import flagmaker.ColorExtensions;
import flagmaker.Overlays.Attributes.*;
import flagmaker.Overlays.Overlay;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

public class OverlayQuadrilateral extends Overlay
{
	public OverlayQuadrilateral(int maximumX, int maximumY)
	{
		super("quadrilateral", new Attribute[]
		{
			new DoubleAttribute("X1", 1, maximumX, true),
			new DoubleAttribute("Y1", 1, maximumY, false),
			new DoubleAttribute("X2", 1, maximumX, true),
			new DoubleAttribute("Y2", 2, maximumY, false),
			new DoubleAttribute("X3", 2, maximumX, true),
			new DoubleAttribute("Y3", 2, maximumY, false),
			new DoubleAttribute("X4", 2, maximumX, true),
			new DoubleAttribute("Y4", 1, maximumY, false)
		}, maximumX, maximumY);
	}

	public OverlayQuadrilateral(Color color, double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4, int maximumX, int maximumY)
	{
		super("quadrilateral", color, new Attribute[]
		{
			new DoubleAttribute("X1", x1, maximumX, true),
			new DoubleAttribute("Y1", y1, maximumY, false),
			new DoubleAttribute("X2", x2, maximumX, true),
			new DoubleAttribute("Y2", y2, maximumY, false),
			new DoubleAttribute("X3", x3, maximumX, true),
			new DoubleAttribute("Y3", y3, maximumY, false),
			new DoubleAttribute("X4", x4, maximumX, true),
			new DoubleAttribute("Y4", y4, maximumY, false)
		}, maximumX, maximumY);
	}

	@Override
	protected Shape[] Thumbnail()
	{
		SVGPath p = new SVGPath();
		p.setContent("M 10,5 20,5 30,30 0,30");
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
		double x4 = canvas.getWidth() * (GetDoubleAttribute("X4") / MaximumX);
		double y4 = canvas.getHeight() * (GetDoubleAttribute("Y4") / MaximumY);

		SVGPath path = new SVGPath();
		path.setContent(String.format("M %.3f,%.3f %.3f,%.3f %.3f,%.3f %.3f,%.3f",
				x1, y1, x2, y2, x3, y3, x4, y4));
		path.setFill(Color);
		canvas.getChildren().add(path);
	}

	@Override
	public void SetValues(Object[] values)
	{
		SetAttribute("X1", values[0]);
		SetAttribute("Y1", values[1]);
		SetAttribute("X2", values[2]);
		SetAttribute("Y2", values[3]);
		SetAttribute("X3", values[4]);
		SetAttribute("Y3", values[5]);
		SetAttribute("X4", values[6]);
		SetAttribute("Y4", values[7]);
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
		double x4 = width * (GetDoubleAttribute("X4") / MaximumX);
		double y4 = height * (GetDoubleAttribute("Y4") / MaximumY);

		return String.format("<polygon points=\"%.3f,%.3f %.3f,%.3f %.3f,%.3f %.3f,%.3f\" %s />",
				x1, y1, x2, y2, x3, y3, x4, y4, ColorExtensions.ToSvgFillWithOpacity(Color));
	}
}
