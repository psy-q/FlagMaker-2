package flagmaker.Overlays.OverlayTypes.RepeaterTypes;

import flagmaker.Overlays.Attributes.*;
import java.util.UUID;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class OverlayRepeaterRadial extends OverlayRepeater
{
	public OverlayRepeaterRadial(int maximumX, int maximumY)
	{
		super("repeater radial", new Attribute[]
		{
			new DoubleAttribute("X", 1, maximumX, true),
			new DoubleAttribute("Y", 1, maximumY, false),
			new DoubleAttribute("Radius", 1, maximumX, true),
			new IntegerAttribute("Count", 1, maximumX, true),
			new BooleanAttribute("Rotate", true)
		}, maximumX, maximumY);
	}
	
	public OverlayRepeaterRadial(double x, double y, double radius, int count, boolean rotate, int maximumX, int maximumY)
	{
		super("repeater radial", new Attribute[]
		{
			new DoubleAttribute("X", x, maximumX, true),
			new DoubleAttribute("Y", y, maximumY, false),
			new DoubleAttribute("Radius", radius, maximumX, true),
			new IntegerAttribute("Count", count, maximumX, true),
			new BooleanAttribute("Rotate", rotate)
		}, maximumX, maximumY);
	}

	@Override
	protected Shape[] Thumbnail()
	{
		final int count = 7;
		final int radius = 10;
		final double interval = 2 * Math.PI / count;
		Shape[] shapes = new Shape[count];

		for (int i = 0; i < count; i++)
		{
			double left = Math.cos(i * interval) * radius + 15;
			double top = Math.sin(i * interval) * radius + 15;
			shapes[i] = new Ellipse(left, top, 2.5, 2.5);
		}

		return shapes;
	}

	@Override
	public void Draw(Pane canvas)
	{
		if (Overlay == null || !Overlay.IsEnabled) return;

		double locX = canvas.getWidth() * (GetDoubleAttribute("X") / MaximumX);
		double locY = canvas.getHeight() * (GetDoubleAttribute("Y") / MaximumY);
		double radius = canvas.getWidth() * (GetDoubleAttribute("Radius") / MaximumX);
		double interval = 2 * Math.PI / GetIntegerAttribute("Count");
		boolean rotate = GetBooleanAttribute("Rotate");

		for (int i = 0; i < GetIntegerAttribute("Count"); i++)
		{
			AnchorPane a = new AnchorPane();
			a.setBackground(Background.EMPTY);
			Scene s = new Scene(a, radius, radius);
			Pane p = new Pane();
			p.setBackground(Background.EMPTY);
			s.setRoot(p);
			
			double x = locX + Math.cos(i * interval - Math.PI / 2) * radius;
			double y = locY + Math.sin(i * interval - Math.PI / 2) * radius;
			
			if (rotate)
			{
				p.getTransforms().add(new Rotate(i * 360 / GetIntegerAttribute("Count"), 0, 0));
			}

			Overlay.Draw(p);
			canvas.getChildren().add(p);

			p.setLayoutX(x);
			p.setLayoutY(y);
		}
	}

	@Override
	public void SetValues(Object[] values)
	{
		SetAttribute("X", values[0]);
		SetAttribute("Y", values[1]);
		SetAttribute("Radius", values[2]);
		SetAttribute("Count", values[3]);
		SetAttribute("Rotate", values[4]);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		if (Overlay == null) return "";
		if (!Overlay.IsEnabled) return "";

		double locX = width * (GetDoubleAttribute("X") / MaximumX);
		double locY = height * (GetDoubleAttribute("Y") / MaximumY);
		double radius = width * (GetDoubleAttribute("Radius") / MaximumX);
		double interval = 2 * Math.PI / GetIntegerAttribute("Count");
		boolean rotate = GetBooleanAttribute("Rotate");

		UUID id = UUID.randomUUID();
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("<defs><g id=\"%s\">%s</g></defs>",
			id.toString(), Overlay.ExportSvg((int)radius, (int)radius)));

		for (int i = 0; i < GetIntegerAttribute("Count"); i++)
		{
			sb.append(String.format("<g transform=\"translate(%.3f,%.3f)%s\">\n",
				locX + Math.cos(i * interval - Math.PI / 2) * radius,
				locY + Math.sin(i * interval - Math.PI / 2) * radius,
				rotate ? String.format("rotate(%.3f)", i * 360.0 / GetIntegerAttribute("Count")) : ""));
			sb.append(String.format("<use xlink:href=\"#%s\" />\n", id.toString()));
			sb.append("</g>\n");
		}

		return sb.toString();
	}
}
