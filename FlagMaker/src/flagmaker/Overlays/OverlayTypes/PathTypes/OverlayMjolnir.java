package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayMjolnir extends OverlayPath
{
	private final String Path = "m 0.403663 -40.088283 c -13.291938 8.424462 -35.616768 9.734932 -37.395266 9.828542 -1.778499 0.0936 -1.778499 1.59128 -1.778499 1.59128 l 0 14.97683 c 31.0301139 -0.60843 29.2048139 5.9858099 29.2048139 5.9858099 l 0 26.7661501 c 0 0 0.09689 13.05889 -6.7395699 13.19537 -0.21276 0.004 0 6.28436 0 6.28436 l 33.41705 0 c 0 0 0.12276 -6.27993 0 -6.28436 -6.88065 -0.24819 -6.73958 -13.19537 -6.73958 -13.19537 l 0 -26.7661501 c 0 0 -1.8253001 -6.5942399 29.20482 -5.9858099 l 0 -14.97683 c 0 0 0 -1.49768 -1.7785 -1.59128 -1.77849 -0.0936 -24.10333 -1.40408 -37.395269 -9.828542 z";
	private final Vector PathSize = new Vector(100, 100);
	
	public OverlayMjolnir(int maximumX, int maximumY)
	{
		super("mjolnir", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
