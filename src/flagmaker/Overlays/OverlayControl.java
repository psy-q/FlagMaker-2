package flagmaker.Overlays;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class OverlayControl extends VBox
{
	public OverlayControl()
	{
		Load();
	}

	public void Load()
	{
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
