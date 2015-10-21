package flagmaker.Overlays.Attributes;

import flagmaker.Overlays.Attributes.Sliders.AttributeSlider;
import flagmaker.Overlays.Attributes.Sliders.BooleanAttributeSlider;
import flagmaker.Overlays.OverlayControl;

public class BooleanAttribute extends Attribute<Boolean>
{
	public boolean Value;
	
	public BooleanAttribute(String name, boolean initialValue)
	{
		super(name);
		Value = initialValue;
	}

	@Override
	public void SetValue(Object value)
	{
		Value = (boolean)value;
	}

	@Override
	public void SetValue(String value)
	{
		Value = Boolean.parseBoolean(value);
	}
	
	@Override
	public Boolean GetValue()
	{
		return Value;
	}

	@Override
	public AttributeSlider GetSlider(OverlayControl parent)
	{
		return new BooleanAttributeSlider(parent, Name, Value);
	}

	@Override
	public Attribute Clone()
	{
		return new BooleanAttribute(Name, Value);
	}
}
