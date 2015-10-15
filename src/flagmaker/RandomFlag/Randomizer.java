package flagmaker.RandomFlag;

import java.util.Random;
import java.util.stream.IntStream;

public class Randomizer
{
	private static final Random R = new Random();
	
	public static int Next(int max)
	{
		return R.nextInt(max);
	}
	
	public static int RandomWeighted(int[] weights)
	{
		// Algorithm courtesy of this guy:
		// http://peterkellyonline.blogspot.com/2012/02/weighted-random-selection-in-php.html

		int sum = IntStream.of(weights).sum();
		int target = R.nextInt(sum) + 1;

		for (int i = 0; i < weights.length; i++)
		{
			target -= weights[i];
			if (target <= 0)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public static double NextNormalized(double mean, double standardDeviation)
	{
		// Box–Muller transform
		// http://stackoverflow.com/a/2751988/436282
		double u1 = R.nextDouble();
		double u2 = R.nextDouble();
		double normal = Math.sqrt(-2 * Math.log(u1)) * Math.cos(2 * Math.PI * u2);
		return (normal * standardDeviation) + mean;
	}
	
	public static boolean ProbabilityOfTrue(double probability)
	{
		return R.nextDouble() < probability;
	}
	
	public static int Clamp(double value, int min, int max, boolean forceOdd)
	{
		int val = (int)value;
		if (value < min) val = min;
		else if (value > max) val = max;

		if (forceOdd && val % 2 == 0)
		{
			val--;
		}

		return val;
	}
}
