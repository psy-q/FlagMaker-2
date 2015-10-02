package flagmaker.Divisions;

import javafx.scene.SubScene;
import javafx.scene.canvas.*;
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
		Rectangle right = new Rectangle();
		right.fillProperty().set(Colors[2]);
		right.widthProperty().set(100);
		right.heightProperty().set(100);
		canvas.getChildren().add(right);
		
		//GraphicsContext context = canvas.getGraphicsContext2D();
		//context.setFill(Colors[2]);
		//context.fillRect(0, 0, 100, 100);
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
