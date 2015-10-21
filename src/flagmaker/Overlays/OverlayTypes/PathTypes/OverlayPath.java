package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.ColorExtensions;
import flagmaker.Overlays.Attributes.*;
import flagmaker.Overlays.Overlay;
import flagmaker.Vector;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineJoin;

public class OverlayPath extends Overlay
{
	private Vector _pathSize;
	private String _path;
	
	public Color StrokeColor;
	
	public OverlayPath(String name, int maximumX, int maximumY)
	{
		super(name, new Attribute[]
		{
			new ColorAttribute("Color", Color.BLACK),
			new DoubleAttribute("X", 1, maximumX, true),
			new DoubleAttribute("Y", 1, maximumY, false),
			new DoubleAttribute("Size", 1, maximumX, true),
			new DoubleAttribute("Rotation", 0, maximumX, true),
			new DoubleAttribute("Stroke", 0, maximumX, true),
			new BooleanAttribute("StrokeCurved", false)
		}, maximumX, maximumY);
		
		StrokeColor = Color.WHITE;
	}
	
	public OverlayPath(String name, String path, Vector pathSize, Color color, double x, double y, double size, double rotation, double stroke, boolean strokeCurved, int maximumX, int maximumY)
	{
		super(name, new Attribute[]
		{
			new ColorAttribute("Color", color),
			new DoubleAttribute("X", x, maximumX, true),
			new DoubleAttribute("Y", y, maximumY, false),
			new DoubleAttribute("Size", size, maximumX, true),
			new DoubleAttribute("Rotation", rotation, maximumX, true),
			new DoubleAttribute("Stroke", stroke, maximumX, true),
			new BooleanAttribute("StrokeCurved", strokeCurved)
		}, maximumX, maximumY);
		
		_path = path;
		_pathSize = pathSize;
		StrokeColor = Color.WHITE;
	}
	
	protected void Constructor(String path, Vector pathSize)
	{
		_path = path;
		_pathSize = pathSize;
	}

	@Override
	protected Shape[] Thumbnail()
	{
		final double thumbSize = 30.0;
		double scale = thumbSize / Math.max(_pathSize.X, _pathSize.Y);
		SVGPath path = new SVGPath();
		path.setContent(_path);
		path.setScaleX(scale);
		path.setScaleY(scale);
		path.setLayoutX(thumbSize / 2);
		path.setLayoutY(thumbSize / 2);
		return new Shape[] { path };
	}

	@Override
	public void Draw(Pane canvas)
	{
		try
		{
			double xGridSize = canvas.getWidth() / MaximumX;
			double yGridSize = canvas.getHeight() / MaximumY;
			
			double x = GetDoubleAttribute("X");
			double y = GetDoubleAttribute("Y");
			
			Vector finalCenterPoint = new Vector(x * xGridSize, y * yGridSize);
			double scaleFactor = ScaleFactor(canvas.getWidth(), canvas.getHeight());
			
			SVGPath path = new SVGPath();
			path.setContent(_path);
			path.setFill(GetColorAttribute("Color"));
			path.setStroke(StrokeColor);
			path.setStrokeWidth(StrokeThickness(canvas.getWidth(), canvas.getHeight()));
			path.setStrokeLineJoin(GetBooleanAttribute("StrokeCurved")
				? StrokeLineJoin.ROUND
				: StrokeLineJoin.MITER);
			
			path.setRotate(GetDoubleAttribute("Rotation") / MaximumX * 360);
			path.setScaleX(scaleFactor);
			path.setScaleY(scaleFactor);
			
			canvas.getChildren().add(path);
						
			path.setLayoutX(finalCenterPoint.X);
			path.setLayoutY(finalCenterPoint.Y);
		}
		catch (Exception e)
		{
			// Show alert
		}
	}

	@Override
	public String ExportSvg(int width, int height)
	{
		double xGridSize = (double)width / MaximumX;
		double yGridSize = (double)height / MaximumY;

		double x = GetDoubleAttribute("X");
		double y = GetDoubleAttribute("Y");

		Vector finalCenterPoint = new Vector(x * xGridSize, y * yGridSize);

		double idealPixelSize = GetDoubleAttribute("Size") / MaximumX * Math.max(width, height);
		double scaleFactor = idealPixelSize / Math.max(_pathSize.X, _pathSize.Y);
		double rotate = (GetDoubleAttribute("Rotation") / MaximumX) * 360;

		double strokeThickness = StrokeThickness(width, height);
		boolean strokeCurved = GetBooleanAttribute("StrokeCurved");

		return String.format("<g transform=\"translate(%.3f,%.3f) rotate(%.3f) scale(%.3f)\"><path d=\"%s\" %s %s /></g>",
			finalCenterPoint.X, finalCenterPoint.Y, rotate, scaleFactor, _path, ColorExtensions.ToSvgFillWithOpacity(GetColorAttribute("Color")),
			strokeThickness > 0
				? String.format("stroke=\"#%s\" stroke-width=\"$.3f\" stroke-linejoin=\"%s\"",
					ColorExtensions.ToHexString(StrokeColor, false), strokeThickness, strokeCurved ? "round" : "miter")
				: "");
	}
	
	private double StrokeThickness(double canvasWidth, double canvasHeight)
	{
		return canvasWidth * GetDoubleAttribute("Stroke") / 32 / ScaleFactor(canvasWidth, canvasHeight) / MaximumX;
	}
	
	private double ScaleFactor(double canvasWidth, double canvasHeight)
	{
		double idealPixelSize = GetDoubleAttribute("Size") / MaximumX * Math.max(canvasWidth, canvasHeight);
		return idealPixelSize / Math.max(_pathSize.X, _pathSize.Y);
	}
}
