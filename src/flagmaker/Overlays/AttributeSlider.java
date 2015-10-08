package flagmaker.Overlays;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class AttributeSlider extends HBox
{
	@FXML private Label lblName;
	@FXML private Label lblValue;
	@FXML private Slider slider;
	@FXML public CheckBox chkDiscrete;
	
	private boolean _isDiscrete;
	private final OverlayControl _parent;
	
	public AttributeSlider(OverlayControl parent, String name, boolean isDiscrete, double value, int maximum)
	{
		Load();
		
		_parent = parent;
		lblName.setText(name);
		lblName.setTooltip(new Tooltip(name));
		_isDiscrete = isDiscrete && (value % 1 == 0);
		chkDiscrete.setSelected(_isDiscrete);
		chkDiscrete.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldval, Boolean newval) -> CheckChanged());
		lblValue.setText(Double.toString(value));
		slider.setMax(maximum);
		slider.setSnapToTicks(_isDiscrete);
		slider.setValue(value);
		slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldval, Number newval) -> SliderValueChanged());
		//txtValue.Visibility = Visibility.Hidden;
	}
	
	public int GetMaximum()
	{
		return (int)slider.getMax();
	}
	
	public void SetMaximum(int value)
	{
		slider.setMax(value);
	}
	
	public double GetValue()
	{
		return slider.getValue();
	}
	
	public void SetValue(double value)
	{
		slider.setValue(value);
		SliderValueChanged();
	}
	
	private void SliderValueChanged()
	{
		lblValue.setText(_isDiscrete
				? String.format("%d", (int)slider.getValue())
				: String.format("%.2f", slider.getValue()));
		_parent.OverlaySliderChanged();
	}
	
	private void CheckChanged()
	{
		_isDiscrete = chkDiscrete.isSelected();
		slider.setSnapToTicks(_isDiscrete);
		
		if (_isDiscrete)
		{
			SetValue((int)Math.round(slider.getValue()));
		}
	}

	private void Load()
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AttributeSlider.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try
		{
			loader.load();
		}
		catch (Exception ex)
		{
			String s = ex.getMessage();
		}
	}
}
