package flagmaker.Overlays.Attributes;

import flagmaker.Overlays.Attributes.Sliders.AttributeSlider;
import flagmaker.Overlays.Attributes.Sliders.IntegerAttributeSlider;
import flagmaker.Overlays.OverlayControl;

public class IntegerAttribute extends NumericAttribute<Integer>
{
	public int Value;
	
	private IntegerAttributeSlider _slider;
	
	public IntegerAttribute(String name, int initialValue, int maximum, boolean useMaxX)
	{
		super(name, maximum, useMaxX);
		Value = initialValue;
	}

	@Override
	public void SetValue(Object value)
	{
		Value = (int)value;
		if (_slider != null)
		{
			_slider.SetValue(Value);
		}
	}

	@Override
	public void SetValue(String value)
	{
		SetValue(Integer.parseInt(value));
	}
	
	@Override
	public Integer GetValue()
	{
		return Value;
	}

	@Override
	public AttributeSlider GetSlider(OverlayControl parent)
	{
		_slider = new IntegerAttributeSlider(parent, Name, Value, Maximum, UseMaxX);
		return _slider;
	}

	@Override
	public Attribute Clone()
	{
		return new IntegerAttribute(Name, Value, Maximum, UseMaxX);
	}

	@Override
	public String ToSaveAsString()
	{
		return String.format("%d", Value);
	}
}
