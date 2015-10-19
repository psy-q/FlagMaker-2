package flagmaker.Overlays.OverlayTypes.ShapeTypes;

import flagmaker.Divisions.DivisionGrid;
import flagmaker.Flag;
import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayTypes.RepeaterTypes.OverlayRepeater;
import flagmaker.Ratio;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class OverlayFlag extends OverlayShape
{
	public Flag Flag;
	public File Path;

	public OverlayFlag(int maximumX, int maximumY)
	{
		super("flag", maximumX, maximumY);
		Flag = new Flag("flag", new Ratio(2, 3), new Ratio(2, 3), new DivisionGrid(Color.WHITE, Color.BLACK, 2, 2), new Overlay[]{});
	}
	
	public OverlayFlag(Flag flag, File path, int maximumX, int maximumY)
	{
		super("flag", maximumX, maximumY);
		Flag = flag;
		Path = path;
	}
	
	public OverlayFlag(Flag flag, File path, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super("flag", x, y, width, height, maximumX, maximumY);
		Flag = flag;
		Path = path;
	}

	@Override
	protected Shape[] Thumbnail()
	{
		Rectangle base = new Rectangle(30, 20, new Color(0, 0, 0, 0));
		base.setLayoutY(5);
		base.setStrokeWidth(3);
		
		Line l1 = new Line(10, 7, 10, 23);
		l1.setStrokeWidth(5);
		
		Line l2 = new Line(2, 15, 28, 15);
		l2.setStrokeWidth(5);
		
		return new Shape[] { base, l1, l2 };
	}

	@Override
	public void Draw(Pane canvas)
	{		
		double canvasWidth = canvas.getWidth() * GetAttribute("Width").Value / MaximumX;
		double canvasHeight = canvas.getHeight() * GetAttribute("Height").Value / MaximumY;
		
		AnchorPane a = new AnchorPane();
		Scene s = new Scene(a, canvasWidth, canvasHeight);
		Rectangle clip = new Rectangle(canvasWidth, canvasHeight);
		Pane p = new Pane();
		p.setClip(clip);
		s.setRoot(p);
		Flag.Draw(p);
		
		p.setLayoutX(canvas.getWidth() * GetAttribute("X").Value / MaximumX);
		p.setLayoutY(canvas.getHeight() * GetAttribute("Y").Value / MaximumX);
		canvas.getChildren().add(p);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("<g transform=\"translate(%.3f,%.3f) scale(%.3f %.3f)\">\n",
			width * (GetAttribute("X").Value / MaximumX),
			height * (GetAttribute("Y").Value / MaximumY),
			GetAttribute("Width").Value / MaximumX,
			GetAttribute("Height").Value / MaximumY));

		sb.append(Flag.Division.ExportSvg(width, height));

		for (int i = 0; i < Flag.Overlays.length; i++)
		{
			if (i == 0 || !(Flag.Overlays[i - 1] instanceof OverlayRepeater))
			{
				sb.append(Flag.Overlays[i].ExportSvg(width, height));
			}
		}

		sb.append("</g>\n");

		return sb.toString();
	}
}
