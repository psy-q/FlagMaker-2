package flagmaker.Overlays;

import flagmaker.Overlays.Attributes.*;
import java.util.HashMap;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Overlay
{
	public final String Name;
	public boolean IsEnabled;
	public Attribute[] Attributes;
	protected int MaximumX;
	protected int MaximumY;
	
	protected abstract Shape[] Thumbnail();
	
	public abstract void Draw(Pane canvas);
	public abstract String ExportSvg(int width, int height);
	
	protected Overlay(String name, Attribute[] attributes, int maximumX, int maximumY)
	{
		Name = name;
		IsEnabled = true;
		Attributes = attributes;
		SetMaximum(maximumX, maximumY);
	}
	
	public final void SetMaximum(int maximumX, int maximumY)
	{
		MaximumX = maximumX;
		MaximumY = maximumY;
		
		for (Attribute a : Attributes)
		{
			if (a instanceof NumericAttribute)
			{
				((NumericAttribute)a).Maximum = MaximumX;
			}
		}
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
			if (thumb.fillProperty().get() == null) thumb.setFill(Color.BLACK);
			p.getChildren().add(thumb);
		}
		
		return p;
	}
	
	public void SetValues(HashMap<String, Object> values)
	{
		values.entrySet().stream().forEach((v) ->
		{
			String name = v.getKey();
			Object value = v.getValue();
			
			// Will fail for missing sttributes
			SetAttribute(name, value);
		});
	}
	
	public void SetValuesFromStrings(HashMap<String, String> values)
	{
		values.entrySet().stream().forEach((v) ->
		{
			String name = v.getKey();
			String value = v.getValue();
			
			if (name.matches("size\\d"))
			{
				// Backwards-compatibility for 1.x file format
				int attributeIndex = Integer.parseInt(name.substring(4, 5));
				Attributes[attributeIndex].SetValue(value);
			}
			else
			{
				// Will fail for missing sttributes
				for (Attribute a : Attributes)
				{
					if (a.Name.equalsIgnoreCase(name))
					{
						a.SetValue(value);
						return;
					}
				}
			}
		});
	}
	
	public <T> void SetAttribute(String name, T value)
	{
		for (Attribute a : Attributes)
		{
			if (a.Name.equalsIgnoreCase(name))
			{
				a.SetValue(value);
				return;
			}
		}
		
		// Attribute not found
	}
	
	public Attribute GetAttribute(String name)
	{
		for (Attribute a : Attributes)
		{
			if (a.Name.equalsIgnoreCase(name))
			{
				return a;
			}
		}
		
		// Attribute not found
		return null;
	}
	
	public double GetDoubleAttribute(String name)
	{
		for (Attribute a : Attributes)
		{
			if (a.Name.equalsIgnoreCase(name) && a instanceof DoubleAttribute)
			{
				return ((DoubleAttribute)a).Value;
			}
		}
		
		// Attribute not found
		return 0;
	}
	
	public int GetIntegerAttribute(String name)
	{
		for (Attribute a : Attributes)
		{
			if (a.Name.equalsIgnoreCase(name) && a instanceof IntegerAttribute)
			{
				return ((IntegerAttribute)a).Value;
			}
		}
		
		// Attribute not found
		return 0;
	}
	
	public boolean GetBooleanAttribute(String name)
	{
		for (Attribute a : Attributes)
		{
			if (a.Name.equalsIgnoreCase(name) && a instanceof BooleanAttribute)
			{
				return ((BooleanAttribute)a).Value;
			}
		}
		
		// Attribute not found
		return false;
	}
	
	public Color GetColorAttribute(String name)
	{
		for (Attribute a : Attributes)
		{
			if (a.Name.equalsIgnoreCase(name) && a instanceof ColorAttribute)
			{
				return ((ColorAttribute)a).Value;
			}
		}
		
		// Attribute not found
		return Color.BLACK;
	}
}
