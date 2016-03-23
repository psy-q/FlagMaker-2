package flagmaker.Overlays;

import flagmaker.Files.LocalizationHandler;
import flagmaker.MainWindowController;
import flagmaker.Overlays.Attributes.Attribute;
import flagmaker.Overlays.Attributes.Sliders.*;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPath;
import flagmaker.UI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OverlayControl extends VBox
{
	@FXML private Button btnOverlay;
	@FXML private VBox pnlSliders;
	@FXML private ImageView btnVisibility;
	
	@FXML private Tooltip ttpChangeType;
	@FXML private Tooltip ttpVisibility;
	@FXML private Tooltip ttpRemove;
	@FXML private Tooltip ttpMoveUp;
	@FXML private Tooltip ttpMoveDown;
	@FXML private Tooltip ttpClone;
	
	public Stage Stage;
	
	private Overlay _overlay;
	private int _defaultMaximumX;
	private int _defaultMaximumY;
	private boolean _isFirst;
	public final MainWindowController MainWindow;
	
	public boolean IsLoading;
	public boolean WasCanceled;
		
	public OverlayControl(Stage stage, MainWindowController mainWindow, int defaultMaximumX, int defaultMaximumY, boolean isLoading)
	{
		Load(stage);
		LoadLocalization();
		
		IsLoading = isLoading;
		MainWindow = mainWindow;
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
		Attribute[] oldAttributes = SaveOldEmblemAttributes(value);
		
		_overlay = value;
		btnOverlay.graphicProperty().set(_overlay.PaneThumbnail());
		btnOverlay.tooltipProperty().set(new Tooltip(_overlay.Name));
		
		SetAttributesFromSliders();		
		SetVisibilityButton();
		AddSliders();
		CopyOldEmblemAttributes(oldAttributes);
		
		_isFirst = false;
		IsLoading = false;
	}

	private void AddSliders()
	{
		pnlSliders.getChildren().clear();
		for (Attribute a : _overlay.Attributes)
		{
			pnlSliders.getChildren().add(a.GetSlider(this));
		}
	}

	private void SetAttributesFromSliders()
	{
		if (!_isFirst && !IsLoading)
		{
			HashMap<String, Object> sliderValues = GetAttributeSliderValues();
			if (!sliderValues.isEmpty())
			{
				sliderValues.clear();
				_overlay.SetValues(sliderValues);
			}
		}
	}

	private Attribute[] SaveOldEmblemAttributes(Overlay value)
	{
		if (_overlay != null && _overlay instanceof OverlayPath && value instanceof OverlayPath)
		{
			return ((OverlayPath)_overlay).Attributes;
		}
		
		return null;
	}

	private void CopyOldEmblemAttributes(Attribute[] oldAttributes)
	{
		if (oldAttributes != null)
		{
			for (int i = 0; i < oldAttributes.length; i++)
			{
				AttributeSlider slider = (AttributeSlider)pnlSliders.getChildren().get(i);
				slider.SetValue(oldAttributes[i].GetValue());
			}
		}
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
	}
	
	public void OverlaySliderChanged(boolean triggeredByUser)
	{
		if (triggeredByUser)
		{
			_overlay.SetValues(GetAttributeSliderValues());
			Draw();
		}
		MainWindow.SetAsUnsaved();
	}

	@FXML private void OverlaySelect()
	{
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(Stage);
		OverlaySelector control = new OverlaySelector(dialog, _defaultMaximumX, _defaultMaximumY);
		Scene dialogScene = new Scene(control, 400, 300);
		dialogScene.getStylesheets().add(UI.class.getResource("Style.css").toExternalForm());
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
		MainWindow.Draw();
	}

	@FXML private void Remove()
	{
		MainWindow.Remove(this);
	}

	@FXML private void MoveUp()
	{
		MainWindow.MoveUp(this);
	}

	@FXML private void MoveDown()
	{
		MainWindow.MoveDown(this);
	}

	@FXML private void Clone()
	{
		MainWindow.Clone(this);
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
		Stage = stage;
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
