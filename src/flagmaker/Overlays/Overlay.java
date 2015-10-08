package flagmaker.Overlays;

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
	public abstract void SetValues(Double[] values);
	public abstract String ExportSvg(int width, int height);
	
	protected Overlay(Attribute[] attributes, int maximumX, int maximumY)
	{
		IsEnabled = true;
		Color = Color.BLACK;
		Attributes = attributes;
		SetMaximum(maximumX, maximumY);
	}
	
	protected Overlay(Color color, Attribute[] attributes, int maximumX, int maximumY)
	{
		IsEnabled = true;
		Color = color;
		Attributes = attributes;
		SetMaximum(maximumX, maximumY);
	}
	
	public void SetColor(Color color)
	{
		Color = color;
	}
	
	public final void SetMaximum(int maximumX, int maximumY)
	{
		MaximumX = maximumX;
		MaximumY = maximumY;
	}
	
	public Pane PaneThumbnail()
	{
		Pane p = new Pane();
		p.setMinHeight(30);
		p.setMinWidth(30);
	
		for (Shape thumb : Thumbnail())
		{
			thumb.setStroke(Color.BLACK);
			if (thumb.getStrokeWidth() == 1.0) thumb.setStrokeWidth(0);
			thumb.setFill(Color.BLACK);
			p.getChildren().add(thumb);
		}
		
		return p;
	}
	
	public void SetAttribute(String name, double value)
	{
		for (Attribute a : Attributes)
		{
			if (a.Name == name)
			{
				a.Value = value;
				return;
			}
		}
		
		// Attribute not found
	}
	
	public Attribute GetAttribute(String name)
	{
		for (Attribute a : Attributes)
		{
			if (a.Name == name)
			{
				return a;
			}
		}
		
		// Attribute not found
		return null;
	}
}
