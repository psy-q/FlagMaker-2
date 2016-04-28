package flagmaker.Overlays.Attributes;

import flagmaker.Extensions.ColorExtensions;
import flagmaker.Overlays.Attributes.Sliders.AttributeSlider;
import flagmaker.Overlays.Attributes.Sliders.ColorAttributeSlider;
import flagmaker.Overlays.OverlayControl;
import javafx.scene.paint.Color;

public class ColorAttribute extends Attribute
{
	public Color Value;
	
	private ColorAttributeSlider _slider;
	
	public ColorAttribute(String name, Color initialValue)
	{
		super(name);
		Value = initialValue;
	}
	
	@Override
	public void SetValue(Object value)
	{
		Value = (Color)value;
		if (_slider != null)
		{
			_slider.SetValue(Value);
		}
	}

	@Override
	public void SetValue(String value)
	{
		SetValue(ColorExtensions.ParseColor(value));
	}
	
	@Override
	public Color GetValue()
	{
		return Value;
	}

	@Override
	public AttributeSlider GetSlider(OverlayControl parent)
	{
		_slider = new ColorAttributeSlider(parent, Name, Value);
		return _slider;
	}

	@Override
	public Attribute Clone()
	{
		return new ColorAttribute(Name, Value);
	}

	@Override
	public String ExportAsString()
	{
		return ColorExtensions.ToHexString(Value, true);
	}
}
