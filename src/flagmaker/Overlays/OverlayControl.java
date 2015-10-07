package flagmaker.Overlays;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OverlayControl extends VBox
{
	@FXML private ColorPicker overlayPicker;
	
	private Stage _stage;
	
	private Overlay _overlay;
	private int _defaultMaximumX;
	private int _defaultMaximumY;
	private boolean _isFirst;
	
	public boolean IsLoading;
	public boolean WasCanceled;
	
	public OverlayControl(Stage stage, int defaultMaximumX, int defaultMaximumY, boolean isLoading)
	{
		Load(stage);
		
		IsLoading = isLoading;
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
		//...
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
	
	private void OverlaySliderChanged()
	{
		//Overlay.SetValues(_pnlSliders.Children.OfType<AttributeSlider>().Select(s => s.Value).ToList());
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
		
		if (control.GetSelectedOverlay() == null)
		{
			WasCanceled = true;
			return;
		}

		_overlay = control.GetSelectedOverlay();
		if (!IsLoading) Draw();
	}

	private void Draw()
	{
//		if (OnDraw != null)
//		{
//			OnDraw(null, new EventArgs());
//		}
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
}
