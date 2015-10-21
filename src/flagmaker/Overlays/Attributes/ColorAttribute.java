package flagmaker.Overlays.Attributes;

import flagmaker.ColorExtensions;
import flagmaker.Overlays.Attributes.Sliders.AttributeSlider;
import flagmaker.Overlays.Attributes.Sliders.ColorAttributeSlider;
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
	public void SetValue(String value)
	{
		Value = ColorExtensions.ParseColor(value);
	}
	
	@Override
	public Color GetValue()
	{
		return Value;
	}

	@Override
	public AttributeSlider GetSlider(OverlayControl parent)
	{
		return new ColorAttributeSlider(parent, Name, Value);
	}

	@Override
	public Attribute Clone()
	{
		return new ColorAttribute(Name, Value);
	}
}
