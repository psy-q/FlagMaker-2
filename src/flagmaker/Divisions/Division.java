package flagmaker.Divisions;

import javafx.scene.SubScene;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public abstract class Division
{
	public Color[] Colors;
	public Double[] Values;
	
	protected Division(Color[] colors, Double[] values)
	{
		Colors = colors;
		Values = values;
	}
	
	public abstract String Name();
	public abstract void Draw(Pane canvas);
	public abstract void SetColors(Color[] colors);
	public abstract void SetValues(Double[] values);
	public abstract String ExportSvg(int width, int height);
}
