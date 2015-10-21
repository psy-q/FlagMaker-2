package flagmaker.Overlays.Attributes.Sliders;

import flagmaker.Files.LocalizationHandler;
import flagmaker.Overlays.OverlayControl;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

public class ColorAttributeSlider extends AttributeSlider
{
	@FXML private Label lblName;
	@FXML private ColorPicker picker;
		
	public ColorAttributeSlider(OverlayControl parent, String name, Color value)
	{
		super(parent, name);
		Load();
		
		String label = LocalizationHandler.Get(name);
		lblName.setText(label);
		lblName.setTooltip(new Tooltip(label));
		picker.setValue(value);
		picker.valueProperty().addListener((ObservableValue<? extends Color> ov, Color oldval, Color newval) ->
		{
			if (!newval.equals(oldval)) ValueChanged();
		});
	}
	
	@Override
	public Color GetValue()
	{
		return picker.getValue();
	}
	
	public void SetValue(Color value)
	{
		picker.setValue(value);
	}

	private void Load()
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ColorAttributeSlider.fxml"));
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
