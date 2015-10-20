package flagmaker.Overlays.OverlayTypes;

import flagmaker.Extensions.ColorExtensions;
import flagmaker.Overlays.Attribute;
import flagmaker.Overlays.Overlay;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class OverlayLineVertical extends Overlay
{
	public OverlayLineVertical(int maximumX, int maximumY)
	{
		super("line vertical", new Attribute[]
		{
			new Attribute("X", true, 1, true),
			new Attribute("Thickness", true, 0.5, false)
		}, maximumX, maximumY);
	}

	public OverlayLineVertical(Color color, double x, double thickness, int maximumX, int maximumY)
	{
		super("line vertical", color, new Attribute[]
		{
			new Attribute("X", true, x, true),
			new Attribute("Thickness", true, thickness, false)
		}, maximumX, maximumY);
	}

	@Override
	protected Shape[] Thumbnail()
	{
		Line l = new Line(15, 5, 15, 25);
		l.setStrokeWidth(5);
		return new Shape[] { l };
	}

	@Override
	public void Draw(Pane canvas)
	{
		Line line = new Line(
				canvas.getWidth() * GetAttribute("X").Value / MaximumX,
				0,
				canvas.getWidth() * GetAttribute("X").Value / MaximumX,
				canvas.getHeight());
		line.setStrokeWidth(canvas.getWidth() * (GetAttribute("Thickness").Value / MaximumX));
		line.setStroke(Color);
		canvas.getChildren().add(line);
	}

	@Override
	public void SetValues(double[] values)
	{
		SetAttribute("X", values[0]);
		SetAttribute("Thickness", values[1]);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		return String.format("<line x1=\"%.3f\" y1=\"0\" x2=\"%3f\" y2=\"%d\" stroke=\"#%s\" stroke-width=\"%.3f\" />",
			width * GetAttribute("X").Value / MaximumX,
			width * GetAttribute("X").Value / MaximumX,
			height,
			ColorExtensions.ToHexString(Color, false),
			width * (GetAttribute("Thickness").Value / MaximumX));
	}
}
