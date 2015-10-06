package flagmaker.Divisions;

import flagmaker.ColorExtensions;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;

public class DivisionBendsBackward extends Division
{
	public DivisionBendsBackward(Color color1, Color color2)
	{
		super(new Color[] { color1, color2 }, new int[] {});
	}

	@Override
	public String Name()
	{
		return "bends backward";
	}

	@Override
	public void Draw(Pane canvas)
	{
		double height = canvas.getHeight();
		double width = canvas.getWidth();
		
		canvas.getChildren().add(new Rectangle(width, height, Colors[0]));
		
		Path p = new Path(new PathElement[]
		{
			new MoveTo(width, height),
			new LineTo(0, height),
			new LineTo(0, 0),
			new LineTo(width, height)
		});
		p.fillProperty().set(Colors[1]);
		p.strokeWidthProperty().set(0);
		canvas.getChildren().add(p);
	}

	@Override
	public void SetColors(Color[] colors)
	{
		Colors[0] = colors[0];
		Colors[1] = colors[1];
	}

	@Override
	public void SetValues(int[] values)
	{
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("<rect width=\"%d\" height=\"%d\" x=\"0\" y=\"0\" %s />",
				width,
				height,
				ColorExtensions.ToSvgFillWithOpacity(Colors[0])));
		sb.append(String.format("<polygon points=\"0,0 0,%1$d %1$d,%2$d\" %3$s />",
				width,
				height,
				ColorExtensions.ToSvgFillWithOpacity(Colors[1])));
		return sb.toString();
	}
}
