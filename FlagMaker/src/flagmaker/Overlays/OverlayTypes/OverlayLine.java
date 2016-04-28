package flagmaker.Overlays.OverlayTypes;

import flagmaker.Extensions.ColorExtensions;
import flagmaker.Overlays.Attributes.*;
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
			new ColorAttribute("Color", Color.BLACK),
			new DoubleAttribute("X1", 1, maximumX, true),
			new DoubleAttribute("Y1", 1, maximumY, false),
			new DoubleAttribute("X2", 2, maximumX, true),
			new DoubleAttribute("Y2", 2, maximumY, false),
			new DoubleAttribute("Thickness", 0.5, maximumX, true)
		}, maximumX, maximumY);
	}

	public OverlayLine(Color color, double x1, double y1, double x2, double y2, double thickness, int maximumX, int maximumY)
	{
		super("line", new Attribute[]
		{
			new ColorAttribute("Color", color),
			new DoubleAttribute("X1", x1, maximumX, true),
			new DoubleAttribute("Y1", y1, maximumY, false),
			new DoubleAttribute("X2", x2, maximumX, true),
			new DoubleAttribute("Y2", y2, maximumY, false),
			new DoubleAttribute("Thickness", thickness, maximumX, true)
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
				canvas.getWidth() * GetDoubleAttribute("X1") / MaximumX,
				canvas.getHeight() * GetDoubleAttribute("Y1") / MaximumY,
				canvas.getWidth() * GetDoubleAttribute("X2") / MaximumX,
				canvas.getHeight() * GetDoubleAttribute("Y2") / MaximumY);
		line.setStrokeWidth(canvas.getWidth() * (GetDoubleAttribute("Thickness") / MaximumX));
		line.setStroke(GetColorAttribute("Color"));
		canvas.getChildren().add(line);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		return String.format("<line x1=\"%.3f\" y1=\"%.3f\" x2=\"%.3f\" y2=\"%.3f\" stroke=\"#%s\" stroke-width=\"%.3f\" />",
			width * GetDoubleAttribute("X1") / MaximumX,
			height * GetDoubleAttribute("Y1") / MaximumY,
			width * GetDoubleAttribute("X2") / MaximumX,
			height * GetDoubleAttribute("Y2") / MaximumY,
			ColorExtensions.ToHexString(GetColorAttribute("Color"), false),
			width * (GetDoubleAttribute("Thickness") / MaximumX));
	}
}
