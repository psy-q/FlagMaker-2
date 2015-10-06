package flagmaker.Divisions;

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
				
		Rectangle top = new Rectangle(width, height / 3.0, Colors[0]);
		Rectangle middle = new Rectangle(width, height * 2.0 / 3.0, Colors[1]);
		Rectangle bottom = new Rectangle(width, height, Colors[2]);
		canvas.getChildren().addAll(bottom, middle, top);
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
