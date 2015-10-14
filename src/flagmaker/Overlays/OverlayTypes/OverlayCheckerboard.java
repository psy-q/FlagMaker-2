package flagmaker.Overlays.OverlayTypes;

import flagmaker.ColorExtensions;
import flagmaker.Overlays.Attribute;
import flagmaker.Overlays.Overlay;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class OverlayCheckerboard extends Overlay
{
	public OverlayCheckerboard(int maximumX, int maximumY)
	{
		super(new Attribute[]
		{
			new Attribute("X", true, 1, true),
			new Attribute("Y", true, 1, false),
			new Attribute("Width", true, 1, true),
			new Attribute("Height", true, 1, false),
			new Attribute("CountX", true, 4, true),
			new Attribute("CountY", true, 4, true)
		}, maximumX, maximumY);
	}

	public OverlayCheckerboard(Color color, double x, double y, double width, double height, double countX, double countY, int maximumX, int maximumY)
	{
		super(color, new Attribute[]
		{
			new Attribute("X", true, x, true),
			new Attribute("Y", true, y, false),
			new Attribute("Width", true, width, true),
			new Attribute("Height", true, height, false),
			new Attribute("CountX", true, countX, true),
			new Attribute("CountY", true, countY, true)
		}, maximumX, maximumY);
	}

	@Override
	public String Name()
	{
		return "checkerboard";
	}

	@Override
	protected Shape[] Thumbnail()
	{
		ArrayList<Shape> shapes = new ArrayList<>();

		for (int x = 0; x < 5; x++)
		{
			for (int y = 0; y < 5; y++)
			{
				if ((x + y) % 2 == 0)
				{
					shapes.add(new Rectangle(2.5 + x * 5, 2.5 + y * 5, 5, 5));
				}
			}
		}

		Shape[] returnValue = new Shape[]
		{
		};
		return shapes.toArray(returnValue);
	}

	@Override
	public void Draw(Pane canvas)
	{
		double centerX = canvas.getWidth() * (GetAttribute("X").Value / MaximumX);
		double centerY = canvas.getHeight() * (GetAttribute("Y").Value / MaximumY);
		double width = canvas.getWidth() * (GetAttribute("Width").Value / MaximumX);
		double height = canvas.getHeight() * (GetAttribute("Height").Value / MaximumY);
		if (height == 0) height = width;
		double countX = GetAttribute("CountX").Value;
		double countY = GetAttribute("CountY").Value;

		double left = centerX - width / 2;
		double top = centerY - height / 2;
		double blockWidth = width / countX;
		double blockHeight = height / countY;

		for (int x = 0; x < countX; x++)
		{
			for (int y = 0; y < countY; y++)
			{
				if ((x + y) % 2 != 0) continue;

				Rectangle rect = new Rectangle(left + x * blockWidth, top + y * blockHeight, blockWidth, blockHeight);
				rect.setFill(Color);
				canvas.getChildren().add(rect);
			}
		}
	}

	@Override
	public void SetValues(double[] values)
	{
		SetAttribute("X", values[0]);
		SetAttribute("Y", values[1]);
		SetAttribute("Width", values[2]);
		SetAttribute("Height", values[3]);
		SetAttribute("CountX", values[4]);
		SetAttribute("CountY", values[5]);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double centerX = width * (GetAttribute("X").Value / MaximumX);
		double centerY = height * (GetAttribute("Y").Value / MaximumY);
		double w = width * (GetAttribute("Width").Value / MaximumX);
		double h = height * (GetAttribute("Height").Value / MaximumY);
		if (h == 0) h = w;
		double countX = GetAttribute("CountX").Value;
		double countY = GetAttribute("CountY").Value;

		double left = centerX - w / 2;
		double top = centerY - h / 2;
		double blockWidth = w / countX;
		double blockHeight = h / countY;

		StringBuilder sb = new StringBuilder();

		for (int x = 0; x < countX; x++)
		{
			for (int y = 0; y < countY; y++)
			{
				if ((x + y) % 2 != 0) continue;

				sb.append(String.format("<rect width=\"%.3f\" height=\"%.3f\" %s x=\"%.3f\" y=\"%.3f\"/>",
						blockWidth, blockHeight,
						ColorExtensions.ToSvgFillWithOpacity(Color),
						left + x * blockWidth, top + y * blockHeight));
			}
		}

		return sb.toString();
	}
}
