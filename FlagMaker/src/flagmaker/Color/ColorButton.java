package flagmaker.Color;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class ColorButton extends VBox
{
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
}
