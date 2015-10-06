package flagmaker;

import javafx.scene.paint.Color;

public class ColorExtensions
{
	public static String ToHexString(Color c, boolean includeOpacity)
	{
		return String.format("%s%s%s%s",
			includeOpacity && c.getOpacity() < 1.0 ? Integer.toHexString((int)(c.getOpacity() * 255)) : "",
			Integer.toHexString((int)(c.getRed() * 255)),
			Integer.toHexString((int)(c.getGreen() * 255)),
			Integer.toHexString((int)(c.getBlue() * 255)));
	}
	
	public static String ToSvgFillWithOpacity(Color c)
	{
		return String.format("fill=\"#%s\"%s",
				ToHexString(c, true),
				c.getOpacity() < 1.0
					? String.format(" fill=opacity=\"%s\"", c.getOpacity())
					: "");
	}
}