package flagmaker.Overlays;

import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Overlay
{
	public boolean IsEnabled;
	public Color Color;
	public List<Attribute> Attributes;
	protected int MaximumX;
	protected int MaximumY;
	
	public abstract String Name();
	protected abstract List<Shape> Thumbnail();
	
	public abstract void Draw();
	public abstract void SetValues(List<Double> values);
	public abstract void ExportSvg(int width, int height);
	
	protected Overlay(List<Attribute> attributes, int maximumX, int maximumY)
	{
		IsEnabled = true;
		Color = Color.BLACK;
		Attributes = attributes;
		SetMaximum(maximumX, maximumY);
	}
	
	public void SetMaximum(int maximumX, int maximumY)
	{
		MaximumX = maximumX;
		MaximumY = maximumY;
	}
}
