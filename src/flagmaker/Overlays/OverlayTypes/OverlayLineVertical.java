package flagmaker.Overlays.OverlayTypes;

import flagmaker.ColorExtensions;
import flagmaker.Overlays.Attributes.*;
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
			new DoubleAttribute("X", 1, maximumX, true),
			new DoubleAttribute("Thickness", 0.5, maximumX, false)
		}, maximumX, maximumY);
	}

	public OverlayLineVertical(Color color, double x, double thickness, int maximumX, int maximumY)
	{
		super("line vertical", color, new Attribute[]
		{
			new DoubleAttribute("X", x, maximumX, true),
			new DoubleAttribute("Thickness", thickness, maximumX, false)
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
				canvas.getWidth() * GetDoubleAttribute("X") / MaximumX,
				0,
				canvas.getWidth() * GetDoubleAttribute("X") / MaximumX,
				canvas.getHeight());
		line.setStrokeWidth(canvas.getWidth() * (GetDoubleAttribute("Thickness") / MaximumX));
		line.setStroke(Color);
		canvas.getChildren().add(line);
	}

	@Override
	public void SetValues(Object[] values)
	{
		SetAttribute("X", values[0]);
		SetAttribute("Thickness", values[1]);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		return String.format("<line x1=\"%.3f\" y1=\"0\" x2=\"%3f\" y2=\"%d\" stroke=\"#%s\" stroke-width=\"%.3f\" />",
			width * GetDoubleAttribute("X") / MaximumX,
			width * GetDoubleAttribute("X") / MaximumX,
			height,
			ColorExtensions.ToHexString(Color, false),
			width * (GetDoubleAttribute("Thickness") / MaximumX));
	}
}
