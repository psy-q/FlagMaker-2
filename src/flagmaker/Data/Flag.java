package flagmaker.Data;

import flagmaker.Divisions.*;
import flagmaker.Overlays.Attributes.Attribute;
import flagmaker.Overlays.Attributes.ColorAttribute;
import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPath;
import flagmaker.Overlays.OverlayTypes.RepeaterTypes.OverlayRepeater;
import flagmaker.Overlays.OverlayTypes.SpecialTypes.OverlayFlag;
import java.io.File;
import java.io.FileWriter;
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
			else if (overlay instanceof OverlayPath)
			{
				OverlayPath p = (OverlayPath)overlay;
				colors.add(p.GetColorAttribute("Color"));
				if (p.GetDoubleAttribute("Stroke") > 0)
				{
					colors.add(p.GetColorAttribute("StrokeColor"));
				}
			}
			else
			{
				for (Attribute a : overlay.Attributes)
				{
					if (a instanceof ColorAttribute)
					{
						colors.add(((ColorAttribute)a).Value);
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
