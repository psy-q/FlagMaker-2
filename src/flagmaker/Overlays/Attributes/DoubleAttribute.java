package flagmaker.Overlays.Attributes;

import flagmaker.Overlays.Attributes.Sliders.AttributeSlider;
import flagmaker.Overlays.Attributes.Sliders.DoubleAttributeSlider;
import flagmaker.Overlays.OverlayControl;

public class DoubleAttribute extends NumericAttribute<Double>
{
	public boolean IsDiscrete;
	public double Value;
	
	public DoubleAttribute(String name, double initialValue, int maximum, boolean useMaxX)
	{
		super(name, maximum, useMaxX);
		IsDiscrete = initialValue % 1 == 0;
		Value = initialValue;
	}

	@Override
	public void SetValue(Object value)
	{
		Value = (double)value;
	}

	@Override
	public void SetValue(String value)
	{
		Value = Double.parseDouble(value);
	}
	
	@Override
	public Double GetValue()
	{
		return Value;
	}

	@Override
	public AttributeSlider GetSlider(OverlayControl parent)
	{
		return new DoubleAttributeSlider(parent, Name, IsDiscrete, Value, Maximum, UseMaxX);
	}

	@Override
	public Attribute Clone()
	{
		return new DoubleAttribute(Name, Value, Maximum, UseMaxX);
	}
}
