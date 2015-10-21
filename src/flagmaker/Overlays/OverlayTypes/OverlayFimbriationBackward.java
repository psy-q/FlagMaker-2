package flagmaker.Overlays.OverlayTypes;

import flagmaker.Extensions.ColorExtensions;
import flagmaker.Overlays.Attributes.*;
import flagmaker.Overlays.Overlay;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Shape;

public class OverlayFimbriationBackward extends Overlay
{
	public OverlayFimbriationBackward(int maximumX, int maximumY)
	{
		super("fimbriation backward", new Attribute[]
		{
			new ColorAttribute("Color", Color.BLACK),
			new DoubleAttribute("Thickness", 1, maximumX, true)
		}, maximumX, maximumY);
	}

	public OverlayFimbriationBackward(Color color, double thickness, int maximumX, int maximumY)
	{
		super("fimbriation backward", new Attribute[]
		{
			new ColorAttribute("Color", color),
			new DoubleAttribute("Thickness", thickness, maximumX, true)
		}, maximumX, maximumY);
	}

	@Override
	protected Shape[] Thumbnail()
	{
		Line l = new Line(0, 5, 30, 25);
		l.setStrokeWidth(5);
		return new Shape[] { l };
	}

	@Override
	public void Draw(Pane canvas)
	{
		double widthX = canvas.getWidth() * (GetDoubleAttribute("Thickness") / MaximumX) / 2;
		double widthY = canvas.getHeight() * (GetDoubleAttribute("Thickness") / MaximumX) / 2;

		Path path = new Path(new PathElement[]
		{
			new MoveTo(widthX, 0),
			new LineTo(0, 0),
			new LineTo(0, widthY),
			new LineTo(canvas.getWidth() - widthX, canvas.getHeight()),
			new LineTo(canvas.getWidth(), canvas.getHeight()),
			new LineTo(canvas.getWidth(), canvas.getHeight() - widthY),
			new LineTo(widthX, 0)
		});
		path.setFill(GetColorAttribute("Color"));
		path.setStrokeWidth(0);
		canvas.getChildren().add(path);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double wX = width * (GetDoubleAttribute("Thickness") / MaximumX) / 2;
		double wY = height * (GetDoubleAttribute("Thickness") / MaximumX) / 2;

		return String.format("<polygon points=\"%1$.3f,0 0,0 0,%6$.3f %2$.3f,%3$d %4$d,%3$d %4$d,%5$.3f %1$.3f,0\" %7$s />",
			wX, width - wX, height, width, height - wY, wY,
			ColorExtensions.ToSvgFillWithOpacity(GetColorAttribute("Color")));
	}
}
