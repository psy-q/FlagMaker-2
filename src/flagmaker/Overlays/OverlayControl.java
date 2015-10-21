package flagmaker.Overlays;

import flagmaker.Overlays.Attributes.Attribute;
import flagmaker.ControlExtensions;
import flagmaker.LocalizationHandler;
import flagmaker.MainWindowController;
import flagmaker.Overlays.Attributes.Sliders.*;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OverlayControl extends VBox
{
	@FXML private Button btnOverlay;
	@FXML private VBox pnlSliders;
	@FXML private HBox strokeBox;
	@FXML private ColorPicker strokePicker;
	@FXML private ImageView btnVisibility;
	
	@FXML private Tooltip ttpChangeType;
	@FXML private Tooltip ttpVisibility;
	@FXML private Tooltip ttpRemove;
	@FXML private Tooltip ttpMoveUp;
	@FXML private Tooltip ttpMoveDown;
	@FXML private Tooltip ttpClone;
	@FXML private Label lblStroke;
	
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
		LoadLocalization();
		
		IsLoading = isLoading;
		_mainWindow = mainWindow;
		_defaultMaximumX = defaultMaximumX;
		_defaultMaximumY = defaultMaximumY;
		_isFirst = true;
		
		strokePicker.valueProperty().addListener((ObservableValue<? extends Color> ov, Color oldval, Color newval) ->
		{
			((OverlayPath)_overlay).StrokeColor = strokePicker.getValue();
			Draw();
		});
		
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
		btnOverlay.tooltipProperty().set(new Tooltip(_overlay.Name));
		
		if (!_isFirst && !IsLoading)
		{
			HashMap<String, Object> sliderValues = GetAttributeSliderValues();
			if (!sliderValues.isEmpty())
			{
				sliderValues.clear();
				_overlay.SetValues(sliderValues);
				
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
		
		SetVisibilityButton();
		
		pnlSliders.getChildren().clear();
		for (Attribute a : _overlay.Attributes)
		{
			pnlSliders.getChildren().add(a.GetSlider(this));
		}
		
		if (_overlay instanceof OverlayPath)
		{
			ControlExtensions.ShowControl(strokeBox);
		}
		else
		{
			ControlExtensions.HideControl(strokeBox);
		}
		
		_isFirst = false;
		IsLoading = false;
	}
	
	public void SetMaximum(int maximumX, int maximumY)
	{
		_defaultMaximumX = maximumX;
		_defaultMaximumY = maximumY;

		_overlay.SetMaximum(maximumX, maximumY);
		
		AttributeSlider[] sliders = GetAttributeSliders();
		for (AttributeSlider slider : sliders)
		{
			if (slider instanceof NumericAttributeSlider)
			{
				((NumericAttributeSlider)slider).SetMaximum(((NumericAttributeSlider)slider).UseMaxX
						? _defaultMaximumX
						: _defaultMaximumY);
			}
		}
	}
	
	private void LoadLocalization()
	{
		ttpChangeType.setText(LocalizationHandler.Get("OverlayChangeType"));
		ttpVisibility.setText(LocalizationHandler.Get("ToggleVisibility"));
		ttpRemove.setText(LocalizationHandler.Get("Remove"));
		ttpMoveUp.setText(LocalizationHandler.Get("MoveUp"));
		ttpMoveDown.setText(LocalizationHandler.Get("MoveDown"));
		ttpClone.setText(LocalizationHandler.Get("Clone"));
		lblStroke.setText(LocalizationHandler.Get("Stroke"));
	}
	
	public void OverlaySliderChanged()
	{
		_overlay.SetValues(GetAttributeSliderValues());
		Draw();
		_mainWindow.SetAsUnsaved();
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
			list.add((AttributeSlider)control);
		}
		
		AttributeSlider[] returnValue = new AttributeSlider[]{};
		return list.toArray(returnValue);
	}
	
	private HashMap<String, Object> GetAttributeSliderValues()
	{
		int sliderCount = pnlSliders.getChildren().size();
		HashMap<String, Object> list = new HashMap<>();
		
		for (int i = 0; i < sliderCount; i++)
		{
			AttributeSlider slider = (AttributeSlider)pnlSliders.getChildren().get(i);
			list.put(slider.Name, slider.GetValue());
		}
		
		return list;
	}
	
	Object[] AddElement(Object[] original, double added)
	{
		Object[] result = Arrays.copyOf(original, original.length +1);
		result[original.length] = added;
		return result;
	}
}
