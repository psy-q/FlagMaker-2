package flagmaker.Overlays.Attributes.Sliders;

import flagmaker.Color.ColorButton;
import flagmaker.Color.ColorButtonListener;
import flagmaker.Files.LocalizationHandler;
import flagmaker.Overlays.OverlayControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

public class ColorAttributeSlider extends AttributeSlider implements ColorButtonListener
{
	@FXML private Label lblName;
	@FXML private ColorButton picker;
		
	public ColorAttributeSlider(OverlayControl parent, String name, Color value)
	{
		super(parent, name);
		Load();
		picker.SetListener(parent.MainWindow, parent.Stage, this);
		
		String label = LocalizationHandler.Get(name);
		lblName.setText(label);
		lblName.setTooltip(new Tooltip(label));
		picker.SetValue(value);
	}
	
	@Override
	public Color GetValue()
	{
		return picker.GetValue();
	}
	
	public void SetValue(Color value)
	{
		TriggeredByUser = false;
		picker.SetValue(value);
		TriggeredByUser = true;
	}

	@Override
	public void SetValue(Object value)
	{
		SetValue((Color)value);
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

	@Override
	public void ColorChanged(Color oldval, Color newval)
	{
			if (TriggeredByUser && !newval.equals(oldval)) ValueChanged();
			TriggeredByUser = true;
	}
}
