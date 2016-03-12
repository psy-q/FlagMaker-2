package flagmaker.Color;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class ColorList
{
	public static ArrayList<NamedColor> FlagsOfTheWorld()
	{
		ArrayList<NamedColor> list = new ArrayList<>();
		list.add(new NamedColor(new Color(1, 0.4, 0.4, 1), "Very light red"));
		list.add(new NamedColor(new Color(1, 0.2, 0.2, 1), "Light red"));
		list.add(new NamedColor(new Color(1, 0, 0, 1), "Red"));
		list.add(new NamedColor(new Color(0.8, 0, 0, 1), "Dark red"));
		list.add(new NamedColor(new Color(0.6, 0, 0, 1), "Very dark red"));
		list.add(new NamedColor(new Color(0.6, 0.4, 0, 1), "Light brown"));
		list.add(new NamedColor(new Color(0.4, 0.2, 0, 1), "Brown"));
		list.add(new NamedColor(new Color(0.2, 0, 0, 1), "Dark brown"));
		list.add(new NamedColor(new Color(1, 0.6, 0, 1), "Light orange"));
		list.add(new NamedColor(new Color(1, 0.4, 0, 1), "Orange"));
		list.add(new NamedColor(new Color(1, 0.8, 0.2, 1), "Gold"));
		list.add(new NamedColor(new Color(1, 1, 0.8, 1), "Very light yellow"));
		list.add(new NamedColor(new Color(1, 1, 0.6, 1), "Light yellow"));
		list.add(new NamedColor(new Color(1, 1, 0, 1), "Yellow"));
		list.add(new NamedColor(new Color(1, 0.8, 0, 1), "Dark yellow"));
		list.add(new NamedColor(new Color(0, 1, 0.2, 1), "Very light green"));
		list.add(new NamedColor(new Color(0, 0.8, 0, 1), "Light green"));
		list.add(new NamedColor(new Color(0, 0.6, 0, 1), "Green"));
		list.add(new NamedColor(new Color(0, 0.4, 0, 1), "Dark green"));
		list.add(new NamedColor(new Color(0, 0.2, 0, 1), "Very dark green"));
		list.add(new NamedColor(new Color(0.2, 0.8, 1, 1), "Very light blue"));
		list.add(new NamedColor(new Color(0.2, 0.6, 1, 1), "Light blue"));
		list.add(new NamedColor(new Color(0, 0, 1, 1), "Blue"));
		list.add(new NamedColor(new Color(0, 0, 0.8, 1), "Dark blue"));
		list.add(new NamedColor(new Color(0, 0, 0.6, 1), "Very dark blue"));
		list.add(new NamedColor(new Color(0.4, 0, 0.6, 1), "Purple"));
		list.add(new NamedColor(new Color(1, 1, 1, 1), "White"));
		list.add(new NamedColor(new Color(0.8, 0.8, 0.8, 1), "Light grey"));
		list.add(new NamedColor(new Color(0.6, 0.6, 0.6, 1), "Grey"));
		list.add(new NamedColor(new Color(0.4, 0.4, 0.4, 1), "Dark grey"));
		list.add(new NamedColor(new Color(0.2, 0.2, 0.2, 1), "Very dark grey"));
		list.add(new NamedColor(new Color(0, 0, 0, 1), "Black"));
		return list;
	}
	
	public static ArrayList<NamedColor> FlagsOfAllNations()
	{
		ArrayList<NamedColor> list = new ArrayList<>();
		list.add(new NamedColor(new Color(0.365, 0.208, 0.153, 1),"Red brown"));
		list.add(new NamedColor(new Color(0.510, 0.141, 0.200, 1),"Crimson"));
		list.add(new NamedColor(new Color(0.776, 0.047, 0.188, 1),"Red"));
		list.add(new NamedColor(new Color(1.000, 0.388, 0.098, 1),"Orange"));
		list.add(new NamedColor(new Color(0.992, 0.784, 0.184, 1),"Deep yellow"));
		list.add(new NamedColor(new Color(0.996, 0.867, 0.000, 1),"Yellow"));
		list.add(new NamedColor(new Color(0.200, 0.451, 0.129, 1),"Green"));
		list.add(new NamedColor(new Color(0.078, 0.302, 0.161, 1),"Tartan green"));
		list.add(new NamedColor(new Color(0.157, 0.306, 0.212, 1),"Dark green"));
		list.add(new NamedColor(new Color(0.388, 0.600, 0.671, 1),"Azure blue"));
		list.add(new NamedColor(new Color(0.000, 0.396, 0.741, 1),"Intermediate blue"));
		list.add(new NamedColor(new Color(0.000, 0.224, 0.651, 1),"Heraldic blue"));
		list.add(new NamedColor(new Color(0.000, 0.149, 0.392, 1),"Royal blue"));
		list.add(new NamedColor(new Color(0.000, 0.129, 0.278, 1),"Navy blue"));
		list.add(new NamedColor(new Color(0.000, 0.000, 0.000, 1),"Black"));
		list.add(new NamedColor(new Color(0.553, 0.506, 0.482, 1),"Grey"));
		list.add(new NamedColor(new Color(1.000, 1.000, 1.000, 1),"White"));
		return list;
	}
}
