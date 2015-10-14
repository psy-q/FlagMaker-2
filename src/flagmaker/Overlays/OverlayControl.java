package flagmaker.Overlays;

import flagmaker.ControlExtensions;
import flagmaker.MainWindowController;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPath;
import flagmaker.Overlays.OverlayTypes.RepeaterTypes.OverlayRepeater;
import flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayFlag;
import flagmaker.Overlays.OverlayTypes.ShapeTypes.OverlayImage;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OverlayControl extends VBox
{
	@FXML private Button btnOverlay;
	@FXML private ColorPicker overlayPicker;
	@FXML private VBox pnlSliders;
	@FXML private ColorPicker strokePicker;
	@FXML private ImageView btnVisibility;
	
	private Stage _stage;
	
	private Overlay _overlay;
	private int _defaultMaximumX;
	private int _defaultMaximumY;
	private boolean _isFirst;
	private final MainWindowController _mainWindow;
	
	public boolean IsLoading;
	public boolean WasCanceled;
		
	public OverlayControl(Stage stage, MainWindowController mainWindow, int defaultMaximumX, int defaultMaximumY, boolean isLoading)
	{
		Load(stage);
		
		IsLoading = isLoading;
		_mainWindow = mainWindow;
		_defaultMaximumX = defaultMaximumX;
		_defaultMaximumY = defaultMaximumY;
		_isFirst = true;
		
		overlayPicker.valueProperty().addListener((ObservableValue<? extends Color> ov, Color oldval, Color newval) -> OverlayColorChanged());
		
		if (!IsLoading)
		{
			OverlaySelect();
		}
	}
	
	public Overlay GetOverlay()
	{
		return _overlay;
	}
	
	public void SetOverlay(Overlay value)
	{
		_overlay = value;
		btnOverlay.graphicProperty().set(_overlay.PaneThumbnail());
		btnOverlay.tooltipProperty().set(new Tooltip(_overlay.Name()));
		
		if (!_isFirst && !IsLoading)
		{
			double[] sliderValues = GetAttributeSliderValues();
			if (sliderValues.length > 0)
			{
				for (int i = sliderValues.length; i < _overlay.Attributes.length; i++)
				{
					sliderValues[i] = 0.0;
				}
				
				_overlay.SetValues(sliderValues);
				_overlay.SetColor(overlayPicker.getValue());
				
				if (_overlay instanceof OverlayPath)
				{
					((OverlayPath)_overlay).StrokeColor = strokePicker.getValue();
				}
			}
		}
		else if (_overlay instanceof OverlayPath)
		{
			strokePicker.setValue(((OverlayPath)_overlay).StrokeColor);
		}
		
		if (_overlay instanceof OverlayFlag || _overlay instanceof OverlayRepeater || _overlay instanceof OverlayImage)
		{
			ControlExtensions.HideControl(overlayPicker);
		}
		else
		{
			ControlExtensions.ShowControl(overlayPicker);
		}
		
		overlayPicker.setValue(_overlay.Color);
		SetVisibilityButton();
		
		pnlSliders.getChildren().clear();
		for (Attribute a : _overlay.Attributes)
		{
			AttributeSlider s = new AttributeSlider(this, a.Name, a.IsDiscrete, a.Value, a.UseMaxX ? _defaultMaximumX : _defaultMaximumY);
			pnlSliders.getChildren().add(s);
		}
		
		if (_overlay instanceof OverlayPath)
		{
			ControlExtensions.ShowControl(strokePicker);
		}
		else
		{
			ControlExtensions.HideControl(strokePicker);
		}
		
		_isFirst = false;
		IsLoading = false;
	}
	
	public Color GetColor()
	{
		return overlayPicker.getValue();
	}
	
	public void SetColor(Color value)
	{
		overlayPicker.setValue(value);
	}
	
	public void SetMaximum(int maximumX, int maximumY)
	{
		_defaultMaximumX = maximumX;
		_defaultMaximumY = maximumY;

		_overlay.SetMaximum(maximumX, maximumY);
		
		AttributeSlider[] sliders = GetAttributeSliders();
		for (int i = 0; i < sliders.length; i++)
		{
			AttributeSlider slider = sliders[i];
			int max = _overlay.Attributes[i].UseMaxX ? maximumX : maximumY;
			double newValue = slider.GetValue() * ((double)max / slider.GetMaximum());
			slider.SetDiscrete(newValue % 1 == 0);
			slider.SetMaximum(max);
			slider.SetValue(newValue);
		}
	}
	
	private void OverlayColorChanged()
	{
		if (_overlay == null) return;
		_overlay.SetColor(overlayPicker.getValue());
		Draw();
	}
	
	public void OverlaySliderChanged()
	{
		_overlay.SetValues(GetAttributeSliderValues());
		Draw();
	}

	@FXML private void OverlaySelect()
	{
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(_stage);
		OverlaySelector control = new OverlaySelector(dialog, _defaultMaximumX, _defaultMaximumY);
		Scene dialogScene = new Scene(control, 400, 300);
		dialog.setScene(dialogScene);
		dialog.showAndWait();
		
		Overlay o = control.GetSelectedOverlay();
		if (o == null)
		{
			WasCanceled = true;
			return;
		}

		SetOverlay(o);
		if (!IsLoading) Draw();
	}

	private void Draw()
	{
		_mainWindow.Draw();
	}

	@FXML private void Remove()
	{
		_mainWindow.Remove(this);
	}

	@FXML private void MoveUp()
	{
		_mainWindow.MoveUp(this);
	}

	@FXML private void MoveDown()
	{
		_mainWindow.MoveDown(this);
	}

	@FXML private void Clone()
	{
		_mainWindow.Clone(this);
	}

	@FXML private void SetVisibility()
	{
		_overlay.IsEnabled = !_overlay.IsEnabled;
		SetVisibilityButton();
		Draw();
	}

	private void SetVisibilityButton()
	{
		btnVisibility.setImage(new Image(_overlay.IsEnabled
				? "flagmaker/Images/check_on.png"
				: "flagmaker/Images/check_off.png"));
	}
	
	private void Load(Stage stage)
	{
		_stage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("OverlayControl.fxml"));
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
	
	private AttributeSlider[] GetAttributeSliders()
	{
		ArrayList<AttributeSlider> list = new ArrayList<>();
		for (Object control : pnlSliders.getChildren())
		{
			AttributeSlider slider = (AttributeSlider)control;
			list.add(slider);
		}
		
		AttributeSlider[] returnValue = new AttributeSlider[]{};
		return list.toArray(returnValue);
	}
	
	private double[] GetAttributeSliderValues()
	{
		int sliderCount = pnlSliders.getChildren().size();
		double[] list = new double[sliderCount];
		
		for (int i = 0; i < sliderCount; i++)
		{
			AttributeSlider slider = (AttributeSlider)pnlSliders.getChildren().get(i);
			list[i] = slider.GetValue();
		}
		
		return list;
	}
}
