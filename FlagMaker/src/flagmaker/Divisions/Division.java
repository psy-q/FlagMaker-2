package flagmaker.Divisions;

import javafx.scene.SubScene;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public abstract class Division
{
	public Color[] Colors;
	public int[] Values;
	
	protected Division(Color[] colors, int[] values)
	{
		Colors = colors;
		Values = values;
	}
	
	public abstract String Name();
	public abstract void Draw(Pane canvas);
	public abstract void SetColors(Color[] colors);
	public abstract void SetValues(int[] values);
	public abstract String ExportSvg(int width, int height);
}
