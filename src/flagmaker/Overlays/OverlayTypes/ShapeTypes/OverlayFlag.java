package flagmaker.Overlays.OverlayTypes.ShapeTypes;

import flagmaker.Divisions.DivisionGrid;
import flagmaker.Flag;
import flagmaker.Overlays.Overlay;
import flagmaker.Ratio;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
		Rectangle base = new Rectangle(30, 20, new Color(0, 0, 0, 0));
		base.setLayoutY(5);
		base.setStrokeWidth(3);
		
		Line l1 = new Line(10, 7, 10, 23);
		l1.setStrokeWidth(5);
		
		Line l2 = new Line(2, 15, 28, 15);
		l2.setStrokeWidth(5);
		
		return new Shape[] { base, l1, l2 };
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
