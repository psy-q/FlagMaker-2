package flagmaker.Overlays.OverlayTypes;

import flagmaker.Extensions.ColorExtensions;
import flagmaker.Overlays.Attributes.*;
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
			new ColorAttribute("Color", Color.BLACK),
			new DoubleAttribute("Y", 1, maximumY, false),
			new DoubleAttribute("Thickness", 0.5, maximumY, false)
		}, maximumX, maximumY);
	}

	public OverlayLineHorizontal(Color color, double y, double thickness, int maximumX, int maximumY)
	{
		super("line horizontal", new Attribute[]
		{
			new ColorAttribute("Color", color),
			new DoubleAttribute("Y", y, maximumY, false),
			new DoubleAttribute("Thickness", thickness, maximumY, false)
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
				canvas.getHeight() * GetDoubleAttribute("Y") / MaximumY,
				canvas.getWidth(),
				canvas.getHeight() * GetDoubleAttribute("Y") / MaximumY);
		line.setStrokeWidth(canvas.getHeight() * (GetDoubleAttribute("Thickness") / MaximumY));
		line.setStroke(GetColorAttribute("Color"));
		canvas.getChildren().add(line);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		return String.format("<line x1=\"0\" y1=\"%.3f\" x2=\"%d\" y2=\"%.3f\" stroke=\"#%s\" stroke-width=\"%.3f\" />",
			height * GetDoubleAttribute("Y") / MaximumY,
			width,
			height * GetDoubleAttribute("Y") / MaximumY,
			ColorExtensions.ToHexString(GetColorAttribute("Color"), false),
			height * (GetDoubleAttribute("Thickness") / MaximumY));
	}
}
