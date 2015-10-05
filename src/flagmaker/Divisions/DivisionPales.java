package flagmaker.Divisions;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DivisionPales extends Division 
{
	public DivisionPales(Color[] colors, Double[] values)
	{
		super(colors, values);
	}

	public String Name()
	{
		return "pales";
	}
	
	public void Draw(Pane canvas)
	{
		double height = canvas.getHeight();
		double width = canvas.getWidth();
				
		Rectangle left = new Rectangle(width / 3.0, height, Colors[0]);
		Rectangle center = new Rectangle(width * 2.0 / 3.0, height, Colors[1]);
		Rectangle right = new Rectangle(width, height, Colors[2]);
		canvas.getChildren().addAll(right, center, left);
	}
	
	public void SetColors(Color[] colors)
	{
	}
	
	public void SetValues(Double[] values)
	{
	}
	
	public String ExportSvg(int width, int height)
	{
		return "";
	}
}
