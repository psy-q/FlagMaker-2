package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayPine extends OverlayPath
{
	private final String Path = "m 11,58.4 c 0,20 14,25 20.93525,33 L -32,91.4 c 10,-9 21,-18 21,-33 l 0,-19 c -14,13 -29,18 -47,23 4,-13 19,-15 25,-28 -23,13 -25,13 -46,4 19,-10 26,-10 37,-28 -15,12 -20,10 -40,2 22,-8 27,-7 46,-27 -12,0 -21,-1 -36,1 19,-9 32,-15 47,-25 -9,1 -19,-1 -29,-2 10,-8 28,-17 40,-26 -7,1 -12,1 -19,0 12,-6 26.0000003,-15 33.00000030000001,-25 C 10,-80.6 24,-70.6 38,-64.6 c -6,1 -16,3 -22,0 12,10 22,21 36,27 -8,1 -14,-1 -22,-1 13,10 23,17 37,25 -11,1 -15,0 -26,-3 13,13 19,14 41,30 -16,2 -20,3 -36,1 16,15 18,16 36,26 -10,1 -32,3 -45,-4 3,10 15,21 22,28 -17,-3 -33,-10 -48,-25 l 0,19 z";
	private final Vector PathSize = new Vector(164, 184);
	
	public OverlayPine(int maximumX, int maximumY)
	{
		super("pine", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
