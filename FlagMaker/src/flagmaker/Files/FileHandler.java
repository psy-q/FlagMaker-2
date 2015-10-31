package flagmaker.Files;

import flagmaker.Extensions.ColorExtensions;
import flagmaker.Divisions.Division;
import flagmaker.Divisions.DivisionBendsBackward;
import flagmaker.Divisions.DivisionBendsForward;
import flagmaker.Divisions.DivisionFesses;
import flagmaker.Divisions.DivisionGrid;
import flagmaker.Divisions.DivisionPales;
import flagmaker.Divisions.DivisionX;
import flagmaker.Data.Flag;
import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPath;
import flagmaker.Data.Ratio;
import flagmaker.Data.Size;
import flagmaker.Extensions.StringExtensions;
import flagmaker.Data.Vector;
import flagmaker.Overlays.OverlayTypes.RepeaterTypes.OverlayRepeater;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javax.imageio.ImageIO;

public class FileHandler
{
	public static void SaveFlagToFile(Flag flag, String path) throws IOException
	{
		try (FileWriter writer = new FileWriter(path, false); PrintWriter printLine = new PrintWriter(writer))
		{
			printLine.printf(flag.ExportToString());
		}
	}
	
	public static void ExportFlagToSvg(Flag flag, File file) throws IOException
	{
		final int width = 600;
		int height = (int)(((double)flag.Ratio.Height / flag.Ratio.Width) * width);
		
		try (FileWriter writer = new FileWriter(file, false); PrintWriter printLine = new PrintWriter(writer))
		{
			printLine.printf("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n");
			printLine.printf("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n");
			printLine.printf("<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" version=\"1.1\" width=\"%s\" height=\"%s\">\n", width, height);
			
			printLine.printf("%s\n", flag.Division.ExportSvg(width, height));
			
			flag.SetRepeaterOverlays();
			
			for (int i = 0; i < flag.Overlays.length; i++)
			{
				if (i > 0 && flag.Overlays[i - 1] instanceof OverlayRepeater) continue;
				
				Overlay overlay = flag.Overlays[i];
				if (!overlay.IsEnabled) continue;
				printLine.printf(overlay.ExportSvg(width, height));
			}
			
			printLine.printf("</svg>\n");
		}
	}
	
	public static void ExportFlagToPng(Flag flag, Size size, File path) throws IOException
	{
		AnchorPane a = new AnchorPane();
		Scene s = new Scene(a, size.X, size.Y);
		Rectangle clip = new Rectangle(size.X, size.Y);
		Pane p = new Pane();
		p.setClip(clip);
		s.setRoot(p);
		
		flag.Draw(p);
		
		WritableImage snapshot = p.snapshot(new SnapshotParameters(), null);
		ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", path);
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
	
	public static File GetFilePossiblyRelative(File file, String directory)
	{
		if (file.exists()) return file;
		
		File absolute = new File(String.format("%s%s%s", directory, GetPathSeparator(), file.getPath()));
		if (absolute.exists()) return absolute;
		
		return null;
	}
	
	public static String GetPathSeparator()
	{
		String os = System.getProperty("os.name");
		return os.toLowerCase().contains("windows")
			? "\\"
			: "/";
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
		
		for (String line : lines)
		{
			String[] data = line.split("=");
			if (data[0].equals("overlay") || data[0].equals("type")) continue;
			t.Values.put(data[0], data[1]);
		}
		
		return t.ToOverlay(maximumX, maximumY, directory);
	}
		
	private static String GetValue(ArrayList<String> data, String fieldName, String defaultValue)
	{
		return data.stream().filter(s -> s.toLowerCase().startsWith(fieldName.toLowerCase())).findFirst().orElse(defaultValue).split("=")[1];
	}
}
