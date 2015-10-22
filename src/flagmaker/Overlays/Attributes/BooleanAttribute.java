package flagmaker.Overlays.Attributes;

import flagmaker.Overlays.Attributes.Sliders.AttributeSlider;
import flagmaker.Overlays.Attributes.Sliders.BooleanAttributeSlider;
import flagmaker.Overlays.OverlayControl;

public class BooleanAttribute extends Attribute<Boolean>
{
	public boolean Value;
	
	private BooleanAttributeSlider _slider;
	
	public BooleanAttribute(String name, boolean initialValue)
	{
		super(name);
		Value = initialValue;
	}

	@Override
	public void SetValue(Object value)
	{
		Value = (boolean)value;
		if (_slider != null)
		{
			_slider.SetValue(Value);
		}
	}

	@Override
	public void SetValue(String value)
	{
		SetValue(Boolean.parseBoolean(value));
	}
	
	@Override
	public Boolean GetValue()
	{
		return Value;
	}

	@Override
	public AttributeSlider GetSlider(OverlayControl parent)
	{
		_slider = new BooleanAttributeSlider(parent, Name, Value);
		return _slider;
	}

	@Override
	public Attribute Clone()
	{
		return new BooleanAttribute(Name, Value);
	}

	@Override
	public String ExportAsString()
	{
		return Value ? "true" : "false";
	}
}
