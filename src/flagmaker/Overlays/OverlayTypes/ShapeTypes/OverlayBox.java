package flagmaker.Overlays.OverlayTypes.ShapeTypes;

import flagmaker.ColorExtensions;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class OverlayBox extends OverlayShape
{
	public OverlayBox(int maximumX, int maximumY)
	{
		super(maximumX, maximumY);
	}

	public OverlayBox(Color color, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super(color, x, y, width, height, maximumX, maximumY);
	}

	@Override
	public String Name()
	{
		return "box";
	}

	@Override
	protected Shape[] Thumbnail()
	{
		return new Shape[]
		{
			new Rectangle(5, 7.5, 20, 15)
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
		Rectangle rect = new Rectangle(left, top, width, height);
		rect.setFill(Color);
		canvas.getChildren().add(rect);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double w = width * (GetAttribute("Width").Value / (double)MaximumX);
		double h = GetAttribute("Height").Value == 0
				? w
				: height * (GetAttribute("Height").Value / MaximumY);
		return String.format("<rect width=\"%.3f\" height=\"%.3f\" x=\"%.3f\" y=\"%.3f\" %s />",
				w,
				h,
				width * (GetAttribute("X").Value / MaximumX),
				height * (GetAttribute("Y").Value / MaximumY),
				ColorExtensions.ToSvgFillWithOpacity(Color));
	}
}
