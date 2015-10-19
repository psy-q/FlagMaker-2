package flagmaker.Overlays.OverlayTypes;

import flagmaker.ColorExtensions;
import flagmaker.Overlays.Attribute;
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
			new Attribute("X", true, 1, true),
			new Attribute("Y", true, 1, false),
			new Attribute("Thickness", true, 1, true)
		}, maximumX, maximumY);
	}

	public OverlayCross(Color color, double thickness, double x, double y, int maximumX, int maximumY)
	{
		super("cross", color, new Attribute[]
		{
			new Attribute("X", true, x, true),
			new Attribute("Y", true, y, false),
			new Attribute("Thickness", true, thickness, true)
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
		double thick = canvas.getWidth() * GetAttribute("Thickness").Value / MaximumX;
		Rectangle vertical = new Rectangle(canvas.getWidth() * (GetAttribute("X").Value / MaximumX) - thick / 2, 0, thick, canvas.getHeight());
		Rectangle horizontal = new Rectangle(0, canvas.getHeight() * (GetAttribute("Y").Value / MaximumY) - thick / 2, canvas.getWidth(), thick);
		vertical.setFill(Color);
		horizontal.setFill(Color);
		canvas.getChildren().addAll(vertical, horizontal);
	}

	@Override
	public void SetValues(double[] values)
	{
		SetAttribute("X", values[0]);
		SetAttribute("Y", values[1]);
		SetAttribute("Thickness", values[2]);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double thick = width * GetAttribute("Thickness").Value / MaximumX;
		double x = width * (GetAttribute("X").Value / MaximumX) - thick / 2;
		double y = height * (GetAttribute("Y").Value / MaximumY) - thick / 2;
			
		return String.format("<rect width=\"%1$.3f\" height=\"%2$d\" x=\"%3$.3f\" y=\"0\" %6$s /><rect width=\"%4$d\" height=\"%1$.3f\" x=\"0\" y=\"%5$.3f\" %6$s />",
				thick, height, x, width, y, ColorExtensions.ToSvgFillWithOpacity(Color));
	}
}
