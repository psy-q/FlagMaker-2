package flagmaker;

import flagmaker.Divisions.Division;
import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayTypes.RepeaterTypes.OverlayRepeater;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	
	public void ExportToSvg(String path) throws IOException
	{
		final int width = 600;
		int height = (int)(((double)Ratio.Height / Ratio.Width) * width);
		
		try (FileWriter writer = new FileWriter(path, false); PrintWriter printLine = new PrintWriter(writer))
		{
			printLine.printf("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n");
			printLine.printf("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n");
			printLine.printf("<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" width=\"%s\" height=\"%s\">\n", width, height);
			
			printLine.printf("%s\n", Division.ExportSvg(width, height));
			
			SetRepeaterOverlays();
			
			for (int i = 0; i < Overlays.size(); i++)
			{
				if (i > 0 && Overlays.get(i - 1) instanceof OverlayRepeater) continue;
				
				Overlay overlay = Overlays.get(i);
				if (!overlay.IsEnabled) continue;
				
				try
				{
					printLine.printf(overlay.ExportSvg(width, height));
				}
				catch (UnsupportedOperationException e)
				{
				}
			}
			
			printLine.printf("</svg>\n");
		}
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
