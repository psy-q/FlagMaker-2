package flagmaker;

import flagmaker.Divisions.*;
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
import java.util.HashSet;
import java.util.Set;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
				printLine.printf("size%d=%d\n", i + 1, Division.Values[i]);
			}
			
			for (Overlay overlay : Overlays)
			{
				printLine.printf("\noverlay\n");
				printLine.printf("type=%s\n", overlay.Name);
				
				if (overlay.Name.equals("flag"))
				{
					printLine.printf("path=%s\n", ((OverlayFlag)overlay).Path);
				}
				
				if (overlay.Name.equals("image"))
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
	
	public void ExportToPng(Size size, File path)
	{
		AnchorPane a = new AnchorPane();
		Scene s = new Scene(a, size.X, size.Y);
		Rectangle clip = new Rectangle(size.X, size.Y);
		Pane p = new Pane();
		p.setClip(clip);
		s.setRoot(p);
		
		Draw(p);
		
		WritableImage snapshot = p.snapshot(new SnapshotParameters(), null);

		try
		{
			ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", path);
		}
		catch (Exception ex)
		{
		}
	}
	
	public void ExportToSvg(File file)
	{
		final int width = 600;
		int height = (int)(((double)Ratio.Height / Ratio.Width) * width);
		
		try (FileWriter writer = new FileWriter(file, false); PrintWriter printLine = new PrintWriter(writer))
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
		catch(Exception e)
		{
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
			if (overlay instanceof OverlayFlag)
			{
				colors.addAll(Arrays.asList(((OverlayFlag)overlay).Flag.ColorsUsed()));
			}
			else if (!(overlay instanceof OverlayRepeater || overlay instanceof OverlayImage))
			{
				colors.add(overlay.Color);

				if (overlay instanceof OverlayPath)
				{
					OverlayPath path = (OverlayPath)overlay;
					
					if (path.StrokeColor.getOpacity() > 0 && path.GetAttribute("Stroke").Value > 0)
					{
						colors.add(path.StrokeColor);
					}
				}
			}
		}
		
		Set<Color> hs = new HashSet<>();
		hs.addAll(colors);
		Color[] returnValue = new Color[]{};
		return hs.toArray(returnValue);
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
}
