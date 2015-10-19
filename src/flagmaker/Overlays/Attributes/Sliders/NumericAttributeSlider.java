package flagmaker.Overlays.Attributes.Sliders;

import flagmaker.Overlays.OverlayControl;

public abstract class NumericAttributeSlider extends AttributeSlider
{
	public final boolean UseMaxX;
	
	public NumericAttributeSlider(OverlayControl parent, String name, boolean useMaxX)
	{
		super(parent, name);
		UseMaxX = useMaxX;
	}
	
	public abstract int GetMaximum();
	public abstract void SetMaximum(int value);
}
