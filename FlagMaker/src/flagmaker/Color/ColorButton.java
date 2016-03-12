package flagmaker.Color;

import flagmaker.MainWindowController;
import flagmaker.UI;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ColorButton extends VBox
{
	@FXML private Rectangle rect;
	private Color _value;
	private MainWindowController _mainWindowController;
	private Stage _stage;
	private ColorButtonListener _listener;
	
	public ColorButton()
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ColorButton.fxml"));
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
	
	public void SetListener(MainWindowController mainWindowController, Stage stage, ColorButtonListener listener)
	{
		_mainWindowController = mainWindowController;
		_stage = stage;
		_listener = listener;
	}
	
	public Color GetValue()
	{
		return _value;
	}
	
	public void SetValue(Color value)
	{
		_value = value;
		rect.setFill(value);
	}
	
	@FXML private void OnClicked()
	{
		Color oldval = _value;
		
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(_stage);
		ColorSelector control = new ColorSelector(dialog, new ArrayList<>(Arrays.asList(_mainWindowController.Flag().ColorsUsed())), _mainWindowController.RecentColors);
		Scene dialogScene = new Scene(control, 400, 400);
		dialogScene.getStylesheets().add(UI.class.getResource("Style.css").toExternalForm());
		dialog.setScene(dialogScene);
		dialog.setResizable(false);
		dialog.showAndWait();
		
		Color c = control.GetSelectedColor();
		if (c != null)
		{
			SetValue(c);
			
			if (_mainWindowController.RecentColors.contains(c))
			{
				_mainWindowController.RecentColors.removeIf(r -> r.equals(c));
			}
			
			_mainWindowController.RecentColors.add(0, c);
			
			_listener.ColorChanged(oldval, _value);
		}
	}
}
