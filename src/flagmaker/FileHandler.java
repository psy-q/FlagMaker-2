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
import flagmaker.Overlays.OverlayTypes.SpecialTypes.OverlayFlag;
import flagmaker.Overlays.OverlayTypes.SpecialTypes.OverlayImage;
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

				for (int i = 0; i < overlay.Attributes.length; i++)
				{
					printLine.printf("%s=%s\n", overlay.Attributes[i].Name, overlay.Attributes[i].ToSaveAsString());
				}
			}
		}
	}
	
	public static Flag LoadFlagFromFile(File file) throws Exception
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
	
	public static OverlayPath LoadOverlayFromFile(File file) throws Exception
	{
		ArrayList<String> lines = ReadAllLines(file);
		String name = GetValue(lines, "name", "name= ");
		int width = Integer.parseInt(GetValue(lines, "width", "width=1"));
		int height = Integer.parseInt(GetValue(lines, "height", "height=1"));
		String path = GetValue(lines, "path", "path= ");
		
		return new OverlayPath(name, path, new Vector(width, height));
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
		t.Path = new File(GetValue(lines, "path", "path= "));
		
		for (String line : lines)
		{
			String[] data = line.split("=");
			if (data[0].equals("overlay") || data[0].equals("type") || data[0].equals("path")) continue;
			t.Values.put(data[0], data[1]);
		}
		
		return t.ToOverlay(maximumX, maximumY, directory);
	}
		
	private static String GetValue(ArrayList<String> data, String fieldName, String defaultValue)
	{
		return data.stream().filter(s -> s.startsWith(fieldName)).findFirst().orElse(defaultValue).split("=")[1];
	}
}
