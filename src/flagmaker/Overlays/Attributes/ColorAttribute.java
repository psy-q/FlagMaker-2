package flagmaker.Overlays.Attributes;

import flagmaker.Overlays.Attributes.Sliders.AttributeSlider;
import flagmaker.Overlays.OverlayControl;
import javafx.scene.paint.Color;

public class ColorAttribute extends Attribute
{
	public Color Value;
	
	public ColorAttribute(String name, Color initialValue)
	{
		super(name);
		Value = initialValue;
	}
	
	@Override
	public void SetValue(Object value)
	{
		Value = (Color)value;
	}
	
	@Override
	public Color GetValue()
	{
		return Value;
	}

	@Override
	public AttributeSlider GetSlider(OverlayControl parent)
	{
		return null;
	}

	@Override
	public Attribute Clone()
	{
		return new ColorAttribute(Name, Value);
	}
}
