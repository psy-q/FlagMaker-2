package flagmaker.Divisions;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DivisionPales extends Division 
{
	public DivisionPales(Color color1, Color color2, Color color3, int v1, int v2, int v3)
	{
		super(new Color[] { color1, color2, color3 }, new int[] { v1, v2, v3 });
	}

	@Override
	public String Name()
	{
		return "pales";
	}
	
	@Override
	public void Draw(Pane canvas)
	{
		double height = canvas.getHeight();
		double width = canvas.getWidth();
				
		Rectangle left = new Rectangle(width / 3.0, height, Colors[0]);
		Rectangle center = new Rectangle(width * 2.0 / 3.0, height, Colors[1]);
		Rectangle right = new Rectangle(width, height, Colors[2]);
		canvas.getChildren().addAll(right, center, left);
	}
	
	@Override
	public void SetColors(Color[] colors)
	{
	}
	
	@Override
	public void SetValues(int[] values)
	{
	}
	
	@Override
	public String ExportSvg(int width, int height)
	{
		return "";
	}
}
