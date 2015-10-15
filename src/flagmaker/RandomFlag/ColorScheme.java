package flagmaker.RandomFlag;

import javafx.scene.paint.Color;

public class ColorScheme
{
	private static final Color Yellow = Color.rgb(253, 200, 47);
	private static final Color White = Color.WHITE;
	private static final Color Black = Color.BLACK;
	private static final Color Red = Color.rgb(198, 12, 48);
	private static final Color Orange = Color.rgb(255, 99, 25);
	private static final Color Green = Color.rgb(20, 77, 41);
	private static final Color Blue = Color.rgb(0, 57, 166);
	
	private final Color _color1;
	private final Color _color2;
	private final Color _color3;
	private final Color _metal;
	
	public ColorScheme()
	{
		Color[] colors = new Color[] { Black, Red, Orange, Green, Blue };
		int color1Index = Randomizer.RandomWeighted(new int[] { 27, 102, 4, 45, 58 });
		
		int[][] firstOrderBase = new int[][]
		{            //  B   R  O   G   U
			new int[] {  0, 38, 0, 22, 11 }, // Black
			new int[] { 38,  0, 0, 76, 69 }, // Red
			new int[] {  0,  0, 0,  8,  1 }, // Orange
			new int[] { 22, 76, 8,  0, 34 }, // Green
			new int[] { 11, 69, 1, 34,  0 }  // Blue
		};
		
		int color2Index = Randomizer.RandomWeighted(firstOrderBase[color1Index]);
		
		int color3Index;
		do
		{
			color3Index = Randomizer.RandomWeighted(firstOrderBase[color1Index]);
		} while (color3Index == color2Index);
		
		double[][] yellowProbabilities = new double[][]
		{                  // B     R     O     G     B
			new double[] { 0.00, 0.54, 0.00, 0.25, 0.60 }, // Black
			new double[] { 0.54, 0.00, 0.00, 0.59, 0.24 }, // Red
			new double[] { 0.00, 0.00, 0.00, 0.00, 0.00 }, // Orange
			new double[] { 0.25, 0.59, 0.00, 0.00, 0.55 }, // Green
			new double[] { 0.60, 0.60, 0.00, 0.55, 0.00 }, // Blue
		};
		double yellowProbability = yellowProbabilities[color1Index][color2Index];

		_color1 = TweakColor(colors[color1Index]);
		_color2 = TweakColor(colors[color2Index]);
		_color3 = TweakColor(colors[color3Index]);
		_metal = Randomizer.ProbabilityOfTrue(yellowProbability) ? TweakColor(Yellow) : White;
	}
	
	private ColorScheme(Color color1, Color color2, Color color3, Color metal)
	{
		_color1 = color1;
		_color2 = color2;
		_color3 = color3;
		_metal = metal;
	}
	
	public Color Color1() { return _color1; }
	public Color Color2() { return _color2; }
	public Color Color3() { return _color3; }
	public Color Metal() { return _metal; }
	
	public ColorScheme Swapped()
	{
		return new ColorScheme(_color3, _color1, _color2, _metal);
	}
	
	private Color TweakColor(Color color)
	{
		if (color == Color.BLACK || color == Color.WHITE)
		{ // Don't adjust black or white, it looks bad
			return color;
		}

		final int spread = 15;
		return Color.rgb(
			Randomizer.Clamp(Randomizer.NextNormalized(color.getRed() * 255, spread), 0, 255, false),
			Randomizer.Clamp(Randomizer.NextNormalized(color.getGreen() * 255, spread), 0, 255, false),
			Randomizer.Clamp(Randomizer.NextNormalized(color.getBlue() * 255, spread), 0, 255, false));
	}
}
