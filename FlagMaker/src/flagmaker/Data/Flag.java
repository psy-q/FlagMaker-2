package flagmaker.Data;

import flagmaker.Divisions.*;
import flagmaker.Extensions.ColorExtensions;
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
	
	public String ExportToString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("name=%s\n", Name));
		sb.append(String.format("ratio=%d:%d\n", Ratio.Height, Ratio.Width));
		sb.append(String.format("gridSize=%s\n\n", GridSize.ToString()));
		sb.append(String.format("type=%s\n", Division.Name()));
		
		for (int i = 0; i < Division.Colors.length; i++)
		{
			sb.append(String.format("color%d=%s\n", i + 1, ColorExtensions.ToHexString(Division.Colors[i], false)));
		}

		for (int i = 0; i < Division.Values.length; i++)
		{
			sb.append(String.format("size%d=%d\n", i + 1, Division.Values[i]));
		}
		
		for (Overlay overlay : Overlays)
		{
			sb.append(overlay.ExportToString());
		}
		
		return sb.toString();
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
	
	public void SetRepeaterOverlays()
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
