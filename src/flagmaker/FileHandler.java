package flagmaker;

import flagmaker.Divisions.Division;
import flagmaker.Divisions.DivisionBendsBackward;
import flagmaker.Divisions.DivisionBendsForward;
import flagmaker.Divisions.DivisionFesses;
import flagmaker.Divisions.DivisionGrid;
import flagmaker.Divisions.DivisionPales;
import flagmaker.Divisions.DivisionX;
import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPath;
import flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayFlag;
import flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class FileHandler
{
	public static void Save(Flag flag, String path) throws IOException
	{
		try (FileWriter writer = new FileWriter(path, false); PrintWriter printLine = new PrintWriter(writer))
		{
			printLine.printf("name=%s\n", flag.Name);
			printLine.printf("ratio=%d:%d\n", flag.Ratio.Height, flag.Ratio.Width);
			printLine.printf("gridSize=%s\n\n", flag.GridSize.ToString());
			
			printLine.printf("division\n");
			printLine.printf("type=%s\n", flag.Division.Name());
			
			for (int i = 0; i < flag.Division.Colors.length; i++)
			{
				printLine.printf("color%d=%s\n", i + 1, ColorExtensions.ToHexString(flag.Division.Colors[i], false));
			}
			
			for (int i = 0; i < flag.Division.Values.length; i++)
			{
				printLine.printf("size%d=%d\n", i + 1, flag.Division.Values[i]);
			}
			
			for (Overlay overlay : flag.Overlays)
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
	
	public static Flag LoadFromFile(File file) throws Exception
	{
		ArrayList<String> lines = ReadAllLines(file);
		ArrayList<ArrayList<String>> groups = SplitLines(lines);
		
		String name = GetValue(groups.get(0), "name", "name= ");
		Ratio ratio = new Ratio(GetValue(groups.get(0), "ratio", "ratio=2:3"));
		Ratio gridRatio = new Ratio(GetValue(groups.get(0), "gridSize", "ratio=2:3"));
		
		Division division = ReadDivision(groups.get(1));
		
		ArrayList<Overlay> overlays = new ArrayList<>();
		for (int i = 2; i < groups.size(); i++)
		{
			overlays.add(ReadOverlay(groups.get(i), gridRatio.Width, gridRatio.Height, file.getParent()));
		}

		Overlay[] finalOverlays = new Overlay[]{};
		return new Flag(name, ratio, gridRatio, division, overlays.toArray(finalOverlays));
	}
	
	private static ArrayList<String> ReadAllLines(File file) throws Exception
	{
		ArrayList<String> lines = new ArrayList<>();
		String line = "";
		try (FileReader fr = new FileReader(file); BufferedReader sr = new BufferedReader(fr))
		{
			while ((line = sr.readLine()) != null)
			{
				lines.add(line);
			}
		}
		catch (Exception ex)
		{
			throw new Exception(line, ex);
		}
		
		return lines;
	}
	
	private static ArrayList<ArrayList<String>> SplitLines(ArrayList<String> lines)
	{
		ArrayList<ArrayList<String>> returnValue = new ArrayList<>();
		
		ArrayList<String> currentSection = new ArrayList<>();
		for (String line : lines)
		{
			if (StringExtensions.IsNullOrWhitespace(line) && !currentSection.isEmpty())
			{
				returnValue.add((ArrayList<String>)currentSection.clone());
				currentSection.clear();
			}
			else
			{
				currentSection.add(line);
			}
		}
		
		returnValue.add((ArrayList<String>)currentSection.clone());
		return returnValue;
	}
	
	private static Division ReadDivision(ArrayList<String> lines)
	{
		String type = GetValue(lines, "type", "type=grid");
		
		Color color1 = ColorExtensions.ParseColor(GetValue(lines, "color1", "color1=ffffff"));
		Color color2 = ColorExtensions.ParseColor(GetValue(lines, "color2", "color2=ffffff"));
		Color color3 = ColorExtensions.ParseColor(GetValue(lines, "color3", "color3=ffffff"));
		
		int divisionVal1 = Integer.parseInt(GetValue(lines, "size1", "size1=1"));
		int divisionVal2 = Integer.parseInt(GetValue(lines, "size2", "size2=1"));
		int divisionVal3 = Integer.parseInt(GetValue(lines, "size3", "size3=1"));
		
		switch (type)
		{
			case "fesses":
				return new DivisionFesses(color1, color2, color3, divisionVal1, divisionVal2, divisionVal3);
			case "pales":
				return new DivisionPales(color1, color2, color3, divisionVal1, divisionVal2, divisionVal3);
			case "bends forward":
				return new DivisionBendsForward(color1, color2);
			case "bends backward":
				return new DivisionBendsBackward(color1, color2);
			case "bends both":
				return new DivisionX(color1, color2);
			default:
				return new DivisionGrid(color1, color2, divisionVal1, divisionVal2);
		}
	}
	
	private static Overlay ReadOverlay(ArrayList<String> lines, int maximumX, int maximumY, String directory) throws Exception
	{
		TempOverlay t = new TempOverlay();
		t.Type = GetValue(lines, "type", "type=grid");
		t.Color = ColorExtensions.ParseColor(GetValue(lines, "color", "color=ffffff"));
		t.StrokeColor = ColorExtensions.ParseColor(GetValue(lines, "stroke", "stroke=ffffff"));
		t.Path = new File(GetValue(lines, "path", "path= "));
		t.StrokeColor = ColorExtensions.ParseColor(GetValue(lines, "stroke", "stroke=ffffff"));
		
		for (int i = 0; i < 8; i++)
		{
			t.Values[i] = GetDoubleFromString(GetValue(lines, String.format("size%d", i + 1), String.format("size%d=1", i + 1)));
		}
		
		return t.ToOverlay(maximumX, maximumY, directory);
	}
		
	private static String GetValue(ArrayList<String> data, String fieldName, String defaultValue)
	{
		return data.stream().filter(s -> s.startsWith(fieldName)).findFirst().orElse(defaultValue).split("=")[1];
	}
	
	private static Double GetDoubleFromString(String data)
	{
		// Doubles in files can be written as "123.45" or "123,45".
		// (Ignore thousands separators - not really applicable for FlagMaker.)
		// If the user saved a file with commas, replace and parse with invariant culture.
		return Double.parseDouble(data.replace(',', '.'));
	}
}
