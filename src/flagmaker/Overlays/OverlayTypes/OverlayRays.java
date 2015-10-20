package flagmaker.Overlays.OverlayTypes;

import flagmaker.Extensions.ColorExtensions;
import flagmaker.Overlays.Attribute;
import flagmaker.Overlays.Overlay;
import flagmaker.Data.Vector;
import java.util.ArrayList;
import java.util.Comparator;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

public class OverlayRays extends Overlay
{
	public OverlayRays(int maximumX, int maximumY)
	{
		super("rays", new Attribute[]
		{
			new Attribute("X", true, 1, true),
			new Attribute("Y", true, 1, false),
			new Attribute("Count", true, 4, true),
			new Attribute("Rotation", true, 0, true),
		}, maximumX, maximumY);
	}
	
	public OverlayRays(Color color, double x, double y, double count, double rotation, int maximumX, int maximumY)
	{
		super("rays", color, new Attribute[]
		{
			new Attribute("X", true, x, true),
			new Attribute("Y", true, y, false),
			new Attribute("Count", true, count, true),
			new Attribute("Rotation", true, rotation, true),
		}, maximumX, maximumY);
	}

	@Override
	protected Shape[] Thumbnail()
	{
		SVGPath path = new SVGPath();
		path.setContent("M 15,10 18,0 12,0 Z M 15,10 0,8 0,12 Z M 15,10 18,20 12,20 Z M 15,10 30,8 30,12 Z" +
						"M 15,10 6,0 0,0 0,3 Z M 15,10 24,0 30,0 30,3 Z M 15,10 24,20 30,20 30,17 Z M 15,10 6,20 0,20 0,17 Z");
		path.setLayoutY(5);
		return new Shape[] { path };
	}

	@Override
	public void Draw(Pane canvas)
	{
		for (String path : GetPaths(canvas.getWidth(), canvas.getHeight()))
		{
			SVGPath p = new SVGPath();
			p.setContent(path);
			p.setFill(Color);
			canvas.getChildren().add(p);
		}
	}

	@Override
	public void SetValues(double[] values)
	{
		SetAttribute("X", values[0]);
		SetAttribute("Y", values[1]);
		SetAttribute("Count", values[2]);
		SetAttribute("Rotation", values[3]);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		StringBuilder sb = new StringBuilder();

		for (String path : GetPaths(width, height))
		{
			sb.append(String.format("<path d=\"%s\" %s />",
				path, ColorExtensions.ToSvgFillWithOpacity(Color)));
		}

		return sb.toString();
	}
	
	private String[] GetPaths(double width, double height)
	{
		double centerX = width * (GetAttribute("X").Value / MaximumX);
		double centerY = height * (GetAttribute("Y").Value / MaximumY);
		double count = (int)GetAttribute("Count").Value;
		double rotation = GetAttribute("Rotation").Value / MaximumX;
		double rotationOffset = rotation * Math.PI * 2 / count;
		double angularInterval = Math.PI / count;
		
		ArrayList<String> returnValue = new ArrayList<>();

		for (int i = 0; i < count; i++)
		{
			Vector point1 = BorderIntersection(centerX, centerY, angularInterval * 2 * i + rotationOffset, width, height);
			Vector point2 = BorderIntersection(centerX, centerY, angularInterval * (2 * i + 1) + rotationOffset, width, height);

			// If points lie on different sides, add corner
			String point3 = "";
			if (point1.X != point2.X && point1.Y != point2.Y)
			{
				if (point1.Y == 0)
				{
					point3 = "0,0 ";
				}
				else if (point1.X == 0)
				{
					point3 = String.format("0,%.3f ", height);
				}
				else if (point1.Y == height)
				{
					point3 = String.format("%.3f,%.3f ", width, height);
				}
				else if (point1.X == width)
				{
					point3 = String.format("%.3f,0 ", width);
				}
			}

			returnValue.add(String.format("M %.3f,%.3f %.3f,%.3f %s%.3f,%.3f Z",
				centerX, centerY,
				point1.X, point1.Y,
				point3,
				point2.X, point2.Y));
		}
		
		String[] rv = new String[]{};
		return returnValue.toArray(rv);
	}

	private static Vector BorderIntersection(double centerX, double centerY, double angle, double width, double height)
	{
		ArrayList<Vector> possiblePoints = new ArrayList<>();

		if (angle > 0 && angle < Math.PI)
		{
			// Check intersection with top border
			double tX = centerY / Math.tan(angle);
			possiblePoints.add(new Vector(centerX + tX, 0));
		}
		if (angle > Math.PI / 2 && angle < 3 * Math.PI / 2)
		{
			// Check intersection with left border
			double tY = centerX * Math.tan(2 * Math.PI - angle);
			possiblePoints.add(new Vector(0, centerY - tY));
		}
		if (angle > Math.PI && angle < 2 * Math.PI)
		{
			// Check intersection with bottom border
			double tX = Math.tan(3 * Math.PI / 2 - angle) * (height - centerY);
			possiblePoints.add(new Vector(centerX - tX, height));
		}
		if (angle < Math.PI / 2)
		{
			// Check intersection with right border
			double tY = Math.tan(angle) * (width - centerX);
			possiblePoints.add(new Vector(width, centerY - tY));
		}
		if (angle > 3 * Math.PI / 2)
		{
			// Check intersection with right border
			double tY = Math.tan(2 * Math.PI - angle) * (width - centerX);
			possiblePoints.add(new Vector(width, centerY + tY));
		}
		
		possiblePoints.sort(new Comparator<Vector>()
		{
			@Override
			public int compare(Vector o1, Vector o2)
			{
				Double l1 = Length(o1, new Vector(centerX, centerY));
				Double l2 = Length(o2, new Vector(centerX, centerY));
				return l1.compareTo(l2);
			}
		});

		return possiblePoints.size() > 0
			? possiblePoints.get(0)
			: new Vector(centerX, centerY);
	}

	private static double Length(Vector p1, Vector p2)
	{
		return Math.sqrt(Math.pow(p2.X - p1.X, 2) + Math.pow(p2.Y - p1.Y, 2));
	}
}
