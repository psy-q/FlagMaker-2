package flagmaker.Overlays.Attributes.Sliders;

import flagmaker.Overlays.OverlayControl;
import javafx.scene.layout.HBox;

public abstract class AttributeSlider extends HBox
{
	public String Name;
	
	private final OverlayControl _parent;
	
	protected AttributeSlider(OverlayControl parent, String name)
	{
		_parent = parent;
		Name = name;
	}
	
	protected void ValueChanged()
	{
		_parent.OverlaySliderChanged();
	}
	
	public abstract Object GetValue();
	public abstract void SetValue(Object value);
}
