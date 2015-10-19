package flagmaker.Overlays.OverlayTypes;

import flagmaker.ColorExtensions;
import flagmaker.Overlays.Attribute;
import flagmaker.Overlays.Overlay;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class OverlayLine extends Overlay
{
	public OverlayLine(int maximumX, int maximumY)
	{
		super("line", new Attribute[]
		{
			new Attribute("X1", true, 1, true),
			new Attribute("Y1", true, 1, false),
			new Attribute("X2", true, 2, true),
			new Attribute("Y2", true, 2, false),
			new Attribute("Thickness", false, 0.5, true)
		}, maximumX, maximumY);
	}

	public OverlayLine(Color color, double x1, double y1, double x2, double y2, double thickness, int maximumX, int maximumY)
	{
		super("line", color, new Attribute[]
		{
			new Attribute("X1", true, x1, true),
			new Attribute("Y1", true, y1, false),
			new Attribute("X2", true, x2, true),
			new Attribute("Y2", true, y2, false),
			new Attribute("Thickness", true, thickness, true)
		}, maximumX, maximumY);
	}

	@Override
	protected Shape[] Thumbnail()
	{
		Line l = new Line(10, 5, 20, 25);
		l.setStrokeWidth(5);
		return new Shape[] { l };
	}

	@Override
	public void Draw(Pane canvas)
	{
		Line line = new Line(
				canvas.getWidth() * GetAttribute("X1").Value / MaximumX,
				canvas.getHeight() * GetAttribute("Y1").Value / MaximumY,
				canvas.getWidth() * GetAttribute("X2").Value / MaximumX,
				canvas.getHeight() * GetAttribute("Y2").Value / MaximumY);
		line.setStrokeWidth(canvas.getWidth() * (GetAttribute("Thickness").Value / MaximumX));
		line.setStroke(Color);
		canvas.getChildren().add(line);
	}

	@Override
	public void SetValues(double[] values)
	{
		SetAttribute("X1", values[0]);
		SetAttribute("Y1", values[1]);
		SetAttribute("X2", values[2]);
		SetAttribute("Y2", values[3]);
		SetAttribute("Thickness", values[4]);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		return String.format("<line x1=\"%.3f\" y1=\"%.3f\" x2=\"%.3f\" y2=\"%.3f\" stroke=\"#%s\" stroke-width=\"%.3f\" />",
			width * GetAttribute("X1").Value / MaximumX,
			height * GetAttribute("Y1").Value / MaximumY,
			width * GetAttribute("X2").Value / MaximumX,
			height * GetAttribute("Y2").Value / MaximumY,
			ColorExtensions.ToHexString(Color, false),
			width * (GetAttribute("Thickness").Value / MaximumX));
	}
}
