package flagmaker.Overlays.Attributes;

import flagmaker.Overlays.Attributes.Sliders.AttributeSlider;
import flagmaker.Overlays.Attributes.Sliders.DoubleAttributeSlider;
import flagmaker.Overlays.OverlayControl;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DoubleAttribute extends NumericAttribute<Double>
{
	public boolean IsDiscrete;
	public double Value;
	
	private DoubleAttributeSlider _slider;
	
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
		if (_slider != null)
		{
			_slider.SetValue(Value);
		}
	}

	@Override
	public void SetValue(String value)
	{
		SetValue(Double.parseDouble(value.replace(",", ".")));
	}
	
	@Override
	public Double GetValue()
	{
		return Value;
	}

	@Override
	public AttributeSlider GetSlider(OverlayControl parent)
	{
		_slider = new DoubleAttributeSlider(parent, Name, IsDiscrete, Value, Maximum, UseMaxX);
		return _slider;
	}

	@Override
	public Attribute Clone()
	{
		return new DoubleAttribute(Name, Value, Maximum, UseMaxX);
	}

	@Override
	public String ExportAsString()
	{
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("0.##", decimalFormatSymbols);
		return decimalFormat.format(Value);
	}
}
