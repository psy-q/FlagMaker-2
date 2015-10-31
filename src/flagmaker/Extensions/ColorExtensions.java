package flagmaker.Extensions;

import javafx.scene.paint.Color;

public class ColorExtensions
{
	public static String ToHexString(Color c, boolean includeOpacity)
	{
		return String.format("%s%s%s%s",
				includeOpacity && c.getOpacity() < 1.0 ? IntToHex((int) (c.getOpacity() * 255)) : "",
				IntToHex((int) (c.getRed() * 255)),
				IntToHex((int) (c.getGreen() * 255)),
				IntToHex((int) (c.getBlue() * 255)));
	}

	public static String ToSvgFillWithOpacity(Color c)
	{
		return String.format("fill=\"#%s\"%s",
				ToHexString(c, true),
				c.getOpacity() < 1.0
						? String.format(" fill=opacity=\"%s\"", c.getOpacity())
						: "");
	}
	
	public static Color ParseColor(String str)
	{
		double a = 1.0;
		int r, b, g;

		if (str.length() == 8)
		{
			a = ((double)Integer.parseInt(str.substring(0, 2), 16)) / 255.0;
			r = Integer.parseInt(str.substring(2, 4), 16);
			g = Integer.parseInt(str.substring(4, 6), 16);
			b = Integer.parseInt(str.substring(6, 8), 16);
		}
		else
		{
			r = Integer.parseInt(str.substring(0, 2), 16);
			g = Integer.parseInt(str.substring(2, 4), 16);
			b = Integer.parseInt(str.substring(4, 6), 16);
		}

		return Color.rgb(r, g, b, a);
	}

	private static String IntToHex(int value)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toHexString(value));
		while (sb.length() < 2)
		{
			sb.insert(0, '0');
		}
		return sb.toString();
	}
}
