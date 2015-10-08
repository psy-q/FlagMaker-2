package flagmaker.Overlays;

import flagmaker.MainWindowController;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OverlayControl extends VBox
{
	@FXML private Button btnOverlay;
	@FXML private ColorPicker overlayPicker;
	@FXML private VBox pnlSliders;
	
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
			Double[] sliderValues = GetAttributeSliderValues();
			//...
		}
//		else if (value instanceof OverlayPath)
//		{
//			
//		}
		
		// Set color picker visibility
		
		overlayPicker.setValue(_overlay.Color);
		SetVisibilityButton();
		
		pnlSliders.getChildren().clear();
		for (Attribute a : _overlay.Attributes)
		{
			AttributeSlider s = new AttributeSlider(this, a.Name, a.IsDiscrete, a.Value, a.UseMaxX ? _defaultMaximumX : _defaultMaximumY);
			pnlSliders.getChildren().add(s);
		}
		
		// Set stroke color picker visibility
		
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

//		var sliders = _pnlSliders.Children.OfType<AttributeSlider>().ToList();
//		for (int i = 0; i < _overlay.Attributes.Count; i++)
//		{
//			var slider = sliders[i];
//			var max = _overlay.Attributes[i].UseMaxX ? maximumX : maximumY;
//			var newValue = slider.Value * ((double)max / slider.Maximum);
//			slider._chkDiscrete.IsChecked = newValue % 1 == 0;
//			slider.Maximum = max;
//			slider.Value = newValue;
//		}
	}
	
	private void OverlayColorChanged()
	{
		if (_overlay == null) return;
		//Overlay.SetColor(overlayPicker.getValue());
		Draw();
	}
	
	public void OverlaySliderChanged()
	{
		_overlay.SetValues(GetAttributeSliderValues());
		Draw();
	}

	private void OverlaySelect()
	{
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(_stage);
		dialog.setResizable(false);
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

	private void Remove()
	{
//		if (OnRemove != null)
//		{
//			OnRemove(this, new EventArgs());
//		}
	}

	private void MoveUp()
	{
//		if (OnMoveUp != null)
//		{
//			OnMoveUp(this, new EventArgs());
//		}
	}

	private void MoveDown()
	{
//		if (OnMoveDown != null)
//		{
//			OnMoveDown(this, new EventArgs());
//		}
	}

	private void Clone()
	{
//		if (OnClone != null)
//		{
//			OnClone(this, new EventArgs());
//		}
	}

	private void SetVisibility()
	{
		_overlay.IsEnabled = !_overlay.IsEnabled;
		SetVisibilityButton();
		Draw();
	}

	private void SetVisibilityButton()
	{
//		((Image)_btnVisibility.Content).Source = new BitmapImage(
//			_overlay.IsEnabled
//				? new Uri(@"..\Images\check_on.png", UriKind.Relative)
//				: new Uri(@"..\Images\check_off.png", UriKind.Relative));
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
	
	private Double[] GetAttributeSliderValues()
	{
		ArrayList<Double> list = new ArrayList<>();
		for (Object control : pnlSliders.getChildren())
		{
			AttributeSlider slider = (AttributeSlider)control;
			list.add(slider.GetValue());
		}
		
		Double[] returnValue = new Double[]{};
		return list.toArray(returnValue);
	}
}
