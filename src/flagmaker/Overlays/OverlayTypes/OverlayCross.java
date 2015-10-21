package flagmaker.Overlays.OverlayTypes;

import flagmaker.ColorExtensions;
import flagmaker.Overlays.Attributes.*;
import flagmaker.Overlays.Overlay;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class OverlayCross extends Overlay
{
	public OverlayCross(int maximumX, int maximumY)
	{
		super("cross", new Attribute[]
		{
			new ColorAttribute("Color", Color.BLACK),
			new DoubleAttribute("X", 1, maximumX, true),
			new DoubleAttribute("Y", 1, maximumY, false),
			new DoubleAttribute("Thickness", 1, maximumX, true)
		}, maximumX, maximumY);
	}

	public OverlayCross(Color color, double thickness, double x, double y, int maximumX, int maximumY)
	{
		super("cross", new Attribute[]
		{
			new ColorAttribute("Color", color),
			new DoubleAttribute("X", x, maximumX, true),
			new DoubleAttribute("Y", y, maximumY, false),
			new DoubleAttribute("Thickness", thickness, maximumX, true)
		}, maximumX, maximumY);
	}

	@Override
	protected Shape[] Thumbnail()
	{
		Line l1 = new Line(10, 5, 10, 25);
		Line l2 = new Line(0, 15, 30, 15);
		
		l1.setStrokeWidth(5);
		l2.setStrokeWidth(5);
		
		return new Shape[]
		{
			l1,
			l2
		};
	}

	@Override
	public void Draw(Pane canvas)
	{
		double thick = canvas.getWidth() * GetDoubleAttribute("Thickness") / MaximumX;
		Rectangle vertical = new Rectangle(canvas.getWidth() * (GetDoubleAttribute("X") / MaximumX) - thick / 2, 0, thick, canvas.getHeight());
		Rectangle horizontal = new Rectangle(0, canvas.getHeight() * (GetDoubleAttribute("Y") / MaximumY) - thick / 2, canvas.getWidth(), thick);
		vertical.setFill(GetColorAttribute("Color"));
		horizontal.setFill(GetColorAttribute("Color"));
		canvas.getChildren().addAll(vertical, horizontal);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double thick = width * GetDoubleAttribute("Thickness") / MaximumX;
		double x = width * (GetDoubleAttribute("X") / MaximumX) - thick / 2;
		double y = height * (GetDoubleAttribute("Y") / MaximumY) - thick / 2;
			
		return String.format("<rect width=\"%1$.3f\" height=\"%2$d\" x=\"%3$.3f\" y=\"0\" %6$s /><rect width=\"%4$d\" height=\"%1$.3f\" x=\"0\" y=\"%5$.3f\" %6$s />",
				thick, height, x, width, y, ColorExtensions.ToSvgFillWithOpacity(GetColorAttribute("Color")));
	}
}
