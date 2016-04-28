package flagmaker.Overlays.OverlayTypes.ShapeTypes;

import flagmaker.Extensions.ColorExtensions;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class OverlayBox extends OverlayShape
{
	public OverlayBox(int maximumX, int maximumY)
	{
		super("box", maximumX, maximumY);
	}

	public OverlayBox(Color color, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super("box", color, x, y, width, height, maximumX, maximumY);
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
		double width = canvas.getWidth() * (GetDoubleAttribute("Width") / (double)MaximumX);
		double height = GetDoubleAttribute("Height") == 0
				? width
				: canvas.getHeight() * (GetDoubleAttribute("Height") / MaximumY);
		double left = canvas.getWidth() * (GetDoubleAttribute("X") / MaximumX);
		double top = canvas.getHeight() * (GetDoubleAttribute("Y") / MaximumY);
		Rectangle rect = new Rectangle(left, top, width, height);
		rect.setFill(GetColorAttribute("Color"));
		canvas.getChildren().add(rect);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double w = width * (GetDoubleAttribute("Width") / (double)MaximumX);
		double h = GetDoubleAttribute("Height") == 0
				? w
				: height * (GetDoubleAttribute("Height") / MaximumY);
		return String.format("<rect width=\"%.3f\" height=\"%.3f\" x=\"%.3f\" y=\"%.3f\" %s />",
				w,
				h,
				width * (GetDoubleAttribute("X") / MaximumX),
				height * (GetDoubleAttribute("Y") / MaximumY),
				ColorExtensions.ToSvgFillWithOpacity(GetColorAttribute("Color")));
	}
}
