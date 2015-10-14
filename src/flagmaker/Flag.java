package flagmaker;

import flagmaker.Divisions.Division;
import flagmaker.Divisions.DivisionGrid;
import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPath;
import flagmaker.Overlays.OverlayTypes.RepeaterTypes.OverlayRepeater;
import flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayFlag;
import flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

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
	
	public static Flag LoadFromFile(File file) throws Exception
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

			// Skip overlays disabled in editor
			if (!Overlays[i].IsEnabled) continue;

			Overlays[i].Draw(canvas);
		}
	}
	
	public void Save(String path) throws IOException
	{
		try (FileWriter writer = new FileWriter(path, false); PrintWriter printLine = new PrintWriter(writer))
		{
			printLine.printf("name=%s\n", Name);
			printLine.printf("ratio=%d:%d\n", Ratio.Height, Ratio.Width);
			printLine.printf("gridSize=%s\n\n", GridSize.ToString());
			
			printLine.printf("division\n");
			printLine.printf("type=%s\n", Division.Name());
			
			for (int i = 0; i < Division.Colors.length; i++)
			{
				printLine.printf("color%d=%s\n", i + 1, ColorExtensions.ToHexString(Division.Colors[i], false));
			}
			
			for (int i = 0; i < Division.Values.length; i++)
			{
				printLine.printf("size%d=%d\n", i + 1, Division.Values[0]);
			}
			
			for (Overlay overlay : Overlays)
			{
				printLine.printf("\noverlay\n");
				printLine.printf("type=%s\n", overlay.Name());
				
				if (overlay.Name().equals("flag"))
				{
					printLine.printf("path=%s\n", ((OverlayFlag)overlay).Path);
				}
				
				if (overlay.Name().equals("image"))
				{
					printLine.printf("path=%s\n", ((OverlayImage)overlay).GetPath());
				}
				else
				{
					printLine.printf("color=%s\n", ColorExtensions.ToHexString(overlay.Color, true));
				}

				for (int i = 0; i < overlay.Attributes.length; i++)
				{
					printLine.printf("size%d=%f\n", i + 1, overlay.Attributes[i].Value);
				}

				if (overlay instanceof OverlayPath)
				{
					printLine.printf("stroke=%s\n", ColorExtensions.ToHexString(((OverlayPath)overlay).StrokeColor, true));
				}
			}
		}
	}
	
	public void ExportToPng(Size size, String path)
	{
		AnchorPane a = new AnchorPane();
		Scene s = new Scene(a, size.X, size.Y);
		Pane p = new Pane();
		s.setRoot(p);
		
		Draw(p);
		
		WritableImage snapshot = p.snapshot(new SnapshotParameters(), null);

		File fileA = new File(path);
		try
		{
			ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", fileA);
		}
		catch (Exception ex)
		{
		}
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
