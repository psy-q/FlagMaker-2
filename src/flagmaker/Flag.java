package flagmaker;

import flagmaker.Divisions.Division;
import flagmaker.Overlays.Overlay;
import java.util.List;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Flag
{
	public String Name;
	public Ratio Ratio;
	public Ratio GridSize;
	public Division Division;
	public List<Overlay> Overlays;
	
	public Flag(String name, Ratio ratio, Ratio gridSize, Division division, List<Overlay> overlays)
	{
		Name = name;
		Ratio = ratio;
		GridSize = gridSize;
		Division = division;
		Overlays = overlays;
	}
	
	public static Flag LoadFromFile(String filename)
	{
		return null;
	}
	
	public static String GetFlagPath()
	{
		return null;
	}
	
	public void Draw(Pane canvas)
	{
		// clear canvas
		Division.Draw(canvas);
	}
	
	public void ExportToPng(Size size, String path)
	{
	}
	
	public void ExportToSvg(String path)
	{
	}
	
	public List<Color> ColorsUsed()
	{
		return null;
	}
	
	private void SetRepeaterOverlays()
	{
	}
	
	private Color ParseColor(String str)
	{
		return null;
	}
	
	private static Double GetDoubleFromString(String data)
	{
		return null;
	}
}
