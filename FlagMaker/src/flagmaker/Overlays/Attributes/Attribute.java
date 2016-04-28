package flagmaker.Overlays.Attributes;

import flagmaker.Overlays.Attributes.Sliders.AttributeSlider;
import flagmaker.Overlays.OverlayControl;

public abstract class Attribute<T>
{
	public String Name;
	
	public Attribute(String name)
	{
		Name = name;
	}
	
	public abstract <T> void SetValue(T value);
	public abstract <T> void SetValue(String value);
	public abstract <T> T GetValue();
	public abstract AttributeSlider GetSlider(OverlayControl parent);
	public abstract Attribute Clone();
	public abstract String ExportAsString();
}
