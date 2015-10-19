package flagmaker;

import flagmaker.Divisions.Division;
import flagmaker.Divisions.DivisionBendsBackward;
import flagmaker.Divisions.DivisionBendsForward;
import flagmaker.Divisions.DivisionFesses;
import flagmaker.Divisions.DivisionGrid;
import flagmaker.Divisions.DivisionPales;
import flagmaker.Divisions.DivisionX;
import flagmaker.Overlays.Overlay;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class FileHandler
{
	public static Flag LoadFromFile(File file) throws Exception
	{
		String name = "";
		Ratio ratio = new Ratio(3, 2);
		Ratio gridRatio = new Ratio(3, 2);
		
		String divisionType = "grid";
		Color divisionColor1 = Color.WHITE;
		Color divisionColor2 = Color.WHITE;
		Color divisionColor3 = Color.WHITE;
		int divisionVal1 = 1;
		int divisionVal2 = 1;
		int divisionVal3 = 1;

		ArrayList<TempOverlay> overlays = new ArrayList<>();

		String line = "";
		try (FileReader fr = new FileReader(file); BufferedReader sr = new BufferedReader(fr))
		{
			boolean isDivision = false;
			int overlayIndex = -1;

			while ((line = sr.readLine()) != null)
			{
				switch (line.split("=")[0].toLowerCase())
				{
					case "name":
						name = line.split("=")[1];
						break;
					case "ratio":
						ratio = new Ratio(line.split("=")[1]);
						break;
					case "gridsize":
						gridRatio = new Ratio(line.split("=")[1]);
						break;
					case "division":
						isDivision = true;
						break;
					case "overlay":
						isDivision = false;
						overlayIndex++;
						TempOverlay to = new TempOverlay();
						overlays.add(to);
						break;
					case "type":
						if (isDivision)
						{
							divisionType = line.split("=")[1];
						}
						else
						{
							overlays.get(overlayIndex).Type = line.split("=")[1];
						}
						break;
					case "color1":
						divisionColor1 = ColorExtensions.ParseColor(line.split("=")[1]);
						break;
					case "color2":
						divisionColor2 = ColorExtensions.ParseColor(line.split("=")[1]);
						break;
					case "color3":
						divisionColor3 = ColorExtensions.ParseColor(line.split("=")[1]);
						break;
					case "color":
						overlays.get(overlayIndex).Color = ColorExtensions.ParseColor(line.split("=")[1]);
						break;
					case "size1":
						if (isDivision)
						{
							divisionVal1 = Integer.parseInt(line.split("=")[1]);
						}
						else
						{
							overlays.get(overlayIndex).Values[0] = GetDoubleFromString(line.split("=")[1]);
						}
						break;
					case "size2":
						if (isDivision)
						{
							divisionVal2 = Integer.parseInt(line.split("=")[1]);
						}
						else
						{
							overlays.get(overlayIndex).Values[1] = GetDoubleFromString(line.split("=")[1]);
						}
						break;
					case "size3":
						if (isDivision)
						{
							divisionVal3 = Integer.parseInt(line.split("=")[1]);
						}
						else
						{
							overlays.get(overlayIndex).Values[2] = GetDoubleFromString(line.split("=")[1]);
						}
						break;
					case "size4":
						overlays.get(overlayIndex).Values[3] = GetDoubleFromString(line.split("=")[1]);
						break;
					case "size5":
						overlays.get(overlayIndex).Values[4] = GetDoubleFromString(line.split("=")[1]);
						break;
					case "size6":
						overlays.get(overlayIndex).Values[5] = GetDoubleFromString(line.split("=")[1]);
						break;
					case "size7":
						overlays.get(overlayIndex).Values[6] = GetDoubleFromString(line.split("=")[1]);
						break;
					case "size8":
						overlays.get(overlayIndex).Values[7] = GetDoubleFromString(line.split("=")[1]);
						break;
					case "path":
						overlays.get(overlayIndex).Path = new File(line.split("=")[1]);
						break;
					case "stroke":
						overlays.get(overlayIndex).StrokeColor = ColorExtensions.ParseColor(line.split("=")[1]);
						break;
				}
			}

			Division division;
			switch (divisionType)
			{
				case "fesses":
					division = new DivisionFesses(divisionColor1, divisionColor2, divisionColor3, divisionVal1, divisionVal2,
						divisionVal3);
					break;
				case "pales":
					division = new DivisionPales(divisionColor1, divisionColor2, divisionColor3, divisionVal1, divisionVal2,
						divisionVal3);
					break;
				case "bends forward":
					division = new DivisionBendsForward(divisionColor1, divisionColor2);
					break;
				case "bends backward":
					division = new DivisionBendsBackward(divisionColor1, divisionColor2);
					break;
				case "bends both":
					division = new DivisionX(divisionColor1, divisionColor2);
					break;
				default:
					division = new DivisionGrid(divisionColor1, divisionColor2, divisionVal1, divisionVal2);
					break;
			}
			
			Overlay[] finalOverlays = new Overlay[overlays.size()];
			
			for (int i = 0; i < overlays.size(); i++)
			{
				Overlay o = overlays.get(i).ToOverlay(gridRatio.Width, gridRatio.Height, file.getParent());
				finalOverlays[i] = o;
			}

			return new Flag(name, ratio, gridRatio, division, finalOverlays);
		}
		catch (Exception ex)
		{
			throw new Exception(line, ex);
		}
	}
	
	private static Double GetDoubleFromString(String data)
	{
		// Doubles in files can be written as "123.45" or "123,45".
		// (Ignore thousands separators - not really applicable for FlagMaker.)
		// If the user saved a file with commas, replace and parse with invariant culture.
		return Double.parseDouble(data.replace(',', '.'));
	}
}
