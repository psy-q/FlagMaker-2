package flagmaker.Overlays.OverlayTypes;

import flagmaker.ColorExtensions;
import flagmaker.Overlays.Attributes.*;
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
		super("checkerboard", new Attribute[]
		{
			new DoubleAttribute("X", 1, maximumX, true),
			new DoubleAttribute("Y", 1, maximumY, false),
			new DoubleAttribute("Width", 1, maximumX, true),
			new DoubleAttribute("Height", 1, maximumY, false),
			new IntegerAttribute("CountX", 4, maximumX, true),
			new IntegerAttribute("CountY", 4, maximumX, true)
		}, maximumX, maximumY);
	}

	public OverlayCheckerboard(Color color, double x, double y, double width, double height, int countX, int countY, int maximumX, int maximumY)
	{
		super("checkerboard", color, new Attribute[]
		{
			new DoubleAttribute("X", x, maximumX, true),
			new DoubleAttribute("Y", y, maximumY, false),
			new DoubleAttribute("Width", width, maximumX, true),
			new DoubleAttribute("Height", height, maximumY, false),
			new IntegerAttribute("CountX", countX, maximumX, true),
			new IntegerAttribute("CountY", countY, maximumX, true)
		}, maximumX, maximumY);
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
		double centerX = canvas.getWidth() * (GetDoubleAttribute("X") / MaximumX);
		double centerY = canvas.getHeight() * (GetDoubleAttribute("Y") / MaximumY);
		double width = canvas.getWidth() * (GetDoubleAttribute("Width") / MaximumX);
		double height = canvas.getHeight() * (GetDoubleAttribute("Height") / MaximumY);
		if (height == 0) height = width;
		int countX = GetIntegerAttribute("CountX");
		int countY = GetIntegerAttribute("CountY");

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
	public void SetValues(Object[] values)
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
		double centerX = width * (GetDoubleAttribute("X") / MaximumX);
		double centerY = height * (GetDoubleAttribute("Y") / MaximumY);
		double w = width * (GetDoubleAttribute("Width") / MaximumX);
		double h = height * (GetDoubleAttribute("Height") / MaximumY);
		if (h == 0) h = w;
		int countX = GetIntegerAttribute("CountX");
		int countY = GetIntegerAttribute("CountY");

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
