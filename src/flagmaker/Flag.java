package flagmaker;

import flagmaker.Divisions.Division;
import flagmaker.Divisions.DivisionGrid;
import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayTypes.RepeaterTypes.OverlayRepeater;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Flag
{
	public String Name;
	public Ratio Ratio;
	public Ratio GridSize;
	public Division Division;
	public Overlay[] Overlays;
	
	public Flag(String name, Ratio ratio, Ratio gridSize, Division division, Overlay[] overlays)
	{
		Name = name;
		Ratio = ratio;
		GridSize = gridSize;
		Division = division;
		Overlays = overlays;
	}
	
	public static Flag LoadFromFile(String filename) throws Exception
	{
		return null;
	}
	
	public static String GetFlagPath()
	{
		return null;
	}
	
	public void Draw(Pane canvas)
	{
		canvas.getChildren().clear();
		Division.Draw(canvas);
		SetRepeaterOverlays();
		
		for (int i = 0; i < Overlays.length; i++)
		{
			// Skip overlays used in repeaters
			if (i > 0 && Overlays[i - 1] instanceof OverlayRepeater) continue;

			if (!Overlays[i].IsEnabled) continue;

			Overlays[i].Draw(canvas);
		}
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
			
			for (int i = 0; i < Overlays.length; i++)
			{
				if (i > 0 && Overlays[i - 1] instanceof OverlayRepeater) continue;
				
				Overlay overlay = Overlays[i];
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
	
	public Color[] ColorsUsed()
	{
		ArrayList<Color> colors = new ArrayList<>();
		
		if (Division instanceof DivisionGrid && Division.Values[0] == 1 && Division.Values[1] == 1)
		{
			colors.add(Division.Colors[0]);
		}
		else
		{
			colors.addAll(Arrays.asList(Division.Colors));
		}
		
		for (Overlay overlay : Overlays)
		{
//			var flag = overlay as OverlayFlag;
//			if (flag != null)
//			{
//				colors.AddRange(flag.Flag.ColorsUsed());
//			}
//			else if (!(overlay is OverlayRepeater || overlay is OverlayImage))
//			{
				colors.add(overlay.Color);

//				var path = overlay as OverlayPath;
//				if (path != null && path.StrokeColor.A > 0 && path.Attributes.Get(strings.Stroke).Value > 0)
//				{
//					colors.Add(path.StrokeColor);
//				}
//			}
		}
		
		Color[] returnValue = new Color[]{};
		return colors.toArray(returnValue);
	}
	
	private void SetRepeaterOverlays()
	{
		// Clear last repeater in list
		if (Overlays.length > 0 && Overlays[Overlays.length - 1] instanceof OverlayRepeater)
		{
			((OverlayRepeater)Overlays[Overlays.length - 1]).SetOverlay(null);
		}

		// Set overlays for others
		for (int i = Overlays.length - 1; i > 0; i--)
		{
			if (Overlays[i - 1] instanceof OverlayRepeater)
			{
				OverlayRepeater repeater = (OverlayRepeater)Overlays[i - 1];
				repeater.SetOverlay(Overlays[i]);
			}
		}
	}
	
	private Color ParseColor(String str)
	{
		double a = 1.0;
		byte r, b, g;

		if (str.length() == 8)
		{
			a = ((double)Byte.parseByte(str.substring(0, 2), 16)) / 255.0;
			r = Byte.parseByte(str.substring(2, 2), 16);
			g = Byte.parseByte(str.substring(4, 2), 16);
			b = Byte.parseByte(str.substring(6, 2), 16);
		}
		else
		{
			r = Byte.parseByte(str.substring(0, 2), 16);
			g = Byte.parseByte(str.substring(2, 2), 16);
			b = Byte.parseByte(str.substring(4, 2), 16);
		}

		return Color.rgb(r, g, b, a);
	}
	
	private static Double GetDoubleFromString(String data)
	{
		// Doubles in files can be written as "123.45" or "123,45".
		// (Ignore thousands separators - not really applicable for FlagMaker.)
		// If the user saved a file with commas, replace and parse with invariant culture.
		return Double.parseDouble(data.replace(',', '.'));
	}
}
