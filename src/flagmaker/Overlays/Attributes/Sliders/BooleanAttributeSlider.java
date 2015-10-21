package flagmaker.Overlays.Attributes.Sliders;

import flagmaker.Files.LocalizationHandler;
import flagmaker.Overlays.OverlayControl;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class BooleanAttributeSlider extends AttributeSlider
{
	@FXML private Label lblName;
	@FXML private CheckBox chkEnabled;
		
	public BooleanAttributeSlider(OverlayControl parent, String name, boolean value)
	{
		super(parent, name);
		Load();
		
		String label = LocalizationHandler.Get(name);
		lblName.setText(label);
		lblName.setTooltip(new Tooltip(label));
		chkEnabled.setSelected(value);
		chkEnabled.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldval, Boolean newval) ->
		{
			if (!newval.equals(oldval)) ValueChanged();
		});
	}
	
	@Override
	public Boolean GetValue()
	{
		return chkEnabled.isSelected();
	}
	
	public void SetValue(boolean value)
	{
		chkEnabled.setSelected(value);
	}

	@Override
	public void SetValue(Object value)
	{
		SetValue((boolean)value);
	}

	private void Load()
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BooleanAttributeSlider.fxml"));
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
