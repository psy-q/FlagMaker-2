package flagmaker.Overlays.OverlayTypes.ShapeTypes;

import flagmaker.Divisions.DivisionGrid;
import flagmaker.Flag;
import flagmaker.Overlays.Overlay;
import flagmaker.Ratio;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class OverlayFlag extends OverlayShape
{
	public Flag Flag;
	public String Path;

	public OverlayFlag(int maximumX, int maximumY)
	{
		super(maximumX, maximumY);
		Flag = new Flag("flag", new Ratio(2, 3), new Ratio(2, 3), new DivisionGrid(Color.WHITE, Color.BLACK, 2, 2), new Overlay[]{});
	}
	
	public OverlayFlag(Flag flag, String path, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super(x, y, width, height, maximumX, maximumY);
		Flag = flag;
		Path = path;
	}

	@Override
	public String Name()
	{
		return "flag";
	}

	@Override
	protected Shape[] Thumbnail()
	{
		return new Shape[]
		{

		};
	}

	@Override
	public void Draw(Pane canvas)
	{
		
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		return "";
	}
}
