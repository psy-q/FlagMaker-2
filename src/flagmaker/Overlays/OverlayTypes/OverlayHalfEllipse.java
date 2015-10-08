package flagmaker.Overlays.OverlayTypes;

import flagmaker.ColorExtensions;
import flagmaker.Overlays.Attribute;
import flagmaker.Overlays.Overlay;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Shape;

public class OverlayHalfEllipse extends Overlay
{
	public OverlayHalfEllipse(int maximumX, int maximumY)
	{
		super(new Attribute[]
		{
			new Attribute("X", true, 1, true),
			new Attribute("Y", true, 1, false),
			new Attribute("Width", true, 1, true),
			new Attribute("Height", true, 1, false),
			new Attribute("Rotation", true, 0, true)
		}, maximumX, maximumY);
	}

	public OverlayHalfEllipse(Color color, double x, double y, double width, double height, double rotation, int maximumX, int maximumY)
	{
		super(color, new Attribute[]
		{
			new Attribute("X", true, x, true),
			new Attribute("Y", true, y, false),
			new Attribute("Width", true, width, true),
			new Attribute("Height", true, height, false),
			new Attribute("Rotation", true, rotation, true)
		}, maximumX, maximumY);
	}

	@Override
	public String Name()
	{
		return "half ellipse";
	}

	@Override
	protected Shape[] Thumbnail()
	{
		return new Shape[]
		{
			new Path(new PathElement[]
			{
				new MoveTo(0, 20),
				new ArcTo(15, 15, 0, 30, 20, false, true),
				new LineTo(0, 20)
			})
		};
	}

	@Override
	public void Draw(Pane canvas)
	{
		double x = canvas.getWidth() * GetAttribute("X").Value / MaximumX;
		double y = canvas.getHeight() * GetAttribute("Y").Value / MaximumY;
		double radX = canvas.getWidth() * (GetAttribute("Width").Value / MaximumX) / 2;
		double radY = canvas.getHeight() * GetAttribute("Height").Value / MaximumY;

		double angle = 2 * Math.PI * GetAttribute("Rotation").Value / MaximumX;
		double xOffset = radX - radX * Math.cos(angle);
		double yOffset = radX * Math.sin(angle);

		double x1 = x - radX + xOffset;
		double x2 = x + radX - xOffset;
		double y1 = y - yOffset;
		double y2 = y + yOffset;
			
		Path path = new Path(new PathElement[]
		{
			new MoveTo(x1, y1),
			new ArcTo(radX, radY, angle * 180 / Math.PI, x2, y2, true, true),
			new LineTo(x1, y1)
		});
		path.setFill(Color);
		path.setStrokeWidth(0);
		canvas.getChildren().add(path);
	}

	@Override
	public void SetValues(Double[] values)
	{
		SetAttribute("X", values[0]);
		SetAttribute("Y", values[1]);
		SetAttribute("Width", values[2]);
		SetAttribute("Height", values[3]);
		SetAttribute("Rotation", values[4]);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double x = width * GetAttribute("X").Value / MaximumX;
		double y = height * GetAttribute("Y").Value / MaximumY;
		double radX = width * (GetAttribute("Width").Value / MaximumX) / 2;
		double radY = height * GetAttribute("Height").Value / MaximumY;

		double angle = 2 * Math.PI * GetAttribute("Rotation").Value / MaximumX;
		double xOffset = radX - radX * Math.cos(angle);
		double yOffset = radX * Math.sin(angle);

		double x1 = x - radX + xOffset;
		double x2 = x + radX - xOffset;
		double y1 = y - yOffset;
		double y2 = y + yOffset;
		
		return String.format("<path d=\"M %.3f,%.3f A %.3f,%.3f %.3f 1,1 %.3f,%.3f z\" %s />",
				x1, y1, radX, radY, angle * 180 / Math.PI, x2, y2,
				ColorExtensions.ToSvgFillWithOpacity(Color));
	}
}
