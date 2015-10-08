package flagmaker.Overlays.OverlayTypes.ShapeTypes;

import flagmaker.ColorExtensions;
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
		super(maximumX, maximumY);
	}

	public OverlayDiamond(Color color, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super(color, x, y, width, height, maximumX, maximumY);
	}

	@Override
	public String Name()
	{
		return "diamond";
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
		double width = canvas.getWidth() * (GetAttribute("Width").Value / (double) MaximumX);
		double height = GetAttribute("Height").Value == 0
				? width
				: canvas.getHeight() * (GetAttribute("Height").Value / MaximumY);
		double left = canvas.getWidth() * (GetAttribute("X").Value / MaximumX) - width / 2;
		double top = canvas.getHeight() * (GetAttribute("Y").Value / MaximumY) - height / 2;
		Path path = new Path(new PathElement[]
		{
			new MoveTo(0, height / 2),
			new LineTo(width / 2, 0),
			new LineTo(width, height / 2),
			new LineTo(width / 2, height),
			new LineTo(0, height / 2)
		});
		path.setFill(Color);
		path.setLayoutX(left);
		path.setLayoutY(top);
		canvas.getChildren().add(path);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double w = width * (GetAttribute("Width").Value / (double) MaximumX);
		double h = GetAttribute("Height").Value == 0
				? w
				: height * (GetAttribute("Height").Value / MaximumY);
		
		double x = width * (GetAttribute("X").Value / MaximumX);
		double y = height * (GetAttribute("Y").Value / MaximumY);
		
		return String.format("<polygon points=\"%1$.3f,%2$.3f %3$.3f,%4$.3f %1$.3f,%5$.3f %6$.3f,%4$.3f\" %7$s />",
				x, y - h / 2, x + w / 2, y, y + h / 2, x - w / 2,
				ColorExtensions.ToSvgFillWithOpacity(Color));
	}
}
