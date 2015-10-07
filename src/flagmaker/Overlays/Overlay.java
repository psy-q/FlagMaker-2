package flagmaker.Overlays;

import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Overlay
{
	public boolean IsEnabled;
	public Color Color;
	public Attribute[] Attributes;
	protected int MaximumX;
	protected int MaximumY;
	
	public abstract String Name();
	protected abstract Shape[] Thumbnail();
	
	public abstract void Draw(Pane canvas);
	public abstract void SetValues(List<Double> values);
	public abstract String ExportSvg(int width, int height);
	
	protected Overlay(Attribute[] attributes, int maximumX, int maximumY)
	{
		IsEnabled = true;
		Color = Color.BLACK;
		Attributes = attributes;
		SetMaximum(maximumX, maximumY);
	}
	
	public void SetColor(Color color)
	{
		Color = color;
	}
	
	public void SetMaximum(int maximumX, int maximumY)
	{
		MaximumX = maximumX;
		MaximumY = maximumY;
	}
}
