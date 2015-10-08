package flagmaker.Overlays.OverlayTypes;

import flagmaker.Overlays.Attribute;
import flagmaker.Overlays.Overlay;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class OverlayBorder extends Overlay
{
	public OverlayBorder(int maximumX, int maximumY)
	{
		super(new Attribute[]
		{
			new Attribute("Thickness", true, 1, true)
		}, maximumX, maximumY);
	}

	public OverlayBorder(Color color, double thickness, int maximumX, int maximumY)
	{
		super(color, new Attribute[]
		{
			new Attribute("Thickness", true, 1, true)
		}, maximumX, maximumY);
	}

	@Override
	public String Name()
	{
		return "border";
	}

	@Override
	protected Shape[] Thumbnail()
	{
		return new Shape[]
		{
			new Path(new PathElement[]
			{
				new MoveTo(0, 5),
				new LineTo(30, 5),
				new LineTo(30, 25),
				new LineTo(0, 25),
				new LineTo(0, 5),
				new MoveTo(5, 10),
				new LineTo(5, 20),
				new LineTo(25, 20),
				new LineTo(25, 10),
				new LineTo(5, 10)
			})
		};
	}

	@Override
	public void Draw(Pane canvas)
	{
		double thickness = canvas.getWidth() * (GetAttribute("Thickness").Value / MaximumX) / 2;

		// Prevent the border from overlapping itself
		if (canvas.getWidth() - thickness * 2 < 0)
		{
			thickness = canvas.getWidth() / 2;
		}

		if (canvas.getHeight() - thickness * 2 < 0)
		{
			thickness = canvas.getHeight() / 2;
		}

		Path path = new Path(new PathElement[]
		{
			new MoveTo(0, 0),
			new LineTo(canvas.getWidth(), 0),
			new LineTo(canvas.getWidth(), canvas.getHeight()),
			new LineTo(0, canvas.getHeight()),
			new LineTo(0, 0),
			new MoveTo(thickness, thickness),
			new LineTo(thickness, canvas.getHeight() - thickness),
			new LineTo(canvas.getWidth() - thickness, canvas.getHeight() - thickness),
			new LineTo(canvas.getWidth() - thickness, thickness),
			new LineTo(thickness, thickness)
		});
		path.setFill(Color);
		path.setStrokeWidth(0);

		canvas.getChildren().add(path);
	}

	@Override
	public void SetValues(Double[] values)
	{
		SetAttribute("Thickness", values[0]);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		return "";
	}
}
