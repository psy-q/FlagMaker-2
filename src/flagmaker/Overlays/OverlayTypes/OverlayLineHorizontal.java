package flagmaker.Overlays.OverlayTypes;

import flagmaker.Extensions.ColorExtensions;
import flagmaker.Overlays.Attribute;
import flagmaker.Overlays.Overlay;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class OverlayLineHorizontal extends Overlay
{
	public OverlayLineHorizontal(int maximumX, int maximumY)
	{
		super("line horizontal", new Attribute[]
		{
			new Attribute("Y", true, 1, false),
			new Attribute("Thickness", true, 0.5, false)
		}, maximumX, maximumY);
	}

	public OverlayLineHorizontal(Color color, double y, double thickness, int maximumX, int maximumY)
	{
		super("line horizontal", color, new Attribute[]
		{
			new Attribute("Y", true, y, false),
			new Attribute("Thickness", true, thickness, false)
		}, maximumX, maximumY);
	}

	@Override
	protected Shape[] Thumbnail()
	{
		Line l = new Line(0, 15, 30, 15);
		l.setStrokeWidth(5);
		return new Shape[] { l };
	}

	@Override
	public void Draw(Pane canvas)
	{
		Line line = new Line(
				0,
				canvas.getHeight() * GetAttribute("Y").Value / MaximumY,
				canvas.getWidth(),
				canvas.getHeight() * GetAttribute("Y").Value / MaximumY);
		line.setStrokeWidth(canvas.getHeight() * (GetAttribute("Thickness").Value / MaximumY));
		line.setStroke(Color);
		canvas.getChildren().add(line);
	}

	@Override
	public void SetValues(double[] values)
	{
		SetAttribute("Y", values[0]);
		SetAttribute("Thickness", values[1]);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		return String.format("<line x1=\"0\" y1=\"%.3f\" x2=\"%d\" y2=\"%.3f\" stroke=\"#%s\" stroke-width=\"%.3f\" />",
			height * GetAttribute("Y").Value / MaximumY,
			width,
			height * GetAttribute("Y").Value / MaximumY,
			ColorExtensions.ToHexString(Color, false),
			height * (GetAttribute("Thickness").Value / MaximumY));
	}
}
