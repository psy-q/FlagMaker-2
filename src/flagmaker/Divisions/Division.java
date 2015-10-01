package flagmaker.Divisions;

import java.util.List;
import javafx.scene.paint.Color;

public abstract class Division
{
	public List<Color> Colors;
	public List<Double> Values;
	
	protected Division(List<Color> colors, List<Double> values)
	{
		Colors = colors;
		Values = values;
	}
	
	public abstract String Name();
	public abstract void Draw();
	public abstract void SetColors(List<Color> colors);
	public abstract void SetValues(List<Double> values);
	public abstract String ExportSvg(int width, int height);
}
