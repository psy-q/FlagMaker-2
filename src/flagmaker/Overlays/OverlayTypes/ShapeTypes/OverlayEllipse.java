package flagmaker.Overlays.OverlayTypes.ShapeTypes;

import flagmaker.ColorExtensions;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class OverlayEllipse extends OverlayShape
{
	public OverlayEllipse(int maximumX, int maximumY)
	{
		super("ellipse", maximumX, maximumY);
	}

	public OverlayEllipse(Color color, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super("ellipse", color, x, y, width, height, maximumX, maximumY);
	}

	@Override
	protected Shape[] Thumbnail()
	{
		return new Shape[]
		{
			new Ellipse(15, 15, 15, 10)
		};
	}

	@Override
	public void Draw(Pane canvas)
	{
		double width = canvas.getWidth() * (GetAttribute("Width").Value / (double)MaximumX);
		double height = GetAttribute("Height").Value == 0
				? width
				: canvas.getHeight() * (GetAttribute("Height").Value / MaximumY);
		double left = canvas.getWidth() * (GetAttribute("X").Value / MaximumX);
		double top = canvas.getHeight() * (GetAttribute("Y").Value / MaximumY);
		Ellipse ellipse = new Ellipse(left, top, width / 2, height / 2);
		ellipse.setFill(Color);
		canvas.getChildren().add(ellipse);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double w = width * (GetAttribute("Width").Value / (double)MaximumX);
		double h = GetAttribute("Height").Value == 0
				? w
				: height * (GetAttribute("Height").Value / MaximumY);
		
		double x = width * (GetAttribute("X").Value / MaximumX);
		double y = height * (GetAttribute("Y").Value / MaximumY);
		
		return String.format("<ellipse cx=\"%.3f\" cy=\"%.3f\" rx=\"%.3f\" ry=\"%.3f\" %s />",
				x, y, w / 2, h / 2,
				ColorExtensions.ToSvgFillWithOpacity(Color));
	}
}
