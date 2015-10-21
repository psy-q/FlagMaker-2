package flagmaker.Overlays.Attributes;

import flagmaker.Overlays.Attributes.Sliders.AttributeSlider;
import flagmaker.Overlays.Attributes.Sliders.IntegerAttributeSlider;
import flagmaker.Overlays.OverlayControl;

public class IntegerAttribute extends NumericAttribute<Integer>
{
	public int Value;
	
	public IntegerAttribute(String name, int initialValue, int maximum, boolean useMaxX)
	{
		super(name, maximum, useMaxX);
		Value = initialValue;
	}

	@Override
	public void SetValue(Object value)
	{
		Value = (int)value;
	}
	
	@Override
	public Integer GetValue()
	{
		return Value;
	}

	@Override
	public void SetValue(String value)
	{
		Value = Integer.parseInt(value);
	}

	@Override
	public AttributeSlider GetSlider(OverlayControl parent)
	{
		return new IntegerAttributeSlider(parent, Name, Value, Maximum, UseMaxX);
	}

	@Override
	public Attribute Clone()
	{
		return new IntegerAttribute(Name, Value, Maximum, UseMaxX);
	}
}
