package flagmaker.Overlays.Attributes.Sliders;

import flagmaker.Extensions.ControlExtensions;
import flagmaker.Files.LocalizationHandler;
import flagmaker.Overlays.OverlayControl;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class IntegerAttributeSlider extends NumericAttributeSlider
{
	@FXML private Label lblName;
	@FXML private Label lblValue;
	@FXML private TextField txtValue;
	@FXML private Slider slider;
		
	public IntegerAttributeSlider(OverlayControl parent, String name, int value, int maximum, boolean useMaxX)
	{
		super(parent, name, useMaxX);
		Load();
		
		String label = LocalizationHandler.Get(name);
		lblName.setText(label);
		lblName.setTooltip(new Tooltip(label));
		lblValue.setText(String.format("%d", (int)value));
		slider.setMax(maximum);
		slider.setValue(value);
		slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldval, Number newval) ->
		{
			if (!oldval.equals(newval))
			{
				SliderValueChanged();
			}
		});
		ControlExtensions.HideControl(txtValue);
		txtValue.setOnKeyPressed((KeyEvent event) -> TxtValueKeyDown(event));
		txtValue.focusedProperty().addListener((arg0, oldval, newval) ->
		{
			if (oldval && !newval)
			{
				HideTxtValue();
			}
		});
	}
	
	@Override
	public int GetMaximum()
	{
		return (int)slider.getMax();
	}
	
	@Override
	public void SetMaximum(int value)
	{
		slider.setMax(value);
	}
	
	@Override
	public Integer GetValue()
	{
		return (int)slider.getValue();
	}
	
	public void SetValue(int value)
	{
		slider.setValue(value);
		SliderValueChanged();
	}
	
	private void SliderValueChanged()
	{
		lblValue.setText(String.format("%d", (int)slider.getValue()));
		ValueChanged();
	}

	private void Load()
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("IntegerAttributeSlider.fxml"));
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
	
	@FXML
	private void Clicked()
	{
		ControlExtensions.HideControl(lblValue);
		ControlExtensions.ShowControl(txtValue);
		txtValue.setText(Integer.toString((int)slider.getValue()));
		txtValue.selectAll();
		txtValue.requestFocus();
	}
	
	@FXML
	private void TxtValueKeyDown(KeyEvent e)
	{
		KeyCode k = e.getCode();
		switch (k)
		{
			case ENTER:
				HideTxtValue();
				try
				{
					slider.setValue(Integer.parseInt(txtValue.getText()));
				}
				catch (Exception ex)
				{
				}
				break;
			case ESCAPE:
				HideTxtValue();
				break;
			case DOWN:
			case UP:
				try
				{
					int value = Integer.parseInt(txtValue.getText());
					value = value + (k == KeyCode.UP ? 1 : -1);
					
					if (value < 0) value = 0;
					if (value > slider.getMax()) value = (int)slider.getMax();
					
					txtValue.setText(String.format("%d", value));
					slider.setValue(value);
				}
				catch (Exception ex)
				{
				}
				break;
		}
	}
	
	private void HideTxtValue()
	{
		ControlExtensions.HideControl(txtValue);
		ControlExtensions.ShowControl(lblValue);
	}
}
