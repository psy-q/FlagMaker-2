package flagmaker.Color;

import flagmaker.Files.LocalizationHandler;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ColorSelector extends VBox
{
	private Stage _stage;

	@FXML private TabPane tabs;
	@FXML private Button btnCancel;
	private Random _random;
	
	public ColorSelector(Stage stage)
	{
		Load(stage);
		
		_random = new Random();

		stage.titleProperty().set(LocalizationHandler.Get("Color"));
		stage.getIcons().add(new Image("flagmaker/Images/icon.png"));
		btnCancel.setText(LocalizationHandler.Get("Cancel"));
	}
	
	private void Load(Stage stage)
	{
		_stage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ColorSelector.fxml"));
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

	Color GetSelectedColor()
	{
		double newR = _random.nextDouble();
		double newG = _random.nextDouble();
		double newB = _random.nextDouble();
		return new Color(newR, newG, newB, 1.0);
	}
	
	@FXML
	private void Cancel()
	{
		_stage.close();
	}
}
