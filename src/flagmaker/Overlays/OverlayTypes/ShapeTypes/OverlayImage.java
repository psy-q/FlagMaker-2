package flagmaker.Overlays.OverlayTypes.ShapeTypes;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class OverlayImage extends OverlayShape
{
	private File _path;
	private Image _bitmap;

	public OverlayImage(int maximumX, int maximumY)
	{
		super(maximumX, maximumY);
	}
	
	public OverlayImage(File path, int maximumX, int maximumY)
	{
		super(maximumX, maximumY);
		SetPath(path);
	}
	
	public OverlayImage(File path, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super(x, y, width, height, maximumX, maximumY);
		SetPath(path);
	}
	
	public File GetPath()
	{
		return _path;
	}
	
	public void SetPath(File value)
	{
		_path = value;
		
		if (_path.exists())
		{
			_bitmap = new Image(_path.getPath());
		}
	}

	@Override
	public String Name()
	{
		return "image";
	}

	@Override
	protected Shape[] Thumbnail()
	{
		Rectangle border = new Rectangle(25, 30, new Color(1, 1, 0, 0));
		border.setStrokeWidth(3);
		border.setLayoutX(2.5);
		
		Rectangle square = new Rectangle(8, 8);
		square.setLayoutX(7);
		square.setLayoutY(5);
		
		Ellipse circle = new Ellipse(19, 15, 4, 4);
		Polygon triangle = new Polygon(8, 25, 12, 17, 16, 25);
		
		return new Shape[]
		{
			border,
			square,
			circle,
			triangle
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
