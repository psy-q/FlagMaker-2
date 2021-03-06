package flagmaker.Overlays.OverlayTypes.ShapeTypes;

import flagmaker.Extensions.ColorExtensions;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Shape;

public class OverlayDiamond extends OverlayShape
{
	public OverlayDiamond(int maximumX, int maximumY)
	{
		super("diamond", maximumX, maximumY);
	}

	public OverlayDiamond(Color color, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super("diamond", color, x, y, width, height, maximumX, maximumY);
	}

	@Override
	protected Shape[] Thumbnail()
	{
		return new Shape[]
		{
			new Path(new PathElement[]
			{
				new MoveTo(0, 15),
				new LineTo(15, 0),
				new LineTo(30, 15),
				new LineTo(15, 30),
				new LineTo(0, 15)
			})
		};
	}

	@Override
	public void Draw(Pane canvas)
	{
		double width = canvas.getWidth() * (GetDoubleAttribute("Width") / (double) MaximumX);
		double height = GetDoubleAttribute("Height") == 0
				? width
				: canvas.getHeight() * (GetDoubleAttribute("Height") / MaximumY);
		double left = canvas.getWidth() * (GetDoubleAttribute("X") / MaximumX) - width / 2;
		double top = canvas.getHeight() * (GetDoubleAttribute("Y") / MaximumY) - height / 2;
		Path path = new Path(new PathElement[]
		{
			new MoveTo(0, height / 2),
			new LineTo(width / 2, 0),
			new LineTo(width, height / 2),
			new LineTo(width / 2, height),
			new LineTo(0, height / 2)
		});
		path.setFill(GetColorAttribute("Color"));
		path.setStrokeWidth(0);
		path.setLayoutX(left);
		path.setLayoutY(top);
		canvas.getChildren().add(path);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double w = width * (GetDoubleAttribute("Width") / (double) MaximumX);
		double h = GetDoubleAttribute("Height") == 0
				? w
				: height * (GetDoubleAttribute("Height") / MaximumY);
		
		double x = width * (GetDoubleAttribute("X") / MaximumX);
		double y = height * (GetDoubleAttribute("Y") / MaximumY);
		
		return String.format("<polygon points=\"%1$.3f,%2$.3f %3$.3f,%4$.3f %1$.3f,%5$.3f %6$.3f,%4$.3f\" %7$s />",
				x, y - h / 2, x + w / 2, y, y + h / 2, x - w / 2,
				ColorExtensions.ToSvgFillWithOpacity(GetColorAttribute("Color")));
	}
}
