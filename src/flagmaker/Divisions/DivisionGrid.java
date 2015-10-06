package flagmaker.Divisions;

import flagmaker.ColorExtensions;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DivisionGrid extends Division
{
	public DivisionGrid(Color color1, Color color2, int horizontalCount, int verticalCount)
	{
		super(new Color[] { color1, color2 }, new int[] { horizontalCount, verticalCount });
	}

	@Override
	public String Name()
	{
		return "grid";
	}

	@Override
	public void Draw(Pane canvas)
	{
		double height = canvas.getHeight();
		double width = canvas.getWidth();
		
		canvas.getChildren().add(new Rectangle(width, height, Colors[0]));
		
		double gHeight = height / Values[1];
		double gWidth = width / Values[0];
		
		for (int x = 0; x < Values[0]; x++)
		{
			for (int y = 0; y < Values[1]; y++)
			{
				if ((x + y) % 2 == 0) continue;
				
				Rectangle r = new Rectangle(gWidth, gHeight, Colors[1]);
				r.setX(x * gWidth);
				r.setY(y * gHeight);
				canvas.getChildren().add(r);
			}
		}
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
		Values[0] = values[0];
		Values[1] = values[1];
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("<rect width=\"%d\" height=\"%d\" x=\"0\" y=\"0\" %s />",
				width,
				height,
				ColorExtensions.ToSvgFillWithOpacity(Colors[0])));
		
		double h = height / (double)Values[1];
		double w = width / (double)Values[0];
			
		for	(int x = 0; x < Values[0]; x++)
		{
			for (int y = 0; y < Values[1]; y++)
			{
				sb.append(String.format("<rect width=\"%.3f\" height=\"%.3f\" x=\"%.3f\" y=\"%.3f\" %s />",
					w, h, x * w, y * h,
					ColorExtensions.ToSvgFillWithOpacity((x + y) % 2 == 0 ? Colors[0] : Colors[1])));
			}
		}
		
		return sb.toString();
	}
}
