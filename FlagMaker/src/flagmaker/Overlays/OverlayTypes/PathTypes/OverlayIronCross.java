package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayIronCross extends OverlayPath
{
	private final String Path = "M -155.90625,-270 A 352.46,352.46 0 0 1 -31.84375,-31.71875 352.46,352.46 0 0 1 -270,-155.90625 l 0,311.8125 A 352.46,352.46 0 0 1 -31.71875,31.84375 352.46,352.46 0 0 1 -155.90625,270 l 311.8125,0 A 352.46,352.46 0 0 1 31.84375,31.71875 352.46,352.46 0 0 1 270,155.90625 l 0,-311.8125 A 352.46,352.46 0 0 1 31.71875,-31.84375 352.46,352.46 0 0 1 155.90625,-270 l -311.8125,0 z";
	private final Vector PathSize = new Vector(540, 540);

	public OverlayIronCross(int maximumX, int maximumY)
	{
		super("iron cross", maximumX, maximumY);
		Constructor(Path, PathSize);
	}
}
