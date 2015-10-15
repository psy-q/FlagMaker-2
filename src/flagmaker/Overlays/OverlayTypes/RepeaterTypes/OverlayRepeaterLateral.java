package flagmaker.Overlays.OverlayTypes.RepeaterTypes;

import flagmaker.Overlays.Attribute;
import java.util.UUID;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class OverlayRepeaterLateral extends OverlayRepeater
{
	public OverlayRepeaterLateral(int maximumX, int maximumY)
	{
		super(new Attribute[]
		{
			new Attribute("X", true, 1, true),
			new Attribute("Y", true, 1, false),
			new Attribute("Width", true, 1, true),
			new Attribute("Height", true, 1, false),
			new Attribute("CountX", true, 1, true),
			new Attribute("CountY", true, 1, false)
		}, maximumX, maximumY);
	}
	
	public OverlayRepeaterLateral(double x, double y, double width, double height, int countX, int countY, int maximumX, int maximumY)
	{
		super(new Attribute[]
		{
			new Attribute("X", true, x, true),
			new Attribute("Y", true, y, false),
			new Attribute("Width", true, width, true),
			new Attribute("Height", true, height, false),
			new Attribute("CountX", true, countX, true),
			new Attribute("CountY", true, countY, false)
		}, maximumX, maximumY);
	}
	
	@Override
	public String Name()
	{
		return "repeater lateral";
	}

	@Override
	protected Shape[] Thumbnail()
	{
		return new Shape[]
		{
			new Ellipse(5, 15, 2.5, 2.5),
			new Ellipse(15, 15, 2.5, 2.5),
			new Ellipse(25, 15, 2.5, 2.5)
		};
	}

	@Override
	public void Draw(Pane canvas)
	{
		if (Overlay == null || !Overlay.IsEnabled) return;

		int countX = (int)GetAttribute("CountX").Value;
		int countY = (int)GetAttribute("CountY").Value;
		double width = canvas.getWidth() * (GetAttribute("Width").Value / MaximumX);
		double height = canvas.getHeight() * (GetAttribute("Height").Value / MaximumY);

		double locX = canvas.getWidth() * (GetAttribute("X").Value / MaximumX) - width / 2;
		double locY = canvas.getHeight() * (GetAttribute("Y").Value / MaximumY) - height / 2;

		double intervalX = width / (countX > 1 ? countX - 1 : countX);
		double intervalY = height / (countY > 1 ? countY - 1 : countY);

		Pane repeaterCanvas = new Pane();
		repeaterCanvas.setLayoutX(locX);
		repeaterCanvas.setLayoutY(locY);
		repeaterCanvas.setBackground(Background.EMPTY);

		for (int x = 0; x < countX; x++)
		{
			for (int y = 0; y < countY; y++)
			{
				AnchorPane a = new AnchorPane();
				a.setBackground(Background.EMPTY);
				Scene s = new Scene(a, width, height);
				Pane p = new Pane();
				p.setBackground(Background.EMPTY);
				s.setRoot(p);
				
				Overlay.Draw(p);
				repeaterCanvas.getChildren().add(p);
				
				p.setLayoutX(x * intervalX);
				p.setLayoutY(y * intervalY);
			}
		}

		canvas.getChildren().add(repeaterCanvas);
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
		if (Overlay == null) return "";
		if (!Overlay.IsEnabled) return "";

		double countX = (int)GetAttribute("CountX").Value;
		double countY = (int)GetAttribute("CountY").Value;
		double w = width * (GetAttribute("Width").Value / MaximumX);
		double h = height * (GetAttribute("Height").Value / MaximumY);

		double locX = width * (GetAttribute("X").Value / MaximumX) - w / 2;
		double locY = height * (GetAttribute("Y").Value / MaximumY) - h / 2;

		double intervalX = w / (countX > 1 ? countX - 1 : countX);
		double intervalY = h / (countY > 1 ? countY - 1 : countY);

		UUID id = UUID.randomUUID();
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("<defs><g id=\"%s\">%s</g></defs>\n",
			id.toString(), Overlay.ExportSvg((int)w, (int)h)));

		for (int x = 0; x < countX; x++)
		{
			for (int y = 0; y < countY; y++)
			{
				sb.append(String.format("<g transform=\"translate(%.3f,%.3f)\">\n",
					locX + x * intervalX,
					locY + y * intervalY));
				sb.append(String.format("<use xlink:href=\"#%s\" />\n", id.toString()));
				sb.append("</g>\n");
			}
		}

		return sb.toString();
	}
}
