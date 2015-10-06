package flagmaker.Divisions;

import flagmaker.ColorExtensions;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DivisionFesses extends Division 
{
	public DivisionFesses(Color color1, Color color2, Color color3, int v1, int v2, int v3)
	{
		super(new Color[] { color1, color2, color3 }, new int[] { v1, v2, v3 });
	}

	@Override
	public String Name()
	{
		return "fesses";
	}
	
	@Override
	public void Draw(Pane canvas)
	{
		double height = canvas.getHeight();
		double width = canvas.getWidth();
		
		double sizeSum = Values[0] + Values[1] + Values[2];
		
		double r1Size = height * Values[0] / sizeSum;
		double r2Size = height * (Values[0] + Values[1]) / sizeSum;
				
		Rectangle top = new Rectangle(width, r1Size, Colors[0]);
		Rectangle middle = new Rectangle(width, r2Size, Colors[1]);
		Rectangle bottom = new Rectangle(width, height, Colors[2]);
		canvas.getChildren().addAll(bottom, middle, top);
	}
	
	@Override
	public void SetColors(Color[] colors)
	{
		Colors[0] = colors[0];
		Colors[1] = colors[1];
		Colors[2] = colors[2];
	}
	
	@Override
	public void SetValues(int[] values)
	{
		Values[0] = values[0];
		Values[1] = values[1];
		Values[2] = values[2];
	}
	
	@Override
	public String ExportSvg(int width, int height)
	{
		StringBuilder sb = new StringBuilder();

		double sizeSum = Values[0] + Values[1] + Values[2];
		double r1Size = height * Values[0] / sizeSum;
		double r2Size = height * (Values[0] + Values[1]) / sizeSum;
			
		// Bottom
		sb.append(String.format("<rect width=\"%d\" height=\"%d\" x=\"0\" y=\"0\" %s />",
				width,
				height,
				ColorExtensions.ToSvgFillWithOpacity(Colors[2])));
		
		// Middle
		sb.append(String.format("<rect width=\"%d\" height=\"%.3f\" x=\"0\" y=\"0\" %s />",
				width,
				r2Size,
				ColorExtensions.ToSvgFillWithOpacity(Colors[1])));
		
		// Top
		sb.append(String.format("<rect width=\"%d\" height=\"%.3f\" x=\"0\" y=\"0\" %s />",
				width,
				r1Size,
				ColorExtensions.ToSvgFillWithOpacity(Colors[0])));
		
		return sb.toString();
	}
}
