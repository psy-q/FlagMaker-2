package flagmaker.Overlays.OverlayTypes.SpecialTypes;

import flagmaker.Extensions.StringExtensions;
import flagmaker.Overlays.Attributes.Attribute;
import flagmaker.Overlays.Attributes.ColorAttribute;
import flagmaker.Overlays.Attributes.DoubleAttribute;
import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayShape;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class OverlayImage extends Overlay
{
	private File _path;
	private Image _bitmap;

	public OverlayImage(int maximumX, int maximumY)
	{
		super("image", new Attribute[]
		{
			new DoubleAttribute("X", 1, maximumX, true),
			new DoubleAttribute("Y", 1, maximumY, false),
			new DoubleAttribute("Width", 1, maximumX, true),
			new DoubleAttribute("Height", 1, maximumY, false)
		}, maximumX, maximumY);
	}
	
	public OverlayImage(File path, int maximumX, int maximumY)
	{
		super("image", new Attribute[]
		{
			new DoubleAttribute("X", 1, maximumX, true),
			new DoubleAttribute("Y", 1, maximumY, false),
			new DoubleAttribute("Width", 1, maximumX, true),
			new DoubleAttribute("Height", 1, maximumY, false)
		}, maximumX, maximumY);
		SetPath(path);
	}
	
	public OverlayImage(File path, double x, double y, double width, double height, int maximumX, int maximumY)
	{
		super("image", new Attribute[]
		{
			new DoubleAttribute("X", x, maximumX, true),
			new DoubleAttribute("Y", y, maximumY, false),
			new DoubleAttribute("Width", width, maximumX, true),
			new DoubleAttribute("Height", height, maximumY, false)
		}, maximumX, maximumY);
		SetPath(path);
	}
	
	public File GetPath()
	{
		return _path;
	}
	
	public final void SetPath(File value)
	{
		_path = value;
		
		if (_path.exists())
		{
			try
			{
				URI uri = _path.toURI();
				URL url = uri.toURL();
				_bitmap = new Image(url.toString());
			}
			catch (Exception e)
			{
			}
		}
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
		double width = canvas.getWidth() * GetDoubleAttribute("Width") / MaximumX;
		double height = canvas.getHeight() * GetDoubleAttribute("Height") / MaximumY;
		
		if (height == 0)
		{
			double ratio = _bitmap.getHeight() / _bitmap.getWidth();
			height = width * ratio;
		}

		Canvas c = new Canvas(width, height);
		GraphicsContext gc = c.getGraphicsContext2D();
		gc.drawImage(_bitmap, 0, 0, width, height);

		c.setLayoutX((canvas.getWidth() * (GetDoubleAttribute("X") / MaximumX)) - width / 2);
		c.setLayoutY((canvas.getHeight() * (GetDoubleAttribute("Y") / MaximumY)) - height / 2);
		canvas.getChildren().add(c);
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		try
		{
			double imageWidth = width * GetDoubleAttribute("Width") / MaximumX;
			double imageHeight = height * GetDoubleAttribute("Height") / MaximumY;
			if (imageHeight <= 0)
			{
				double ratio = _bitmap.getHeight() / _bitmap.getWidth();
				imageHeight = imageWidth * ratio;
			}
			
			byte[] bytes = Base64.getEncoder().encode(Files.readAllBytes(_path.toPath()));
			String base64String = new String(bytes);
			
			return String.format("<image x=\"%.3f\" y=\"%.3f\" width=\"%.3f\" height=\"%.3f\" preserveAspectRatio=\"none\" xlink:href=\"data:image/%s;base64,%s\" />",
					width * (GetDoubleAttribute("X") / MaximumX) - imageWidth / 2,
					height * (GetDoubleAttribute("Y") / MaximumY) - imageHeight / 2,
					imageWidth,
					imageHeight,
					StringExtensions.GetFilenameExtension(_path.getPath()),
					base64String);
		}
		catch (IOException ex)
		{
			return "";
		}
	}
}
