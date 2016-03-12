package flagmaker.Color;

import flagmaker.UI;
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
	
	public void SetListener(Stage stage, ColorButtonListener listener)
	{
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
		ColorSelector control = new ColorSelector(dialog);
		Scene dialogScene = new Scene(control, 400, 300);
		dialogScene.getStylesheets().add(UI.class.getResource("Style.css").toExternalForm());
		dialog.setScene(dialogScene);
		dialog.setResizable(false);
		dialog.showAndWait();
		
		Color c = control.GetSelectedColor();
		if (c != null)
		{
			SetValue(c);
			_listener.ColorChanged(oldval, _value);
		}
	}
}
