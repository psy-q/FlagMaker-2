package flagmaker.Overlays.Attributes.Sliders;

import flagmaker.Extensions.ControlExtensions;
import flagmaker.Files.LocalizationHandler;
import flagmaker.Overlays.OverlayControl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DoubleAttributeSlider extends NumericAttributeSlider
{
	@FXML private Label lblName;
	@FXML private Label lblValue;
	@FXML private TextField txtValue;
	@FXML private Slider slider;
	@FXML private CheckBox chkDiscrete;
	
	private boolean _isDiscrete;
	
	public DoubleAttributeSlider(OverlayControl parent, String name, boolean isDiscrete, double value, int maximum, boolean useMaxX)
	{
		super(parent, name, useMaxX);
		Load();
		
		String label = LocalizationHandler.Get(name);
		lblName.setText(label);
		lblName.setTooltip(new Tooltip(label));
		_isDiscrete = isDiscrete && (value % 1 == 0);
		chkDiscrete.setSelected(_isDiscrete);
		chkDiscrete.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldval, Boolean newval) -> CheckChanged());
		lblValue.setText(_isDiscrete ? String.format("%d", (int)value) : String.format("%.3f", value));
		slider.setMax(maximum);
		slider.setSnapToTicks(_isDiscrete);
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
		double currentValue = slider.getValue();
		double ratio = currentValue / slider.getMax();
		double newValue = ratio * value;
		
		slider.setMax(value);
		SetValue(newValue);
	}
	
	@Override
	public Double GetValue()
	{
		return slider.getValue();
	}
	
	public void SetValue(double value)
	{
		slider.setValue(value);
		SetDiscrete(value % 1 == 0);
		SliderValueChanged(); // Hopefully won't cause infinite cascading event triggers
	}

	@Override
	public void SetValue(Object value)
	{
		SetValue((double)value);
	}
	
	public void SetDiscrete(boolean isDiscrete)
	{
		_isDiscrete = isDiscrete;
		chkDiscrete.setSelected(isDiscrete);
	}
	
	private void SliderValueChanged()
	{
		lblValue.setText(_isDiscrete
				? String.format("%d", (int)slider.getValue())
				: String.format("%.3f", slider.getValue()));
		ValueChanged();
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("DoubleAttributeSlider.fxml"));
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
		txtValue.setText(Double.toString(slider.getValue()));
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
				String text = txtValue.getText();
				
				if (text.contains("%"))
				{
					String stringVal = text.split("%")[0];
					try
					{
						double percentValue = Double.parseDouble(stringVal);
						SetValueByFraction(percentValue / 100);
					}
					catch (Exception ex)
					{
					}
				}
				else if (text.contains("/"))
				{
					String[] fraction = text.split("/");
					if (fraction.length != 2)
					{
						return;
					}

					String numerator = fraction[0];
					String denominator = fraction[1];
					try
					{
						double num = Double.parseDouble(numerator);
						double den = Double.parseDouble(denominator);
						if (den <= 0) return;
						SetValueByFraction(num / den);
					}
					catch (Exception ex)
					{
					}
				}
				else
				{
					try
					{
						double value = Double.parseDouble(text);
						chkDiscrete.setSelected(value % 1 == 0);
						slider.setValue(value);
					}
					catch (Exception ex)
					{
					}
				}
				break;
			case ESCAPE:
				HideTxtValue();
				break;
			case DOWN:
			case UP:
				try
				{
					double value = Double.parseDouble(txtValue.getText());
					value = value + (k == KeyCode.UP ? 0.01 : -0.01);
					
					if (value < 0.0) value = 0.0;
					if (value > slider.getMax()) value = slider.getMax();
					
					chkDiscrete.setSelected(false);
					txtValue.setText(String.format("%.3f", value));
					slider.setValue(value);
				}
				catch (Exception ex)
				{
				}
				break;
		}
	}
	
	private void SetValueByFraction(double fraction)
	{
		if (fraction > 1) fraction = 1;
		if (fraction < 0) fraction = 0;
		double result = fraction * slider.getMax();
		result = new BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN).doubleValue();

		chkDiscrete.setSelected(result % 1 == 0);
		slider.setValue(result);
	}
	
	private void HideTxtValue()
	{
		ControlExtensions.HideControl(txtValue);
		ControlExtensions.ShowControl(lblValue);
	}
}
